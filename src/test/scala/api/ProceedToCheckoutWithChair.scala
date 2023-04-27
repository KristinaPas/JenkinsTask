package api

import io.gatling.core.structure._
import io.gatling.core.Predef._
import io.gatling.http.Predef._
import config.BaseHelper._

object ProceedToCheckoutWithChair {
  def proceedToCheckoutWithChair(): ChainBuilder = {
    exec(
      http("Actuator Options")
        .options(actuator)
    )
    exec(
      http("Actuator")
        .get(actuator)
    )
    exec(
      http("Shipping Options")
        .options(localhost + "api/v1/shipping/country?store=DEFAULT&lang=en")
    )
    exec(
      http("Shipping")
        .get(localhost + "api/v1/shipping/country?store=DEFAULT&lang=en")
        .check(status.is(200))
    )
    exec(
      http("Zones Options")
        .options(localhost + "api/v1/zones/?code=")
    )
    exec(
      http("Zones")
        .get(localhost + "api/v1/zones/?code=")
        .check(status.is(200))
    )
    exec(
      http("Cart Options")
        .options(localhost + "api/v1/cart/" + cart + "?store=DEFAULT")
    )
    exec(
      http("Cart")
        .get(localhost + "api/v1/cart/" + cart + "?store=DEFAULT")
        .check(status.is(200))
        .check(substring("Your order"))
    )
    exec(
      http("Config Options")
        .options(localhost + "/api/v1/config/")
    )
    exec(
      http("Config")
        .get(localhost + "/api/v1/config/")
        .check(status.is(200))
    )
    exec(
      http("Cart Total Options")
        .options(localhost + "api/v1/cart/" + cart + "/total/")
    )
    exec(
      http("Cart Total")
        .get(localhost + "api/v1/cart/" + cart + "/total/")
        .check(status.is(200))
    )
    exec(
      http("Zones Code Options")
        .options(localhost + "api/v1/zones/?code=")
    )
    exec(
      http("Zones Code")
        .get(localhost + "api/v1/zones/?code=")
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
      http("Store Options")
        .options(localhost + "api/v1/store/DEFAULT")
    )
    exec(
      http("Store")
        .get(localhost + "api/v1/store/DEFAULT")
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
  }
}
