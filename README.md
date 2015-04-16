# Gatling-Workshop!

##Servers:
Gatling Workshop1 server: http://45.55.151.43

Relic - https://rpm.newrelic.com/accounts/23428/applications/5683893

Gatling Workshop2 server: http://188.166.112.45

Relic - https://rpm.newrelic.com/accounts/23428/applications/5683776

Create your own endpoint for testing: 

http://45.55.151.43?new=YOUR-NAME

http://188.166.112.45?new=YOUR-NAME

##Gatling Download:
https://repo1.maven.org/maven2/io/gatling/highcharts/gatling-charts-highcharts-bundle/2.1.4/gatling-charts-highcharts-bundle-2.1.4-bundle.zip

##Cheat Sheet:

### Pause:
 
##### - Fixed pause duration:
```scala 
pause(duration: Duration)
```
##### - Random pause duration:
```scala 
pause(min: Duration, max: Duration)
```
##### - Example:
```scala 
      .exec(http("get some page").get("/some.php")).pause(1 seconds, 10 seconds)
```
Wiki: http://gatling.io/docs/2.0.0-RC2/general/scenario.html
 
 
### Checks:
 
##### - Default is checking for 200 status
##### - example of expecting 404
```scala 
.exec(http("get some page").get("/some.php")).check(status.is(404))
```
##### - Multiple checks:
```scala 
.check(status.not(400), status.not(404), status.not(500))
```
##### - Wiki:
    http://gatling.io/docs/2.0.0-RC2/general/concepts.html
 
### Scenario & Simulation
 
##### - Creating Scenario:
```scala 
val user1 = scenario("gatling 1").exec(http("get some page").get("/some.php"))
val user2 = scenario("gatling 2").exec(http("get another page").get("/another.php"))
``` 
##### - Apply Scenario to a simulation
```scala 
setUp(user1.inject(atOnceUsers(10)),
  user2.inject(atOnceUsers(25))).protocols(httpProtocol)
```
Wiki: http://gatling.io/docs/2.0.0-RC2/general/concepts.html
 
##### - Inject:
    2 scenarios , first is running all users at once, seconds is ramping up users starting with 100 and going up to 500 in 1 minute.
```scala 
setUp(scn.inject(atOnceUsers(10)), otherScn.inject(rampUsersPerSec(100) to(500) during(1 minutes) randomized))
```
Wiki: http://gatling.io/docs/2.0.0-RC2/general/simulation_setup.html#simulation-setup
 
### Feeders:
##### - create a feeder:
```scala 
val random = new util.Random
val feeder = Iterator.continually(Map("randomString" -> random.nextString(20)))
```
##### - use it in a scenario:
```scala 
val scn = scenario("some scenario")
				.feed(feeder)
				.exec(http("some request")
				.post("/nimrod.php")
				.formParam("someParam", "${randomString}")
```
Wiki: http://gatling.io/docs/2.0.0-RC2/session/feeder.html#feeder

link 2 solution
