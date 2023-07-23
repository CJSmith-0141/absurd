package net.tazato.surreal.ql.statements

import cats.implicits.*
import net.tazato.surreal.ql.types.*

sealed trait Define

object Define extends Render[Define]:
  case class NAMESPACE(name: String) extends Define:
    lazy val render = Define.render(this)

  case class DATABASE(name: String) extends Define:
    lazy val render = Define.render(this)

  case class FUNCTION(name: String, args: Seq[(String, String)], block: String) extends Define:
    lazy val render = Define.render(this)

  case class LOGIN(
      name: String,
      base: Base,
      hash: String
  ) extends Define:
    lazy val render = Define.render(this)

  case class TOKEN(
      name: String,
      base: Base,
      kind: Algo,
      code: String
  ) extends Define:
    lazy val render = Define.render(this)

  case class SCOPE(
      name: String,
      session: Option[String] = None,
      signup: Option[String] = None,
      signin: Option[String] = None
  ) extends Define:
    lazy val render = Define.render(this)

  case class TABLE(
      name: String,
      drop: Boolean = false,
      full: Boolean = false,
      view: Option[String] = None,
      permissions: Option[String] = None // TODO permissions needs to be a sub-statement
  ) extends Define:
    lazy val render = Define.render(this)

  case class EVENT(
      name: String,
      what: String,
      when: String,
      `then`: String
  ) extends Define:
    lazy val render = Define.render(this)

  case class FIELD(
      name: String,
      what: String,
      kind: Option[String] = None,
      value: Option[String] = None,
      assert: Option[String] = None,
      permissions: Option[String] = None // TODO permissions needs to be a sub statement
  ) extends Define:
    lazy val render = Define.render(this)

  case class INDEX(
      name: String,
      what: String,
      cols: String,
      uniq: Boolean
  ) extends Define:
    lazy val render = Define.render(this)

  override def render(x: Define): String = {
    val last = x match {
      case NAMESPACE(name) => s"NAMESPACE $name"
      case DATABASE(name)  => s"DATABASE $name"
      case FUNCTION(name, args, block) => {
        val argsString = args
          .map { case (n, k) =>
            s"$n: $k"
          }
          .mkString(", ")
        s"FUNCTION fn::$name($argsString) $block"
      }
      case LOGIN(n, b, h)    => s"LOGIN $n ON ${b.value} PASSHASH $h"
      case TOKEN(n, b, k, c) => s"TOKEN $n ON ${b.value} TYPE ${k.value} VALUE $c"
      case SCOPE(n, session, signup, signin) =>
        List(
          s"SCOPE $n".some,
          session.map(y => s"SESSION $y"),
          signup.map(y => s"SIGNUP $y"),
          signin.map(y => s"SIGNIN $y")
        ).flatten.mkString(" ")
      case TABLE(n, drop, full, view, permissions) =>
        val d: Option[String] = if (drop) Some("DROP") else None
        val f: String         = if (full) "SCHEMAFULL" else "SCHEMALESS"
        val p: String         = permissions.getOrElse("PERMISSIONS FULL")
        List(
          s"TABLE $n".some,
          d,
          f.some,
          view,
          p.some
        ).flatten.mkString(" ")
      case EVENT(n, what, when, t) => s"EVENT $n ON $what WHEN $when THEN $t"
      case FIELD(name, what, kind, value, assert, permissions) =>
        val p: String = permissions.getOrElse("PERMISSIONS FULL")
        List[Option[String]](
          s"FIELD $name ON $what".some,
          kind.map(y => s"TYPE $y"),
          value.map(y => s"VALUE $y"),
          assert.map(y => s"ASSERT $y"),
          p.some
        ).flatten.mkString(" ")
      case INDEX(name, what, cols, uniq) =>
        val u: Option[String] = if (uniq) Some("UNIQUE") else None
        List[Option[String]](
          s"INDEX $name ON $what FIELDS $cols".some,
          u
        ).flatten.mkString(" ")
    }
    s"DEFINE $last;"
  }
