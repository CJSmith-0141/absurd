package net.tazato

import cats.effect.*
import org.http4s.ember.client.*
import org.http4s.implicits.*
import org.http4s.*
import org.http4s.circe.*
import io.circe.Json
import net.tazato.surreal.ql.*
import net.tazato.surreal.ql.statements.*
import org.typelevel.log4cats.*
import org.typelevel.log4cats.slf4j.Slf4jFactory

object Main extends IOApp.Simple {
  implicit val logging: LoggerFactory[IO]   = Slf4jFactory.create[IO]
  val logger: SelfAwareStructuredLogger[IO] = LoggerFactory[IO].getLogger
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
          ).withEntity(
            Define.NAMESPACE("baby").render
              ++ Info.NAMESPACE.render
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
        _ <- logger.info("\n" ++ r.spaces2)
      yield ()
    }
}
