package net.tazato.surreal.ql.statements

import cats.implicits._
import net.tazato.surreal.ql._

object Info:
  def infoStatement(q: Keywords, a: Option[String] = None): Either[Throwable, String] = {
    import Keywords._
    val infoStart: String = s"${Keywords.INFO.value} ${Keywords.FOR.value}"
    (q, a) match {
      case (KV | NS | NAMESPACE | DB | DATABASE, None) =>
        Right(s"$infoStart ${q.value};")
      case (SCOPE | TABLE, Some(a)) =>
        Right(s"$infoStart ${q.value} ${a};")
      case (_, _) =>
        Left(
          new Throwable(
            "Something is wrong with your INFO statement. See https://surrealdb.com/docs/surrealql/statements/info"
          )
        )
    }
  }
