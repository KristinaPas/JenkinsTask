package api

import io.gatling.core.structure._
import io.gatling.core.Predef._
import io.gatling.http.Predef._
import config.BaseHelper._

object FromTableToCart {
  def fromTableToCart(): ChainBuilder = {
    exec(
      http("Actuator Options")
        .options(actuator)
    )
    exec(
      http("Actuator")
        .get(actuator)
    )
    exec(
      http("CartWithTable Options")
        .options(localhost + "GET/api/v1/cart/${cartId}?store=DEFAULT")
    )
    exec(
      http("CartWithTable")
        .get(localhost + "GET/api/v1/cart/${cartId}?store=DEFAULT")
        .check(substring("Your cart items"))
        .check(status.is(200))
    )
    exec(
      http("CartWithTable Category Options")
        .options(localhost + "api/v1/category/?count=20&page=0&store=DEFAULT&lang=en")
    )
    exec(
      http("CartWithTable Category")
        .get(localhost + "api/v1/category/?count=20&page=0&store=DEFAULT&lang=en")
        .check(status.is(200))
    )
    exec(
      http("CartWithTable Content Options")
        .options(localhost + "api/v1/content/pages/?page=0&count=20&store=DEFAULT&lang=en")
    )
    exec(
      http("CartWithTable Content")
        .get(localhost + "api/v1/content/pages/?page=0&count=20&store=DEFAULT&lang=en")
        .check(status.is(200))
    )
    exec(
      http("CartWithTable Store Options")
        .options(localhost + "api/v1/store/DEFAULT")
    )
    exec(
      http("CartWithTable Store")
        .get(localhost + "api/v1/store/DEFAULT")
        .check(status.is(200))
    )
    exec(
      http("CartWithTable Options")
        .options(localhost + "api/v1/cart/${cartId}?store=DEFAULT")
    )
    exec(
      http("CartWithTable")
        // I don't understand why jsonPath extraction doesn't work for this request
        .get(localhost + "api/v1/cart/" + cart + "?store=DEFAULT")
        .check(status.is(200))
    )
  }
}
