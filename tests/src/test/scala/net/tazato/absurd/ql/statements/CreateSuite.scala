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

import net.tazato.absurd.ql.types.*
import net.tazato.absurd.ql.types.ValueLike.Jsonish
import net.tazato.absurd.ql.types.ValueLike.Stringish
import weaver.*
object CreateSuite extends FunSuite {
  test("Create Statement: render works, fields example") {
    val expected =
      """CREATE person SET name = 'Tobie', company = 'SurrealDB', skills = ['Rust', 'Go', 'JavaScript'];"""
    val rendered = Create
      .CREATE(
        createTargets = Seq(Target("person")),
        fieldsWithValues = Seq(
          (Field("name"), Stringish("Tobie")),
          (Field("company"), Stringish("SurrealDB")),
          (Field("skills"), Jsonish("""['Rust', 'Go', 'JavaScript']"""))
        )
      )
      .render
    expect.same(expected, rendered)
  }

  test("Create Statement: render works, content example") {
    val expected = """CREATE person CONTENT {
                |	name: 'Tobie',
                |	company: 'SurrealDB',
                |	skills: ['Rust', 'Go', 'JavaScript'],
                |};""".stripMargin
    val rendered = Create
      .CREATE(
        createTargets = Seq(Target("person")),
        content = Some("""{
                         |	name: 'Tobie',
                         |	company: 'SurrealDB',
                         |	skills: ['Rust', 'Go', 'JavaScript'],
                         |}""".stripMargin)
      )
      .render

    expect.same(expected, rendered)
  }

}
