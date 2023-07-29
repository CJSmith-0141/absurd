/*
 * Copyright 2023 Connor James Smith
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package net.tazato.absurd.ql.statements

import net.tazato.absurd.ql.types.{Algo, Base}
import weaver.*

object DefineSuite extends FunSuite:
  test("Define Statement: render works, namespace") {
    val expected = "DEFINE NAMESPACE tazato;"
    val rendered = Define.NAMESPACE("tazato").render
    expect.same(expected, rendered)
  }

  test("Define Statement: render works, database") {
    val expected = "DEFINE DATABASE tazato;"
    val rendered = Define.DATABASE("tazato").render
    expect.same(expected, rendered)
  }

  test("Define Statement: render works, function") {
    val expected =
      """DEFINE FUNCTION fn::greet($name: string) {
	      |  RETURN "Hello, " + $name + "!";
        |};""".stripMargin
    val rendered = Define
      .FUNCTION(
        "greet",
        Seq(("$name", "string")),
        """{
        |  RETURN "Hello, " + $name + "!";
        |}""".stripMargin
      )
      .render
    assert(expected == rendered)
  }

  test("Define Statement: render works, scope") {
    val expected = "DEFINE SCOPE taz SESSION sexy SIGNUP taz;"
    val rendered = Define.SCOPE("taz", Some("sexy"), Some("taz"), None).render
    expect.same(expected, rendered)
  }

  test("Define Statement: render works, login on namespace") {
    val expected = "DEFINE LOGIN taz ON NAMESPACE PASSHASH fakehashrofl;"
    val rendered = Define.LOGIN("taz", Base.NAMESPACE, "fakehashrofl").render
    expect.same(expected, rendered)
  }

  test("Define Statement: render works, token") {
    val expected = "DEFINE TOKEN tazato ON NS TYPE RS256 VALUE fakevaluerofl;"
    val rendered =
      Define.TOKEN("tazato", Base.NS, Algo.Rs256, "fakevaluerofl").render
    expect.same(expected, rendered)
  }

  test("Define Statement: render works, scope") {
    val expected = "DEFINE SCOPE taz;"
    val rendered = Define.SCOPE("taz").render
    expect.same(expected, rendered)
  }

  test("Define Statement: render works, a couple optionals") {
    val expected = "DEFINE SCOPE taz SIGNIN taz;"
    val rendered = Define.SCOPE("taz", signin = Some("taz")).render
    expect.same(expected, rendered)
  }

  test(
    "Define Statement: render works, table without schema TODO fix permissions"
  ) {
    val expected = "DEFINE TABLE taz SCHEMALESS PERMISSIONS FULL;"
    val rendered = Define.TABLE("taz").render
    expect.same(expected, rendered)
  }

  test("Define Statement: render works, event") {
    val expected = "DEFINE EVENT taz ON kitchen WHEN needed THEN dinner;"
    val rendered = Define.EVENT("taz", "kitchen", "needed", "dinner").render
    expect.same(expected, rendered)
  }

  test("Define Statement: render works, field TODO fix permissions") {
    val expected =
      "DEFINE FIELD taz ON kitchen TYPE float VALUE 0.0 PERMISSIONS FULL;"
    val rendered = Define
      .FIELD("taz", "kitchen", kind = Some("float"), value = Some("0.0"))
      .render
    expect.same(expected, rendered)
  }

  test("Define Statement: render works, index") {
    val expected = "DEFINE INDEX taz ON kitchen FIELDS fork, knife UNIQUE;"
    val rendered =
      Define.INDEX("taz", "kitchen", cols = "fork, knife", uniq = true).render
    expect.same(expected, rendered)
  }
