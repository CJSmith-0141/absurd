package net.tazato

import cats.implicits.*
import cats.effect.*
import cats.syntax.all.*
import org.http4s.ember.client.*
import org.http4s.client.*
import org.http4s.implicits.*
import org.http4s.*
import org.http4s.circe.*
import io.circe.Json
import org.typelevel.ci.*
import net.tazato.surreal.ql.*
import net.tazato.surreal.ql.statements.*

object Main extends IOApp.Simple {
  def run: IO[Unit] =
    EmberClientBuilder.default[IO].build.use { client =>
      val runner = client
        .run(
          Request[IO](
            Method.POST,
            uri = uri"http://localhost:8000/sql",
            headers = Headers(
              "NS"            -> "baby",
              "DB"            -> "tazato",
              "Accept"        -> "application/json",
              "Authorization" -> ("Basic " ++ BasicCredentials("root", "root").token)
            )
          ).withEntity[String](
            Define.render(Define.NAMESPACE("baby"))
              ++ Info.render(Info.NAMESPACE)
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
