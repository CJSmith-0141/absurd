ThisBuild / organization := "com.example"
ThisBuild / scalaVersion := "3.2.1"

val Http4sVersion = "0.23.16"

lazy val root = (project in file(".")).settings(
  name := "surreal",
  libraryDependencies ++= Seq(
    // "core" module - IO, IOApp, schedulers
    // This pulls in the kernel and std modules automatically.
    "org.typelevel" %% "cats-effect" % "3.3.12",
    // concurrency abstractions and primitives (Concurrent, Sync, Async etc.)
    "org.typelevel" %% "cats-effect-kernel" % "3.3.12",
    // standard "effect" library (Queues, Console, Random etc.)
    "org.typelevel" %% "cats-effect-std"     % "3.3.12",
    "org.typelevel" %% "munit-cats-effect-3" % "1.0.7"  % Test,
    "org.typelevel" %% "log4cats-core"       % "2.5.0",
    "org.typelevel" %% "log4cats-slf4j"      % "2.5.0",
    "org.http4s"    %% "http4s-ember-server" % Http4sVersion,
    "org.http4s"    %% "http4s-ember-client" % Http4sVersion,
    "org.http4s"    %% "http4s-circe"        % Http4sVersion,
    "org.http4s"    %% "http4s-dsl"          % Http4sVersion,
    "org.scalameta" %% "munit"               % "0.7.29" % Test,
    "ch.qos.logback" % "logback-classic"     % "1.3.6"
  )
)
