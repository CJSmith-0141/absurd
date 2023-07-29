ThisBuild / tlBaseVersion    := "0.0"
ThisBuild / organization     := "net.tazato"
ThisBuild / organizationName := "Connor James Smith"
ThisBuild / startYear        := Some(2023)
ThisBuild / licenses         := Seq(License.Apache2)
ThisBuild / developers ++= List(
  tlGitHubDev("CJSmith-0141", "CJ Smith")
)

ThisBuild / scalaVersion    := "3.3.0"
ThisBuild / tlFatalWarnings := true
ThisBuild / tlJdkRelease    := Some(17)

val Http4sVersion     = "1.0.0-M40"
val catsEffectVersion = "3.5.1"
val log4CatsVersion   = "2.6.0"

val commonLibraryDependencies = Seq(
  // "core" module - IO, IOApp, schedulers
  // This pulls in the kernel and std modules automatically.
  "org.typelevel" %% "cats-core"   % "2.9.0",
  "org.typelevel" %% "cats-effect" % catsEffectVersion,
  // concurrency abstractions and primitives (Concurrent, Sync, Async etc.)
  "org.typelevel" %% "cats-effect-kernel" % catsEffectVersion,
  // standard "effect" library (Queues, Console, Random etc.)
  "org.typelevel" %% "cats-effect-std"     % catsEffectVersion,
  "org.typelevel" %% "log4cats-core"       % log4CatsVersion,
  "org.typelevel" %% "log4cats-slf4j"      % log4CatsVersion,
  "org.http4s"    %% "http4s-ember-server" % Http4sVersion,
  "org.http4s"    %% "http4s-ember-client" % Http4sVersion,
  "org.http4s"    %% "http4s-circe"        % Http4sVersion,
  "org.http4s"    %% "http4s-dsl"          % Http4sVersion,
  "ch.qos.logback" % "logback-classic"     % "1.4.8"
)

lazy val root = tlCrossRootProject.aggregate(core, tests)

lazy val core = crossProject(JVMPlatform)
  .crossType(CrossType.Pure)
  .in(file("core"))
  .settings(
    name := "absurd",
    libraryDependencies ++= commonLibraryDependencies,
    Compile / doc / scalacOptions ++= Seq("-groups")
  )

lazy val tests = crossProject(JVMPlatform)
  .crossType(CrossType.Pure)
  .in(file("tests"))
  .enablePlugins(NoPublishPlugin)
  .dependsOn(core)
  .settings(
    name := "absurd-tests",
    libraryDependencies ++=
      commonLibraryDependencies ++ Seq(
        "com.disneystreaming" %% "weaver-cats" % "0.8.3" % Test
      ),
    testFrameworks += new TestFramework("weaver.framework.CatsEffect")
  )
