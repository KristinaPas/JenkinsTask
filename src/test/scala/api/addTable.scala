package api

import io.gatling.core.structure._
import io.gatling.core.Predef._
import io.gatling.http.Predef._
import config.BaseHelper._

object addTable {
  def addTable(): ChainBuilder = {
    exec(
      http("POST Cart ID Options")
        .options(localhost + "api/v1/cart/?store=DEFAULT")
    )
    exec(
      http("POST Cart Id")
        .post(localhost + "api/v1/cart/?store=DEFAULT")
        .body(StringBody("""{"attributes":[],"product":"table1","quantity":1}""")).asJson
        .check(regex("Added Cart"))
        //.check(css("#code", "code").saveAs("cartId"))
        .check(jsonPath("$.code").is("$code"))
        .check(jsonPath("$.code").is("$code").saveAs("cartId"))
    )
    exec(
      http("Cart Options")
        .options(localhost + "api/v1/cart/${cartId}?lang=en")
        .check(status.is(200))
    )
    exec(
      http("GET Cart")
        .get(localhost + "api/v1/cart/" + cart + "?lang=en")
        .check(status.is(200))
    )
  }
}
