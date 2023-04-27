package api

import io.gatling.core.structure._
import io.gatling.core.Predef._
import io.gatling.http.Predef._
import config.BaseHelper._

object FromChairToCart {
  def fromChairToCart(): ChainBuilder = {
    exec(
      http("Actuator Options")
        .options(actuator)
    )
    exec(
      http("Actuator")
        .get(actuator)
    )
    exec(
      http("Cart Options")
        .options(localhost + "api/v1/cart/${cartId}?store=DEFAULT")
    )
    exec(
      http("Cart Get")
        .get(localhost + "api/v1/cart/${cartId}?store=DEFAULT")
        .check(status.is(200))
        .check(substring("Your cart items"))
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
    exec(
      http("Cart Get")
        .get(localhost + "api/v1/cart/" + cart + "?store=DEFAULT")
        .check(status.is(200))
    )
  }

}
