package nimrod

import scala.concurrent.duration._

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._

class Simple2 extends Simulation {

	val httpProtocol = http
		.baseURL("http://45.55.151.43")
		.inferHtmlResources()
		.acceptHeader("text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8")
		.acceptEncodingHeader("gzip, deflate, sdch")
		.acceptLanguageHeader("en-US,en;q=0.8,he;q=0.6")
		.userAgentHeader("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_10_1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/41.0.2272.118 Safari/537.36")

	val headers_0 = Map("whoAmI" -> "Nimrod")

	val random = new util.Random
	val emailsAndPasswordFeed = Iterator.continually(Map("email" -> s"${random.nextString(20)}@foo.com", "pass" -> random.nextString(10)))

	val scn = scenario("Home Page visitor")
		.exec(http("get home page")
			.get("/nimrod.php")
			.headers(headers_0))
			.pause(1 seconds, 5 seconds) //pasue a random time 
		.exec(http("expecting error")
			.get("/nimrod.php?error=1")
			.check(status.is(500)) //expecting to get 500 response 
			.headers(headers_0))

	val otherScn = scenario("User register/login Flow")
				.feed(emailsAndPasswordFeed) //using a feeder to generate random email/users
				.exec(http("register")
					.post("/nimrod.php")
					.headers(headers_0)
					.formParam("email", "${email}")
					.formParam("password", "${pass}"))
					.pause(1 seconds, 10 seconds)
				.exec(http("login")
					.post("/nimrod.php")
					.headers(headers_0)
					.formParam("loginemail", "${email}")
					.formParam("loginpassword", "${pass}"))


	setUp(scn.inject(atOnceUsers(20)), otherScn.inject(rampUsersPerSec(10) to(50) during(3 minutes) randomized)).protocols(httpProtocol)
}
