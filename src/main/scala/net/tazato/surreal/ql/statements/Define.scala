package net.tazato.surreal.ql.statements

import cats.implicits._
import net.tazato.surreal.ql.types._

sealed trait Define

object Define extends Render[Define]:

  case class NAMESPACE(name: String) extends Define with Render[NAMESPACE]:
    override def render(x: NAMESPACE): String = s"NAMESPACE ${x.name}"
  end NAMESPACE

  case class DATABASE(name: String) extends Define with Render[DATABASE]:
    override def render(x: DATABASE): String = s"DATABASE ${x.name}"
  end DATABASE

  case class LOGIN(
      name: String,
      base: Base,
      hash: String
  ) extends Define
      with Render[LOGIN]:
    override def render(x: LOGIN): String = s"LOGIN ${x.name} ON ${x.base.value} PASSHASH ${x.hash}"
  end LOGIN

  case class TOKEN(
      name: String,
      base: Base,
      kind: Algo,
      code: String
  ) extends Define
      with Render[TOKEN]:
    override def render(x: TOKEN): String =
      s"TOKEN ${x.name} ON ${x.base.value} TYPE ${x.kind.value} VALUE ${x.code}"
  end TOKEN

  case class SCOPE(
      name: String,
      session: Option[String] = None,
      signup: Option[String] = None,
      signin: Option[String] = None
  ) extends Define
      with Render[SCOPE]:
    override def render(x: SCOPE): String =
      List(
        s"SCOPE ${x.name}".some,
        x.session.map(y => s"SESSION $y"),
        x.signup.map(y => s"SIGNUP $y"),
        x.signin.map(y => s"SIGNIN $y")
      ).flatten.mkString(" ")
  end SCOPE

  case class TABLE(
      name: String,
      drop: Boolean = false,
      full: Boolean = false,
      view: Option[String] = None,
      permissions: Option[String] = None // TODO permissions needs to be a sub-statement
  ) extends Define
      with Render[TABLE]:
    override def render(x: TABLE): String =
      val d: Option[String] = if (x.drop) Some("DROP") else None
      val f: String         = if (x.full) "SCHEMAFULL" else "SCHEMALESS"
      val p: String = x.permissions match
        case Some(y) => y
        case None    => "PERMISSIONS FULL" // default to full access... maybe bad?

      List(
        s"TABLE ${x.name}".some,
        d,
        f.some,
        x.view,
        p.some
      ).flatten.mkString(" ")
  end TABLE

  case class EVENT(
      name: String,
      what: String,
      when: String,
      `then`: String
  ) extends Define
      with Render[EVENT]:
    override def render(x: EVENT): String =
      s"EVENT ${x.name} ON ${x.what} WHEN ${x.when} THEN ${x.`then`}"
  end EVENT

  case class FIELD(
      name: String,
      what: String,
      kind: Option[String] = None,
      value: Option[String] = None,
      assert: Option[String] = None,
      permissions: Option[String] = None // TODO permissions needs to be a sub statement
  ) extends Define
      with Render[FIELD]:
    override def render(x: FIELD): String =
      val p: String = x.permissions match
        case Some(y) => y
        case None    => "PERMISSIONS FULL"

      List[Option[String]](
        s"FIELD $name ON $what".some,
        x.kind.map(y => s"TYPE $y"),
        x.value.map(y => s"VALUE $y"),
        x.assert.map(y => s"ASSERT $y"),
        p.some
      ).flatten.mkString(" ")
  end FIELD

  case class INDEX(
      name: String,
      what: String,
      cols: String,
      uniq: Boolean
  ) extends Define
      with Render[INDEX]:
    override def render(x: INDEX): String =
      val u: Option[String] = if (x.uniq) Some("UNIQUE") else None
      List[Option[String]](
        s"INDEX ${x.name} ON ${x.what} FIELDS ${x.cols}".some,
        u
      ).flatten.mkString(" ")
  end INDEX

  override def render(x: Define): String =
    def define(s: String): String = s"DEFINE $s;"
    x match
      case y: NAMESPACE => define(y.render(y))
      case y: DATABASE  => define(y.render(y))
      case y: LOGIN     => define(y.render(y))
      case y: TOKEN     => define(y.render(y))
      case y: SCOPE     => define(y.render(y))
      case y: TABLE     => define(y.render(y))
      case y: EVENT     => define(y.render(y))
      case y: FIELD     => define(y.render(y))
      case y: INDEX     => define(y.render(y))
