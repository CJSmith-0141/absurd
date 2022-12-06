package net.tazato.surreal.ql.statements

import cats.implicits._
import net.tazato.surreal.ql._
import net.tazato.surreal.ql.types._

enum Info(val info: String, val ident: Option[String]):
  case KV               extends Info("KV", None)
  case NS               extends Info("NS", None)
  case NAMESPACE        extends Info("NAMESPACE", None)
  case DB               extends Info("DB", None)
  case DATABASE         extends Info("DATABASE", None)
  case SCOPE(x: String) extends Info("SCOPE", Some(x))
  case TABLE(x: String) extends Info("TABLE", Some(x))

object Info extends Render[Info]:
  import Info._
  override def render(x: Info): String =
    x match
      case KV | NS | NAMESPACE | DB | DATABASE =>
        s"INFO FOR ${x.info};"
      case Info.SCOPE(_) | Info.TABLE(_) =>
        s"INFO FOR ${x.info} ${x.ident.get};"
end Info
