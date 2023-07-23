package net.tazato.surreal.ql.statements

import net.tazato.surreal.ql.statements.Info
import weaver._

object InfoSuite extends FunSuite:
  test("Info Statement: no argument keywords return correct statements") {
    import Info._
    val expected = "INFO FOR NS;"
    val rendered = Info.render(NS)
    expect.same(expected, rendered)
  }

  test("Info Statement: single argument keywords return correct statements") {
    import Info._
    val expected = "INFO FOR TABLE person;"
    val rendered = Info.render(TABLE("person"))
    expect.same(expected, rendered)
  }
