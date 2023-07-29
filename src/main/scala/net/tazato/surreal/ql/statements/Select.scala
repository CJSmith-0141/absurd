package net.tazato.surreal.ql.statements

import net.tazato.surreal.ql.types.Render
import net.tazato.surreal.ql.types.LazyRender
import cats.syntax.all.*

sealed trait Select

/*
 * See https://surrealdb.com/docs/surrealql/statements/select
 *
 * For documentation on the structure of a select statement in surrealql
 * */

object Select extends Render[Select]:
  case class Field(fs: String, alias: Option[Alias] = None) extends LazyRender:
    override lazy val render =
      alias match
        case Some(a) => s"${this.fs} ${a.render}"
        case None    => this.fs

  case class Alias(als: String) extends LazyRender:
    override lazy val render = s"AS ${this.als}"
  case class Target(trg: String) extends LazyRender:
    override lazy val render = trg
  case class Cond(test: String, continuation: Option[String] = None) extends LazyRender:
    override lazy val render =
      continuation match
        case Some(c) => s"$c ${this.test}"
        case None    => this.test

  case class Limit(lmt: String)
  case class Start(strt: String)
  case class Duration(dur: String)
  case class OrderClause(
      fields: Seq[Field],
      isRand: Boolean = false,
      isCollate: Boolean = false,
      isNumeric: Boolean = false,
      isAscending: Boolean = true
  ) extends LazyRender:
    override lazy val render =
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
    lazy val render = Select.render(this)

  override def render(x: Select): String =
    import LazyRender.renderSeq
    x match
      case x: SELECT =>
        val statementOrderSeq = Seq[Option[String]](
          (if (x.isValueMode) Some("VALUE") else None),
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
        )
        "SELECT " ++ statementOrderSeq.flatten.mkString(" ") ++ ";"

end Select
