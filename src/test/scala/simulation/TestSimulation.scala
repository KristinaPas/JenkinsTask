package simulation

import scenarios.Scenario._
import io.gatling.core.structure.ChainBuilder
import io.gatling.core.Predef._
import config.BaseHelper._
import api._

class TestSimulation extends Simulation {

  //mvn gatling:test
  //mvn clean gatling:test
  //mvn clean gatling:test -DlocalhostUsers=5

  setUp(
    scnScenario.inject(atOnceUsers(System.getProperty("LocalhostUsers", "1").toInt)),
    scnScenario2.inject(atOnceUsers(System.getProperty("CartTableOnly", "1").toInt)),
    scnScenario3.inject(atOnceUsers(System.getProperty("CartWithChairOnly", "1").toInt))
  )
}

//  setUp(
//    scnScenario.inject(atOnceUsers(1))
//  ).protocols(httpProtocol)
//}

//val allusers = scenario("allusers").exec(home.Home(), tables.tables(), openTable.openTable(), addTable.addTable())

//val fiftypercent = scenario("fiftypercent").exec(FromTableToCart.fromTableToCart(), ProceedToCheckout.proceedToCheckout())

//val thirtypercent = scenario("thirtypercent").exec(Chairs.chairs(), OpenRandomChair.openRandomChair(), addChair.addChair(), FromChairToCart.fromChairToCart(), ProceedToCheckoutWithChair.proceedToCheckoutWithChair())

//  setUp(allusers.inject(atOnceUsers(10)),
//  fiftypercent.inject(atOnceUsers(5)),
//  thirtypercent.inject(atOnceUsers(3))).protocols(httpProtocol)
//}

//setUp(
  //scnScenario.inject(atOnceUsers(System.getProperty("localhostUsers", "1").toInt))
//).protocols(httpProtocol)
