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

import weaver.*

object InfoSuite extends FunSuite:
  // two extra spaces to align with the rest of the ql test suites
  test("Info   Statement: render works, no argument keywords") {
    import net.tazato.absurd.ql.statements.Info.*
    val expected = "INFO FOR NS;"
    val rendered = Info.render(NS)
    expect.same(expected, rendered)
  }

  test("Info   Statement: render works, single argument keywords") {
    import net.tazato.absurd.ql.statements.Info.*
    val expected = "INFO FOR TABLE person;"
    val rendered = Info.render(TABLE("person"))
    expect.same(expected, rendered)
  }
