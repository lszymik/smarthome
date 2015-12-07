import sbt.Keys._
import sbt._
import spray.revolver.RevolverPlugin.Revolver

organization := "com.szymik"

name := "smarthome"

scalaVersion := "2.11.7"

resolvers += "spray repo" at "http://repo.spray.io"


libraryDependencies ++= {
  val akkaVersion = "2.4.0"
  val macwireVersion = "2.1.0"
  val hystrixVersion = "1.4.21"

  Seq(
    // akka
    "com.typesafe.akka" %% "akka-actor" % akkaVersion
      exclude("org.scala-lang", "scala-library"),
    "com.typesafe.akka" %% "akka-slf4j" % akkaVersion
      exclude("org.slf4j", "slf4j-api")
      exclude("org.scala-lang", "scala-library"),

    "com.typesafe.akka" %% "akka-http-experimental" % "2.0-M1",
    "com.typesafe.akka" %% "akka-http-spray-json-experimental" % "2.0-M1",

    // mongo
    "org.mongodb" % "mongodb-driver-reactivestreams" % "1.1.0",

    // macwire
    "com.softwaremill.macwire" %% "macros" % macwireVersion,
    "com.softwaremill.macwire" %% "util" % macwireVersion,

    // Hystrix
    "io.reactivex" % "rxjava" % "1.0.16",
    "io.reactivex" %% "rxscala" % "0.25.0",
    "com.netflix.hystrix" % "hystrix-core" % hystrixVersion,
    "com.netflix.hystrix" % "hystrix-metrics-event-stream" % hystrixVersion,

    // config
    "com.typesafe" % "config" % "1.3.0",

    // logging
    "com.typesafe.scala-logging" %% "scala-logging" % "3.1.0",
    "org.slf4j" % "slf4j-api" % "1.7.13",
    "org.slf4j" % "jcl-over-slf4j" % "1.7.13",
    "ch.qos.logback" % "logback-classic" % "1.1.3",

    // tests
    "com.typesafe.akka" %% "akka-testkit" % akkaVersion % "test",
    "com.typesafe.akka" %% "akka-http-testkit-experimental" % "2.0-M1" % "test",
    "org.scalatest" %% "scalatest" % "2.2.5" % "test"
      exclude("org.scala-lang", "scala-reflect"),
    "org.scalamock" %% "scalamock-scalatest-support" % "3.2.2" % "test"
      exclude("org.scala-lang", "scala-reflect"),
    "org.pegdown" % "pegdown" % "1.6.0" % "test"
  )
}

scalacOptions ++= Seq(
  "-unchecked",
  "-deprecation",
  "-Xlint",
  "-language:_",
  "-target:jvm-1.7",
  "-encoding", "UTF-8",
  "-language:postfixOps",
  "-Ywarn-adapted-args",
  "-Ywarn-dead-code",
  "-Ywarn-inaccessible",
  "-Ywarn-infer-any",
  "-Ywarn-nullary-override",
  "-Ywarn-nullary-unit",
  "-Ywarn-numeric-widen",
  "-Ywarn-unused",
  "-Ywarn-unused-import"
  // "-Ywarn-value-discard"
  //  "-Y"
)

Revolver.settings: Seq[sbt.Def.Setting[_]]

crossPaths := false

//conflictManager := ConflictManager.loose

enablePlugins(JavaAppPackaging)

net.virtualvoid.sbt.graph.Plugin.graphSettings

testOptions in Test += Tests.Argument(TestFrameworks.ScalaTest, "-h", "target/test-reports")
