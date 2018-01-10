lazy val library = new {

  object Version {
    val akka = "2.5.8"
    val akkaHttp = "10.0.11"
    val slick = "3.2.1"
    val flyway = "3.2.1"
    val logback = "1.2.3"
    val scalaTest = "3.0.4"
  }

  val logBack = "ch.qos.logback" % "logback-classic" % Version.logback
  val flyway = "org.flywaydb" % "flyway-core" % Version.flyway
  val akka = "com.typesafe.akka" %% "akka-actor" % Version.akka
  val akkaSl4j = "com.typesafe.akka" %% "akka-slf4j" % Version.akka
  val akkaStreams = "com.typesafe.akka" %% "akka-stream" % Version.akka
  val akkaHttp = "com.typesafe.akka" %% "akka-http" % Version.akkaHttp
  val akkaHttpCore = "com.typesafe.akka" %% "akka-http-core" % Version.akkaHttp
  val sprayJson = "com.typesafe.akka" %% "akka-http-spray-json" % Version.akkaHttp
  val slick = "com.typesafe.slick" %% "slick" % Version.slick
  val slickHikari = "com.typesafe.slick" %% "slick-hikaricp" % Version.slick
  val pgSql = "org.postgresql" % "postgresql" % "9.4.1211"
  val h2 = "com.h2database" % "h2" % "1.4.192"
  val akkaTest = "com.typesafe.akka" %% "akka-testkit" % Version.akka
  val akkaHttpTest = "com.typesafe.akka" %% "akka-http-testkit" % Version.akkaHttp
  val scalaTest = "org.scalatest" %% "scalatest" % Version.scalaTest
}

lazy val projectSettings =
  Seq(
    scalaVersion := "2.12.4",
    organization := "net.softler",
    version := "0.1.0",
    organizationName := "Tobias Frischholz",
    startYear := Some(2017),
    licenses += ("MIT", url("http://opensource.org/licenses/MIT")),
    scalacOptions ++= Seq(
      "-deprecation",
      "-encoding",
      "UTF-8",
      "-feature",
      "-unchecked",
      "-Ywarn-numeric-widen",
      "-Ywarn-value-discard",
      "-Xfatal-warnings",
      "-Yno-adapted-args",
      "-Xfuture"
    ),
    sources in(Compile, doc) := Seq.empty
  )

lazy val settings = projectSettings

lazy val `akka-full-stack` = (project in file(".")).settings(settings).settings(libraryDependencies ++=
  Seq(
    library.logBack,
    library.akka,
    library.akkaSl4j,
    library.akkaStreams,
    library.sprayJson,
    library.akkaHttpCore,
    library.akkaHttp,
    library.pgSql,
    library.slick,
    library.slickHikari,
    library.pgSql,
    library.flyway,
    library.h2 % Test,
    library.scalaTest % Test,
    library.akkaTest % Test,
    library.akkaHttpTest % Test
  )
)

