
val commonSettings = Seq(
  version            := "0.6",
  scalaVersion       := "3.2.0",
  crossScalaVersions := Seq("3.2.0"),
  organization       := "ch.epfl.lara",
  resolvers          += "bintray-epfl-lara" at "https://dl.bintray.com/epfl-lara/maven",
)
lazy val silex = RootProject(uri("git://github.com/epfl-lara/silex.git"))

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
    name                   := "scallion-benchmarks",
    run / fork             := true,
    run / baseDirectory    := file("."),
    run / javaOptions      += "-Xss1024K",
    Compile / scalaSource  := baseDirectory.value / "src",
    Test / scalaSource     := baseDirectory.value / "src",
    resolvers += "Sonatyperun OSS Snapshots" at "https://oss.sonatype.org/content/repositories/releases",
    resolvers += Resolver.bintrayRepo("djspiewak", "maven"),
    libraryDependencies += "com.hughsimpson" %% "scalameter" % "0.22.1",
    libraryDependencies += "org.scala" %% "scala-parser-combinators" % "2.2.0",
    libraryDependencies += "com.codecommit" %% "parseback-core" % "0.3",
    libraryDependencies += "com.codecommit" %% "parseback-cats" % "0.3",
    testFrameworks += new TestFramework("org.scalameter.ScalaMeterFramework"),
    Test / parallelExecution := false,
  )
  .dependsOn(scallion)


