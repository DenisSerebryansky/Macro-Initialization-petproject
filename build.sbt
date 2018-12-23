name := "Initialization"

version := "0.1"

scalaVersion := "2.11.8"

dependsOn(reflection)

lazy val reflection = project.in(file("reflection"))
  .settings(
    scalaVersion        := "2.11.8",
    libraryDependencies += "org.scala-lang" % "scala-reflect" % scalaVersion.value
  )