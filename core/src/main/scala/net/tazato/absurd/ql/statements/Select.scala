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

import cats.syntax.all.*
import net.tazato.absurd.ql.traits.{LazyRender, Render}
import net.tazato.absurd.ql.types.*

sealed trait Select

/** See https://surrealdb.com/docs/surrealql/statements/select
  *
  * For documentation on the structure of a select statement in surrealql
  */
object Select extends Render[Select]:

  case class Limit(lmt: String)
  case class Start(strt: String)
  case class OrderClause(
      fields: Seq[Field],
      isRand: Boolean = false,
      isCollate: Boolean = false,
      isNumeric: Boolean = false,
      isAscending: Boolean = true
  ) extends LazyRender:
    override lazy val render: String =
      s"ORDER BY ${LazyRender.renderSeq(fields)}"
        ++ (if (isRand) " RAND()" else "")
        ++ (if (isCollate) " COLLATE" else "")
        ++ (if (isNumeric) " NUMERIC" else "")
        ++ (if (isAscending) " ASC" else " DESC")

  case class SELECT(
      isValueMode: Boolean = false,
      selectFields: Seq[Field],
      targets: Seq[Target],
      whereClause: Option[Seq[Cond]] = None,
      splitBy: Option[Field] = None,
      groupBy: Option[Seq[Field]] = None,
      orderClause: Option[OrderClause] = None,
      limitClause: Option[Limit] = None,
      startClause: Option[Start] = None,
      fetchClause: Option[Seq[Field]] = None,
      timeoutClause: Option[Duration] = None,
      isParallel: Boolean = false
  ) extends Select:
    lazy val render: String = Select.render(this)

  override def render(x: Select): String =
    import net.tazato.absurd.ql.traits.LazyRender.renderSeq
    x match
      case x: SELECT =>
        Seq[Option[String]](
          "SELECT".some,
          if (x.isValueMode) Some("VALUE") else None,
          renderSeq(x.selectFields).some,
          ("FROM " ++ renderSeq(x.targets)).some,
          x.whereClause.map(y => s"WHERE ${renderSeq(y)}"),
          x.splitBy.map(y => s"SPLIT AT ${y.fs}"),
          x.groupBy.map(y => s"GROUP BY ${renderSeq(y)}"),
          x.orderClause.map(_.render),
          x.limitClause.map(y => s"LIMIT BY ${y.lmt}"),
          x.startClause.map(y => s"START AT ${y.strt}"),
          x.fetchClause.map(y => s"FETCH ${renderSeq(y)}"),
          x.timeoutClause.map(y => s"TIMEOUT ${y.dur}"),
          if (x.isParallel) Some("PARALLEL") else None
        ).flatten.mkString(" ") ++ ";"

end Select
