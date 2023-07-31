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

import net.tazato.absurd.ql.traits.*
import net.tazato.absurd.ql.types.*

import cats.syntax.all._

sealed trait Create

object Create extends Render[Create]:
  enum ReturnMode(val value: String) extends LazyRender:
    case NONE                        extends ReturnMode("NONE")
    case BEFORE                      extends ReturnMode("BEFORE")
    case AFTER                       extends ReturnMode("AFTER")
    case DIFF                        extends ReturnMode("DIFF")
    case PROJECTIONS(x: Seq[String]) extends ReturnMode("")
    override lazy val render: String =
      this match
        case NONE | BEFORE | AFTER | DIFF => s"RETURN ${this.value}"
        case PROJECTIONS(x)               => s"RETURN ${x.mkString(", ")}"
  end ReturnMode

  def renderFieldsWithValues(fwv: Seq[(Field, ValueLike)]): String =
    fwv.map(x => s"${x._1.render} = ${x._2.render}").mkString(", ")

  case class CREATE(
      createTargets: Seq[Target],
      // TODO: this is another spot where SurrealDB accepts
      // json-ish objects. Should handle with circe.json maybe
      content: Option[String] = None,
      fieldsWithValues: Seq[(Field, ValueLike)] = Seq.empty,
      returnMode: Option[ReturnMode] = None,
      timeout: Option[Duration] = None,
      isParallel: Boolean = false
  ) extends Create
      with LazyRender:
    override lazy val render: String = Create.render(this)

  override def render(x: Create): String = x match
    case x: CREATE =>
      Seq[Option[String]](
        "CREATE".some,
        LazyRender.renderSeqOption(x.createTargets),
        x.content.map(y => s"CONTENT $y"),
        if (x.fieldsWithValues.isEmpty) None
        else Some("SET " ++ renderFieldsWithValues(x.fieldsWithValues)),
        x.returnMode.map(_.render),
        x.timeout.map(_.dur),
        if (x.isParallel) Some("PARALLEL") else None
      ).flatten.mkString(" ") ++ ";"
