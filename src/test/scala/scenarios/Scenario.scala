package scenarios

import config.BaseHelper._
import io.gatling.core.structure._
import io.gatling.core.Predef._
import io.gatling.http.Predef._

object Scenario {
  def scnScenario: ScenarioBuilder = {
    scenario("Localhost")
      .exec(flushHttpCache)
      .exec(flushCookieJar)
      .exitBlockOnFail(
        group("Home") {
          exec(api.home.Home())
            .exec(thinkTimer())
        }
          .group("Tables") {
            exec((api.tables.tables()))
              .exec(thinkTimer())
          }
          .group("openTable") {
            exec(api.openTable.openTable())
              .exec(thinkTimer())
          }
          .group("addTable") {
            exec(api.addTable.addTable())
          }

      )
  }
  def scnScenario2: ScenarioBuilder = {
    scenario("TableCart30Percent")
      .exec(flushHttpCache)
      .exec(flushCookieJar)
      .exitBlockOnFail(
        group("FromTableToCart") {
        exec(api.FromTableToCart.fromTableToCart())
         }
       .group("ProceedToCheckout") {
        exec(api.ProceedToCheckout.proceedToCheckout())
         }
      )
  }
  def scnScenario3: ScenarioBuilder = {
    scenario("Chair50Percent")
      .exec(flushHttpCache)
      .exec(flushCookieJar)
      .exitBlockOnFail(
        group("Chairs") {
        exec(api.Chairs.chairs())
         }
        .group("OpenRandomChair") {
         exec(api.OpenRandomChair.openRandomChair())
        }
        .group("addChair") {
          exec(api.addChair.addChair())
         }
        .group("FromChairToCart") {
         exec(api.FromChairToCart.fromChairToCart())
         }
       .group("ProceedToCheckoutWithChair") {
        exec(api.ProceedToCheckoutWithChair.proceedToCheckoutWithChair())
         }
      )

  }
}

