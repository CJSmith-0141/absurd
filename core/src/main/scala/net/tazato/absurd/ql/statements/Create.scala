package net.tazato.absurd.ql.statements

import net.tazato.absurd.ql.traits.*
import net.tazato.absurd.ql.types.*

sealed trait Create

object Create extends Render[Create]:
  enum ReturnMode(val value: String, val projections: Option[Seq[String]]) extends LazyRender:
    case NONE                        extends ReturnMode("NONE", None)
    case BEFORE                      extends ReturnMode("BEFORE", None)
    case AFTER                       extends ReturnMode("AFTER", None)
    case DIFF                        extends ReturnMode("DIFF", None)
    case PROJECTIONS(x: Seq[String]) extends ReturnMode("", Some(x))

    override lazy val render: String =
      this match
        case NONE | BEFORE | AFTER | DIFF => s"RETURN ${this.value}"
        case PROJECTIONS(x) => s"RETURN ${x.mkString(", ")}"

  end ReturnMode


  case class CREATE(
      createTargets: Seq[Target],
      content: Option[String] = None,
      fields: Seq[(Field, String)],
      returnMode: Option[ReturnMode] = None,
      timeout: Option[Duration] = None,
      isParallel: Boolean = false
  ) extends Create

  override def render(x: Create): String = ???
