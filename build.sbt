ThisBuild / tlBaseVersion    := "0.0"
ThisBuild / organization     := "net.tazato"
ThisBuild / organizationName := "Tazato.net"
ThisBuild / startYear        := Some(2023)
ThisBuild / licenses         := Seq(License.Apache2)
ThisBuild / developers ++= List(
  tlGitHubDev("CJSmith-0141", "CJ Smith")
)

val Scala3 = "3.3.0"
ThisBuild / crossScalaVersions := Seq("2.13.11", Scala3)
ThisBuild / scalaVersion       := Scala3

val Http4sVersion = "1.0.0-M40"

lazy val root = project
  .in(file("."))
  .settings(
    name := "surreal",
    libraryDependencies ++= Seq(
      // "core" module - IO, IOApp, schedulers
      // This pulls in the kernel and std modules automatically.
      "org.typelevel" %% "cats-effect" % "3.3.12",
      // concurrency abstractions and primitives (Concurrent, Sync, Async etc.)
      "org.typelevel" %% "cats-effect-kernel" % "3.3.12",
      // standard "effect" library (Queues, Console, Random etc.)
      "org.typelevel"       %% "cats-effect-std"     % "3.3.12",
      "org.typelevel"       %% "log4cats-core"       % "2.5.0",
      "org.typelevel"       %% "log4cats-slf4j"      % "2.5.0",
      "org.http4s"          %% "http4s-ember-server" % Http4sVersion,
      "org.http4s"          %% "http4s-ember-client" % Http4sVersion,
      "org.http4s"          %% "http4s-circe"        % Http4sVersion,
      "org.http4s"          %% "http4s-dsl"          % Http4sVersion,
      "com.disneystreaming" %% "weaver-cats"         % "0.7.6" % Test,
      "ch.qos.logback"       % "logback-classic"     % "1.3.6"
    ),
    testFrameworks += new TestFramework("weaver.framework.CatsEffect")
  )
