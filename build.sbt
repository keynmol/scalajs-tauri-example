val Versions = new {
  val tauri = "1.0.0"
}

lazy val tauri = project
  .in(file("."))
  .enablePlugins(ScalablyTypedConverterExternalNpmPlugin, ScalaJSPlugin)
  .settings(
    organization := "com.indoorvivants.tauri",
    moduleName := "api",
    scalaVersion := "3.1.2",
    libraryDependencies += "com.raquo" %%% "laminar" % "0.14.2",
    externalNpm := {
      sys.process.Process("yarn", baseDirectory.value).!
      baseDirectory.value
    },
    stOutputPackage := "com.indoorvivants.tauri",
    stMinimize := Selection.AllExcept("@tauri-apps/api"),
    stIgnore += "type-fest",
    scalaJSLinkerConfig ~= { config =>
      config
        .withModuleKind(ModuleKind.ESModule)
    },
    scalaJSUseMainModuleInitializer := true,
    Compile / packageSrc / mappings ++= {
      val base = (Compile / sourceManaged).value
      val files = (Compile / managedSources).value
      files.map { f => (f, f.relativeTo(base).get.getPath) }
    }
  )

lazy val isRelease = sys.env.get("RELEASE").contains("yesh")

lazy val buildFrontend = taskKey[Map[String, File]]("")

import org.scalajs.linker.interface.Report
lazy val frontendReport = taskKey[(Report, File)]("")

ThisBuild / frontendReport := {
  if (isRelease)
    (tauri / Compile / fullLinkJS).value.data ->
      (tauri / Compile / fullLinkJS / scalaJSLinkerOutputDirectory).value
  else
    (tauri / Compile / fastLinkJS).value.data ->
      (tauri / Compile / fastLinkJS / scalaJSLinkerOutputDirectory).value
}

buildFrontend := {
  val (report, fm) = frontendReport.value
  val outDir = (ThisBuild / baseDirectory).value
  IO.listFiles(fm)
    .toList
    .map { file =>
      val (name, ext) = file.baseAndExt
      val out = outDir / (name + "." + ext)

      IO.copyFile(file, out)

      file.name -> out
    }
    .toMap
}

Global / onChangedBuildSource := ReloadOnSourceChanges
