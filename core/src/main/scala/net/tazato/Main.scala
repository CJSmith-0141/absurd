/*
 * Copyright 2023 Connor James Smith
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package net.tazato

import cats.effect.*
import org.http4s.ember.client.*
import org.http4s.implicits.*
import org.http4s.*
import org.http4s.circe.*
import io.circe.Json
import net.tazato.absurd.ql.statements.{Define, Info}
import net.tazato.absurd.ql.*
import org.typelevel.log4cats.*
import org.typelevel.log4cats.slf4j.Slf4jFactory

/** Testbed for interacting with surrealdb
  */
object Main extends IOApp.Simple {
  implicit val logging: LoggerFactory[IO]   = Slf4jFactory.create[IO]
  val logger: SelfAwareStructuredLogger[IO] = LoggerFactory[IO].getLogger
  def run: IO[Unit]                         =
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
              "Authorization" -> ("Basic " ++ BasicCredentials(
                "root",
                "root"
              ).token)
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
