package api

import io.gatling.core.structure._
import io.gatling.core.Predef._
import io.gatling.http.Predef._
import config.BaseHelper._

object OpenRandomChair {
  def openRandomChair(): ChainBuilder = {
    feed(randomChairFeeder)
      .exec(
        http("Actuator Options")
          .options(actuator)
    )
    exec(
      http("Actuator")
        .get(actuator)
    )
    exec(
      http("Products Chair Options")
        .options(localhost + "api/v1/products/${ID" +
          "}/reviews?store=DEFAULT")
    )
    exec(
      http("Products Chair")
        .get(localhost + "api/v1/products/${ID}/reviews?store=DEFAULT")
        .check(status.is(200))
        .check(substring("Description"))
    )
    exec(
      http("Products Chair")
        .get(localhost + "api/v1/products/${ID}?lang=en&store=DEFAULT")
    )
    exec(
      http("Store Options")
        .options(localhost + "api/v1/store/DEFAULT")
    )
    exec(
      http("Store")
        .get(localhost + "api/v1/store/DEFAULT")
        .check(status.is(200))
    )
    exec(
      http("Category Options")
        .options(localhost + "api/v1/category/?count=20&page=0&store=DEFAULT&lang=en")
    )
    exec(
      http("Category")
        .get(localhost + "api/v1/category/?count=20&page=0&store=DEFAULT&lang=en")
        .check(status.is(200))
    )
    exec(
      http("Content Options")
        .options(localhost + "api/v1/content/pages/?page=0&count=20&store=DEFAULT&lang=en")
    )
    exec(
      http("Content")
        .get(localhost + "api/v1/content/pages/?page=0&count=20&store=DEFAULT&lang=en")
        .check(status.is(200))
    )
    exec(
      http("Chair Price Options")
        .options(localhost + "api/v1/product/${ID}/price/")
    )
    exec(
      http("Chair Price")
        // I don't understand why feed doesn't work for this request
        .post(localhost + "api/v1/product/" + chair1 + "/price/")
        .body(StringBody("""{"options":[]}""")).asJson
        .check(status.is(200))
    )
  }

}
