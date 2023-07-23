package net.tazato.surreal.ql.statements

import net.tazato.surreal.ql.statements.Define
import net.tazato.surreal.ql.types.*
import weaver._

object DefineSuite extends FunSuite:
  import Define._
  test("DEFINE NAMESPACE works") {
    val expected = "DEFINE NAMESPACE tazato;"
    val rendered = Define.render(NAMESPACE("tazato"))
    assert(expected == rendered)
  }

  test("DEFINE DATABASE works") {
    val expected = "DEFINE DATABASE tazato;"
    val rendered = Define.render(DATABASE("tazato"))
    assert(expected == rendered)
  }

  test("DEFINE SCOPE works") {
    val expected = "DEFINE SCOPE taz SESSION sexy SIGNUP taz;"
    val rendered = Define.render(SCOPE("taz", Some("sexy"), Some("taz"), None))
    assert(expected == rendered)
  }

  test("DEFINE LOGIN works") {
    val expected = "DEFINE LOGIN taz ON NAMESPACE PASSHASH fakehashrofl;"
    val rendered = Define.render(LOGIN("taz", Base.NAMESPACE, "fakehashrofl"))
    assert(expected == rendered)
  }

  test("DEFINE TOKEN works") {
    val expected = "DEFINE TOKEN tazato ON NS TYPE RS256 VALUE fakevaluerofl;"
    val rendered = Define.render(TOKEN("tazato", Base.NS, Algo.Rs256, "fakevaluerofl"))
    assert(expected == rendered)
  }

  test("DEFINE SCOPE works, just a name") {
    val expected = "DEFINE SCOPE taz;"
    val rendered = Define.render(SCOPE("taz"))
    assert(expected == rendered)
  }

  test("DEFINE SCOPE works, a couple optionals") {
    val expected = "DEFINE SCOPE taz SIGNIN taz;"
    val rendered = Define.render(SCOPE("taz", signin = Some("taz")))
    assert(expected == rendered)
  }

  test("DEFINE TABLE with simple name, no schema works TODO fix permissions") {
    val expected = "DEFINE TABLE taz SCHEMALESS PERMISSIONS FULL;"
    val rendered = Define.render(TABLE("taz"))
    assert(expected == rendered)
  }

  test("DEFINE EVENT works") {
    val expected = "DEFINE EVENT taz ON kitchen WHEN needed THEN dinner;"
    val rendered = Define.render(EVENT("taz", "kitchen", "needed", "dinner"))
    assert(expected == rendered)
  }

  test("DEFINE FIELD works TODO fix permissions") {
    val expected = "DEFINE FIELD taz ON kitchen TYPE float VALUE 0.0 PERMISSIONS FULL;"
    val rendered = Define.render(FIELD("taz", "kitchen", kind = Some("float"), value = Some("0.0")))
    assert(expected == rendered)
  }

  test("DEFINE INDEX works") {
    val expected = "DEFINE INDEX taz ON kitchen FIELDS fork, knife UNIQUE;"
    val rendered = Define.render(INDEX("taz", "kitchen", cols = "fork, knife", uniq = true))
    assert(expected == rendered)
  }
