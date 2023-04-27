package api

import io.gatling.core.structure._
import io.gatling.core.Predef._
import io.gatling.http.Predef._
import config.BaseHelper._

object openTable {
  def openTable(): ChainBuilder = {
    feed(tableFeeder)
    exec(
      http("Actuator Options")
        .options(actuator)
    )
    exec(
      http("Actuator")
        .get(actuator)
    )
    exec(
      http("Products Table Options")
        .options(localhost + "api/v1/products/${tableID}?lang=en&store=DEFAULT")
    )
    exec(
      http("Products Table")
        .get(localhost + "api/v1/products/" + table + "?lang=en&store=DEFAULT")
        .check(status.is(200))
        .check(substring("Olive Table"))
    )
  }
}
