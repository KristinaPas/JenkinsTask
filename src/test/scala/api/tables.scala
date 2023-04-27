package api

import io.gatling.core.structure._
import io.gatling.core.Predef._
import io.gatling.http.Predef._
import config.BaseHelper._
import api.home._


object tables {
  def tables(): ChainBuilder = {
    feed(jsonFeederTable)
        .exec(
          http("Actuator Options")
          .options(actuator)
    )
    exec(
      http("Actuator")
        .get(actuator)
    )
    exec(
      http("Products Options")
        .options(localhost + "api/v1/products/?&store=DEFAULT&lang=en&page=0&count=15&category=50")
    )
    exec(
      http("Products")
        .get(localhost + "api/v1/products/?&store=DEFAULT&lang=en&page=0&count=15&category=50")
        .check(status.is(200))
        .check(substring("Featured Products"))
        .check(jsonPath("$.products[0].id").is("1"))
       // .check(jsonPath("$.products[0].id").saveAs("tableID"))
    )
    exec(
      http("Category Options")
        .options(localhost + "api/v1/category/?count=20&page=0&store=DEFAULT&lang=en")
    )
    exec(
      http("Category")
        .get(localhost + "api/v1/category/?count=20&page=0&store=DEFAULT&lang=en")
        .check(status.is(200))
        //.check(jsonPath("$.categories[0].id").saveAs("table"))
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
      http("Store Options")
        .options(localhost + "api/v1/store/DEFAULT")
    )
    exec(
      http("Store")
        .get(localhost + "api/v1/store/DEFAULT")
        .check(status.is(200))
    )
    exec(
      http("Table Options")
        .options(localhost + "api/v1/product/" + table + "/price/")
    )
    exec(
      http("POST Table")
        .post(localhost + "api/v1/product/" + table + "/price/")
        .body(StringBody("""{"options":[]}""")).asJson
        .check(status.is(200))
    )
  }
}
