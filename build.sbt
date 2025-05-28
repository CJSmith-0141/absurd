ThisBuild / tlBaseVersion    := "0.0"
ThisBuild / organization     := "net.tazato"
ThisBuild / organizationName := "Connor James Smith"
ThisBuild / startYear        := Some(2023)
ThisBuild / licenses         := Seq(License.Apache2)
ThisBuild / developers ++= List(
  tlGitHubDev("CJSmith-0141", "CJ Smith")
)

ThisBuild / scalaVersion    := "3.3.4"
ThisBuild / tlFatalWarnings := true
ThisBuild / tlJdkRelease    := Some(17)

val Http4sVersion     = "1.0.0-M42"
val catsEffectVersion = "3.5.5"
val log4CatsVersion   = "2.7.1"

val commonLibraryDependencies = Seq(
  "org.typelevel" %% "cats-core"           % "2.12.0",
  "org.typelevel" %% "cats-effect"         % catsEffectVersion,
  "org.typelevel" %% "cats-effect-kernel"  % catsEffectVersion,
  "org.typelevel" %% "cats-effect-std"     % catsEffectVersion,
  "org.typelevel" %% "log4cats-core"       % log4CatsVersion,
  "org.typelevel" %% "log4cats-slf4j"      % log4CatsVersion,
  "org.http4s"    %% "http4s-ember-server" % Http4sVersion,
  "org.http4s"    %% "http4s-ember-client" % Http4sVersion,
  "org.http4s"    %% "http4s-circe"        % Http4sVersion,
  "org.http4s"    %% "http4s-dsl"          % Http4sVersion,
  "ch.qos.logback" % "logback-classic"     % "1.5.12"
)

lazy val root = tlCrossRootProject.aggregate(core, tests)

lazy val core = crossProject(JVMPlatform)
  .crossType(CrossType.Pure)
  .in(file("core"))
  .settings(
    name := "absurd",
    libraryDependencies ++= commonLibraryDependencies,
    Compile / scalacOptions ++= Seq("-explain")
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
        "com.disneystreaming" %% "weaver-cats" % "0.8.4" % Test
      ),
    testFrameworks += new TestFramework("weaver.framework.CatsEffect")
  )
