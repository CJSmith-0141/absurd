package net.tazato.surreal.ql.statements

import munit.FunSuite
import net.tazato.surreal.ql.statements.Info

class InfoSuite extends FunSuite:
  test("Info Statement: no argument keywords return correct statements") {
    import Info._
    val expected = "INFO FOR NS;"
    val rendered = Info.render(NS)
    assert(expected == rendered)
  }

  test("Info Statement: single argument keywords return correct statements") {
    import Info._
    val expected = "INFO FOR TABLE person;"
    val rendered = Info.render(TABLE("person"))
    assert(expected == rendered)
  }
