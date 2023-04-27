package api

import io.gatling.core.structure._
import io.gatling.core.Predef._
import io.gatling.http.Predef._
import config.BaseHelper._

object home {
  def Home(): ChainBuilder = {
    feed(productFeeder)
      .exec(
         http("Home")
        .get(localhost)
        .check(status.is(200))
        .check(regex("2021-2023 Default store"))
    )
    exec(
      http("Actuator Options")
        .options(actuator)
    )
    exec(
      http("Actuator")
        .get(actuator)
    )
    exec(
      http("Product Group Options")
        .get(localhost + "api/v1/products/group/FEATURED_ITEM?store=DEFAULT&lang=en")
        .check(status.is(200))
    )
    exec(
      http("Product Group")
        .get(localhost + "api/v1/products/group/FEATURED_ITEM?store=DEFAULT&lang=en")
        .check(status.is(200))
        //.check(jsonPath("$.products[*].id").findAll.saveAs("productID"))
        //.check(jsonPath("$.products[0].id").saveAs("tableID"))
        // .check(jsonPath("$.products[1].id").saveAs("chair1ID"))
        //.check(jsonPath("$.products[2].id").saveAs("chair2ID"))
        //.check(jsonPath("$.products[3].id").saveAs("chair3ID"))
    )
    exec(
      http("Category Options")
        .options(localhost + "api/v1/category/?count=20&page=0&store=DEFAULT&lang=en")
    )
    exec(
      http("Category")
        .get(localhost + "api/v1/category/?count=20&page=0&store=DEFAULT&lang=en")
        .check(status.is(200))
       // .check(jsonPath("$.categories[0].id").saveAs("tableId"))
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
      http("Options Table")
        .options(localhost + "api/v1/product/${productID}/price/")
    )
    exec(
      http("POST Table")
        .post(localhost + "api/v1/product/${productID}/price/")
        .body(StringBody("""{"options": []}""")).asJson
        .check(status.is(200))
    )
    exec(
      http("Options Chair3")
        .options(localhost + "api/v1/product/${productID}/price/")
    )
    exec(
      http("POST Chair3")
        .post(localhost + "api/v1/product/${productID}/price/")
        .body(StringBody("""{"options": []}""")).asJson
        .check(status.is(200))
    )
    exec(
      http("Options Chair2")
        .options(localhost + "api/v1/product/${productID}/price/")
    )
    exec(
      http("POST Chair2")
        .post(localhost + "api/v1/product/${productID}/price/")
        .body(StringBody("""{"options": []}""")).asJson
        .check(status.is(200))
    )
    exec(
      http("Options Chair1")
        .options(localhost + "api/v1/product/${productID}/price/")
    )
    exec(
      http("POST Chair1")
        // I don't understand why feed doesn't work for this request
        .post(localhost + "api/v1/product/" + chair1 + "/price/")
        .body(StringBody("""{"options": []}""")).asJson
        .check(status.is(200))
    )
  }
}
