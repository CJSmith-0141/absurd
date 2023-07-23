package net.tazato.surreal.ql.statements

import net.tazato.surreal.ql.types.Render

sealed trait Select

object Select extends Render[Select]:
  case class Field(fs: String)
  case class Alias(als: String)
  case class Target(trg: String)
  case class Cond(cond: String)
  case class Limit(lmt: String)
  case class Start(strt: String)
  case class Duration(dur: String)
  case class OrderClause(
      fields: Seq[Field],
      isRand: Boolean = false,
      isCollate: Boolean = false,
      isNumeric: Boolean = false,
      isAscending: Boolean = true
  )

  case class SELECT(
      isValueMode: Boolean,
      selectFields: Seq[Field],
      alias: Option[Alias],
      targets: Seq[Target],
      whereClause: Option[Seq[Cond]],
      splitBy: Option[Field],
      groupBy: Option[Seq[Field]],
      orderClause: Option[OrderClause]
  )

  override def render(x: Select): String = ???

end Select
