package net.tazato.surreal.ql.statements
import weaver._

object SelectSuite extends FunSuite {
  test("Select Statement: render works simple case") {
    val expected = """SELECT * FROM person;"""
    val rendered = Select.SELECT(selectFields = Seq(Select.Field("*")), targets = Seq(Select.Target("person"))).render
    expect.same(expected, rendered)
  }

  test("Select Statement: render works for more complex case") {
    val expected = """SELECT name, address, email FROM person:tobie;"""
    val rendered = Select
      .SELECT(
        selectFields = Seq(Select.Field("name"), Select.Field("address"), Select.Field("email")),
        targets = Seq(Select.Target("person:tobie"))
      )
      .render
    expect.same(expected, rendered)
  }

  test("Select Statement: render works with alias") {
    val expected = """SELECT name AS user_name, address FROM person;"""
    val rendered = Select
      .SELECT(
        selectFields = Seq(Select.Field("name", Some(Select.Alias("user_name"))), Select.Field("address")),
        targets = Seq(Select.Target("person"))
      )
      .render
    expect.same(expected, rendered)
  }

}
