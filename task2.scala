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

	val headers_0 = Map("whoAmI" -> "Nimrod") //just to see who this is in logs/relic

	val scn = scenario("scenario")
		.exec(http("get home page")
			.get("/nimrod.php")
			.headers(headers_0)) //adds the above header to the request
			.pause(1 seconds, 5 seconds) //random pause 1-5 seconds
		.exec(http("expecting error")
			.get("/nimrod.php?error=1")
			.check(status.is(500)) // expecting 500 response the defualt is 200 range
			.headers(headers_0))

	val otherScn = scenario("other scenario")
		.exec(http("get home page")
			.get("/nimrod.php")
			.headers(headers_0))


	setUp(scn.inject(atOnceUsers(10)),  //first scenario will be all 10 users at once
	      otherScn.inject(rampUsersPerSec(100) to(500) during(1 minutes) randomized)). //start with 100 and up to 500 in 1 minute
	      protocols(httpProtocol)
}
