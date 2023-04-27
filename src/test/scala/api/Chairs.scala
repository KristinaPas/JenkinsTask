package api

import io.gatling.core.structure._
import io.gatling.core.Predef._
import io.gatling.http.Predef._
import config.BaseHelper._

object Chairs {
  def chairs(): ChainBuilder = {
    feed(chairFeeder)
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
        .options(localhost + "api/v1/products/?&store=DEFAULT&lang=en&page=0&count=15&category=51")
    )
    exec(
      http("Products")
        .get(localhost + "api/v1/products/?&store=DEFAULT&lang=en&page=0&count=15&category=51")
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
        .check(css("/product/chair-beige").is("Chair Beige"))
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
      http("Chair1 Options")
        .options(localhost +"api/v1/product/${chairID}/price/")
    )
    exec(
      http("Chair1 Post")
        .post(localhost + "api/v1/product/${chairID}/price/")
        .body(StringBody("""{"options":[]}""")).asJson
        .check(status.is(200))
    )
    exec(
      http("Chair2 Options")
        .options(localhost + "api/v1/product/${chairID}/price/")
    )
    exec(
      http("Chair2 Post")
        .post(localhost + "api/v1/product/${chairID}/price/")
        .body(StringBody("""{"options":[]}""")).asJson
        .check(status.is(200))
    )
    exec(
      http("Category Chair2 Options")
        .options(localhost + "api/v1/category/${chairID}?store=DEFAULT&lang=en")
    )
    exec(
      http("Category Chair2")
        .get(localhost + "api/v1/category/" + chair2 + "?store=DEFAULT&lang=en")
        .check(status.is(200))
    )
    exec(
      http("Chair3 Options")
        .options(localhost + "api/v1/product/${chairID}/price/")
    )
    exec(
      http("Chair3 Post")
        .post(localhost + "api/v1/product/${chairID}/price/")
        .body(StringBody("""{"options":[]}""")).asJson
        .check(status.is(200))
    )
    exec(
      http("Category Chair2 Options")
        .options(localhost + "api/v1/category/${chairID}/manufacturers/?store=DEFAULT&lang=en")
    )
    exec(
      http("Category Chair2")
        .get(localhost + "api/v1/category/" + chair2 + "/manufacturers/?store=DEFAULT&lang=en")
        .check(status.is(200))
    )
  }

}
