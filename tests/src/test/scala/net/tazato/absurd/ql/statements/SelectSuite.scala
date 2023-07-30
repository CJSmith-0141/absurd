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

import net.tazato.absurd.ql.types.{Target => Trg, *}
import net.tazato.absurd.ql.statements.Select.SELECT
import weaver._

object SelectSuite extends FunSuite {
  test("Select Statement: render works, simple case") {
    val expected = """SELECT * FROM person;"""
    val rendered = SELECT(
      selectFields = Seq(Field("*")),
      targets = Seq(Trg("person"))
    ).render
    expect.same(expected, rendered)
  }

  test("Select Statement: render works, complex case from documentation") {
    val expected = """SELECT name, address, email FROM person:tobie;"""
    val rendered = Select
      .SELECT(
        selectFields = Seq(Field("name"), Field("address"), Field("email")),
        targets = Seq(Trg("person:tobie"))
      )
      .render
    expect.same(expected, rendered)
  }

  test("Select Statement: render works, alias") {
    val expected = """SELECT name AS user_name, address FROM person;"""
    val rendered = Select
      .SELECT(
        selectFields =
          Seq(Field("name", Some(Alias("user_name"))), Field("address")),
        targets = Seq(Trg("person"))
      )
      .render
    expect.same(expected, rendered)
  }

  test("Select Statement: render works, split clause") {
    val expected = """SELECT * FROM user SPLIT AT emails;"""
    val rendered =
      SELECT(
        selectFields = Seq(Field("*")),
        targets = Seq(Trg("user")),
        splitBy = Some(Field("emails"))
      ).render
    expect.same(expected, rendered)
  }

  test("Select Statement: render works, simple where clause") {
    val expected = """SELECT * FROM article WHERE published = true;"""
    val rendered = SELECT(
      selectFields = Seq(Field("*")),
      targets = Seq(Trg("article")),
      whereClause = Some(Seq(Cond("published = true")))
    ).render
    expect.same(expected, rendered)
  }

}
