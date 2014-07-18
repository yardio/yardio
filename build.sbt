import Dependencies._
import com.typesafe.sbt.SbtNativePackager._
import NativePackagerKeys._

val wsP = ws % "provided"

val commonSettings = Seq(
  organization := "io.yard",
  version := "0.1.0-SNAPSHOT",
  scalaVersion := "2.11.1",
  resolvers += "Typesafe repository" at "http://repo.typesafe.com/typesafe/releases/",
  resolvers += "Typesafe repository mwn" at "http://repo.typesafe.com/typesafe/maven-releases/"
)

lazy val core = Project("yardio-module-core", file("yardio-module-core"))
  .settings(commonSettings: _*)
  .settings(
    name := "yardio-module-core",
    libraryDependencies ++= Seq(akkaP, jsonP, playP, wsP, jodaP, scalaTestPlus)
  )
  .enablePlugins(SbtTwirl)

lazy val bitbucket = Project("yardio-module-bitbucket", file("yardio-module-bitbucket"))
  .settings(commonSettings: _*)
  .settings(
    name := "yardio-module-bitbucket",
    libraryDependencies ++= Seq(jsonP, playP, wsP)
  )
  .dependsOn(core)

lazy val jira = Project("yardio-module-jira", file("yardio-module-jira"))
  .settings(commonSettings: _*)
  .settings(
    name := "yardio-module-jira",
    libraryDependencies ++= Seq(jsonP, playP, wsP)
  )
  .dependsOn(core)

lazy val zik = Project("yardio-module-zik", file("yardio-module-zik"))
  .settings(commonSettings: _*)
  .settings(
    name := "yardio-module-zik",
    libraryDependencies ++= Seq(jsonP, playP, wsP, jodaP)
  )
  .dependsOn(core)

lazy val server = Project("yardio-server-play", file("yardio-server-play"))
  .settings(commonSettings: _*)
  .settings(
    name := "yardio-server-play",
    libraryDependencies ++= Seq(joda, json, play, ws, joda)
  )
  .enablePlugins(PlayScala)
  .enablePlugins(SbtTwirl)
  .dependsOn(bitbucket, jira, zik)

lazy val yardio = project.in(file("."))
  .settings(commonSettings: _*)
  .settings(
    name := "yardio"
  )
  .aggregate(core, bitbucket, jira, zik, server)
