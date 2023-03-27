
val commonSettings = Seq(
  version            := "0.6",
  scalaVersion       := "3.2.0",
  crossScalaVersions := Seq("3.2.0"),
  organization       := "ch.epfl.lara",
)

lazy val silex = RootProject(uri("https://github.com/epfl-lara/silex.git"))

lazy val scallion = project
  .in(file("."))
  .settings(
    commonSettings,
    name := "scallion",

    scalacOptions ++= Seq(
      "-deprecation",
      "-feature",
      "-unchecked"
    ),

    Compile / doc / scalacOptions ++= Seq(
      "-groups",
      "-sourcepath", baseDirectory.value.getAbsolutePath,
      "-doc-source-url", "https://raw.githubusercontent.com/epfl-lara/scallion/master€{FILE_PATH}.scala",
      "-doc-root-content", baseDirectory.value + "/project/root-doc.txt"
    ),

    Compile / doc / target := baseDirectory.value / "docs",

    libraryDependencies ++= Seq(
      "org.scalatest" %% "scalatest" % "3.2.9" % "test",
    ),

    bintrayOrganization := Some("epfl-lara"),
    licenses += ("Apache-2.0", url("https://opensource.org/licenses/Apache-2.0")),
    bintrayPackageLabels := Seq(
      "scala", "parser", "parsing",
      "ll1", "ll1-parsing", "ll1-grammar",
      "parser-combinators", "parsing-combinators"
    ),
  ).dependsOn(silex)

lazy val example = project
  .in(file("example"))
  .settings(
    commonSettings,
    name := "scallion-examples",
    Compile / scalaSource := baseDirectory.value,
  )
  .dependsOn(scallion)

lazy val benchmark = project
  .in(file("benchmark"))
  .settings(
    commonSettings,
    scalaVersion           := "3.2.0",
    crossScalaVersions ++= Seq("2.13.10", "3.2.0"),
    name                   := "scallion-benchmarks",
    run / fork             := true,
    run / baseDirectory    := file("."),
    run / javaOptions      += "-Xss1024K",
    Compile / scalaSource  := baseDirectory.value / "src",
    Test / scalaSource     := baseDirectory.value / "src",
    libraryDependencies += ("com.storm-enroute" %% "scalameter" % "0.21").cross(CrossVersion.for3Use2_13),
    libraryDependencies += ("org.scala-lang.modules" %% "scala-parser-combinators" % "1.1.2").cross(CrossVersion.for3Use2_13),
    libraryDependencies += ("io.carpe" %% "parseback" % "0.5.1").cross(CrossVersion.for3Use2_13),
    testFrameworks += new TestFramework("org.scalameter.ScalaMeterFramework"),
    Test / parallelExecution := false,
  )
  .dependsOn(scallion)


