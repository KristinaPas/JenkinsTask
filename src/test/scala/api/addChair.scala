package api

import io.gatling.core.structure._
import io.gatling.core.Predef._
import io.gatling.http.Predef._
import config.BaseHelper._

object addChair {
  def addChair(): ChainBuilder = {
    exec(
      http("Cart Options")
        .options(localhost + "api/v1/cart/${cartId}?store=DEFAULT")
    )
    exec(
      http("Cart Put")
        .put(localhost + "api/v1/cart/${cartId}?store=DEFAULT")
        .body(StringBody("""{"attributes":[],"product":"chair2","quantity":1}""")).asJson
        .check(status.is(200))
    )
    exec(
      http("Cart Options")
        .options(localhost + "api/v1/cart/${cartId}?lang=en")
    )
    exec(
      http("Cart Get")
        .get(localhost + "api/v1/cart/" + cart + "?lang=en")
        .check(status.is(200))
    )
  }
}
