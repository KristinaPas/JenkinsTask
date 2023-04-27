package config

import io.gatling.core.Predef._
import io.gatling.core.structure._
import io.gatling.http.Predef._

object BaseHelper {

  val localhost = "http://localhost:8080/"
  val actuator = "http://localhost:8080/actuator/health/ping/"

  val table = "1"
  val chair1 = "50"
  val chair2 = "51"
  val chair3 = "52"
  val cart = "951ade9ae74a4fde9a15885cb2fabaa1"


  val productFeeder = csv("data/productDetails.csv").circular
  val chairFeeder = csv("data/chairsDetails.csv").circular
  val tableFeeder = csv("data/tableDetails.csv").circular
  val randomChairFeeder = csv("data/randomChair.csv").circular

  val jsonFeederTable = jsonFile("data/tableID.json").circular

  def thinkTimer(Min :Int = 2, Max :Int = 5): ChainBuilder = {
    pause(Min, Max)

  }

  val httpProtocol = http
    .acceptHeader("*/*")
    .acceptEncodingHeader("gzip, deflate, br")
    .acceptLanguageHeader("en-GB,en-US;q=0.9,en;q=0.8")
    .baseUrl("http:localhost:8080")
    .inferHtmlResources(BlackList(""".*\.js""", """.*\.css""", """.*\.gif""", """.*\.jpeg""", """.*\.jpg""", """.*\.ico""", """.*\.woff""", """.*\.woff2""", """.*\.(t|o)tf""", """.*\.png""", """.*detectportal\.firefox\.com.*"""), WhiteList())

}
