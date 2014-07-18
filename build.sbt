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



lazy val apiSlack = Project("yardio-api-slack", file("yardio-api-slack"))
  .settings(commonSettings: _*)
  .settings(
    name := "yardio-api-slack",
    libraryDependencies ++= Seq(json, ws)
  )



lazy val commonModels = Project("yardio-common-models", file("yardio-common-models"))
  .settings(commonSettings: _*)
  .settings(
    name := "yardio-common-models",
    libraryDependencies ++= Seq(playP)
  )

lazy val commonUtils = Project("yardio-common-utils", file("yardio-common-utils"))
  .settings(commonSettings: _*)
  .settings(
    name := "yardio-common-utils",
    libraryDependencies ++= Seq(playP)
  )



lazy val connectorApi = Project("yardio-connector-api", file("yardio-connector-api"))
  .settings(commonSettings: _*)
  .settings(
    name := "yardio-connector-api",
    libraryDependencies ++= Seq(playP)
  )
  .dependsOn(commonModels)

lazy val connectorConfig = Project("yardio-connector-config", file("yardio-connector-config"))
  .settings(commonSettings: _*)
  .settings(
    name := "yardio-connector-config",
    libraryDependencies ++= Seq(playP, wsP)
  )
  .dependsOn(connectorApi, moduleCore)



lazy val providerSlack = Project("yardio-provider-slack", file("yardio-provider-slack"))
  .settings(commonSettings: _*)
  .settings(
    name := "yardio-provider-slack",
    libraryDependencies ++= Seq(playP)
  )
  .dependsOn(apiSlack, commonModels, moduleCore)



lazy val moduleCore  = Project("yardio-module-core", file("yardio-module-core"))
  .settings(commonSettings: _*)
  .settings(
    name := "yardio-module-core",
    libraryDependencies ++= Seq(akkaP, playP, wsP, jodaP, scalaTestPlus)
  )
  .enablePlugins(SbtTwirl)
  .dependsOn(commonModels, commonUtils, connectorApi)

lazy val moduleBitbucket  = Project("yardio-module-bitbucket", file("yardio-module-bitbucket"))
  .settings(commonSettings: _*)
  .settings(
    name := "yardio-module-bitbucket",
    libraryDependencies ++= Seq(playP, wsP)
  )
  .dependsOn(moduleCore)

lazy val moduleJira  = Project("yardio-module-jira", file("yardio-module-jira"))
  .settings(commonSettings: _*)
  .settings(
    name := "yardio-module-jira",
    libraryDependencies ++= Seq(playP, wsP)
  )
  .dependsOn(moduleCore)

lazy val moduleZik  = Project("yardio-module-zik", file("yardio-module-zik"))
  .settings(commonSettings: _*)
  .settings(
    name := "yardio-module-zik",
    libraryDependencies ++= Seq(playP, jodaP)
  )
  .dependsOn(moduleCore)



lazy val serverPlay  = Project("yardio-server-play", file("yardio-server-play"))
  .settings(commonSettings: _*)
  .settings(
    name := "yardio-server-play",
    libraryDependencies ++= Seq(joda, json, play, ws, joda)
  )
  .enablePlugins(PlayScala)
  .enablePlugins(SbtTwirl)
  .dependsOn(connectorConfig, providerSlack, moduleJira)

lazy val yardio = project.in(file("."))
  .settings(commonSettings: _*)
  .settings(
    name := "yardio"
  )
  .aggregate(apiSlack, commonModels, commonUtils, connectorApi, connectorConfig, providerSlack, moduleCore, moduleJira, serverPlay)
