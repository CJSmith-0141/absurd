package net.tazato

import cats.implicits._
import cats.effect._
import org.http4s.ember.client._
import org.http4s.client._
import org.http4s.implicits._
import org.http4s._
import org.http4s.circe._
import io.circe.Json
import org.typelevel.ci._
import net.tazato.surreal.ql._
import net.tazato.surreal.ql.statements._

object Main extends IOApp.Simple {
  def run: IO[Unit] =
    EmberClientBuilder.default[IO].build.use { client =>
      val runner = client
        .run(
          Request[IO](
            Method.POST,
            uri = uri"http://localhost:8000/sql",
            headers = Headers(
              "NS"            -> "tazato",
              "DB"            -> "tazato",
              "Accept"        -> "application/json",
              "Authorization" -> ("Basic " ++ BasicCredentials("root", "root").token)
            )
          ).withEntity[String](
            Info.infoStatement(Keywords.TABLE, Some("person")).getOrElse("")
          )
        )
        .use {
          case Status.Successful(r) =>
            r.as[Json]
          case Status.BadRequest(r) =>
            r.as[Json]
        }
      for
        r <- runner
        _ = println(r.spaces2)
      yield ()
    }
}
