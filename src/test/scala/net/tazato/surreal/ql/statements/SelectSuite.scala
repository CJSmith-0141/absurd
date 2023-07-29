package net.tazato.surreal.ql.statements
import weaver._

object SelectSuite extends FunSuite {
  import Select.{Target => Targ, *}
  test("Select Statement: render works, simple case") {
    val expected = """SELECT * FROM person;"""
    val rendered = SELECT(selectFields = Seq(Field("*")), targets = Seq(Targ("person"))).render
    expect.same(expected, rendered)
  }

  test("Select Statement: render works, complex case from documentation") {
    val expected = """SELECT name, address, email FROM person:tobie;"""
    val rendered = Select
      .SELECT(
        selectFields = Seq(Field("name"), Field("address"), Field("email")),
        targets = Seq(Targ("person:tobie"))
      )
      .render
    expect.same(expected, rendered)
  }

  test("Select Statement: render works, alias") {
    val expected = """SELECT name AS user_name, address FROM person;"""
    val rendered = Select
      .SELECT(
        selectFields = Seq(Field("name", Some(Alias("user_name"))), Field("address")),
        targets = Seq(Targ("person"))
      )
      .render
    expect.same(expected, rendered)
  }

  test("Select Statement: render works, split clause") {
    val expected = """SELECT * FROM user SPLIT AT emails;"""
    val rendered =
      SELECT(selectFields = Seq(Field("*")), targets = Seq(Targ("user")), splitBy = Some(Field("emails"))).render
    expect.same(expected, rendered)
  }

  test("Select Statement: render works, simple where clause") {
    val expected = """SELECT * FROM article WHERE published = true;"""
    val rendered = SELECT(
      selectFields = Seq(Field("*")),
      targets = Seq(Targ("article")),
      whereClause = Some(Seq(Cond("published = true")))
    ).render
    expect.same(expected, rendered)
  }

}
