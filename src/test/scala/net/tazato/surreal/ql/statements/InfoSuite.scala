package net.tazato.surreal.ql.statements

import munit.FunSuite
import net.tazato.surreal.ql.Keywords

class InfoSuite extends FunSuite {
  import Keywords._
  def infoStatementHappyTester(k: Keywords, ident: Option[String] = None): Unit = {
    val expectedStart = s"INFO FOR ${k.value}"
    val expectedEnd = ident match {
      case Some(id) => s" $id;"
      case None     => ";"
    }
    val expected = expectedStart ++ expectedEnd
    val query    = Info.infoStatement(k, ident)
    query match {
      case Right(a) => assert(a == expected)
      case Left(_)  => fail("can never happen, maybe wrong keyword?")
    }
  }

  test("Info Statement: no argument keywords return correct statements") {
    infoStatementHappyTester(KV)
    infoStatementHappyTester(NS)
    infoStatementHappyTester(NAMESPACE)
    infoStatementHappyTester(DB)
    infoStatementHappyTester(DATABASE)
  }

  test("Info Statement: one argument keyword returns correct statements") {
    infoStatementHappyTester(SCOPE, Some("tazato"))
    infoStatementHappyTester(TABLE, Some("thanksgiving"))
  }

  test("Info Statement: bad info statement is a Left") {
    // INFO FROM INFO; isn't valid SurrealQL
    val statement = Info.infoStatement(INFO)
    assert(statement.isLeft)

    // INFO FROM SCOPE; without argument isn't valid SurrealQL
    val noArg = Info.infoStatement(SCOPE, None)
    assert(noArg.isLeft)
  }
}
