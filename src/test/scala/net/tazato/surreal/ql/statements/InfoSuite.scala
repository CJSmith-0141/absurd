package net.tazato.surreal.ql.statements

import net.tazato.surreal.ql.statements.Info
import weaver._

object InfoSuite extends FunSuite:
  // two extra spaces to align with the rest of the ql test suites
  test("Info   Statement: render works, no argument keywords") {
    import Info._
    val expected = "INFO FOR NS;"
    val rendered = Info.render(NS)
    expect.same(expected, rendered)
  }

  test("Info   Statement: render works, single argument keywords") {
    import Info._
    val expected = "INFO FOR TABLE person;"
    val rendered = Info.render(TABLE("person"))
    expect.same(expected, rendered)
  }
