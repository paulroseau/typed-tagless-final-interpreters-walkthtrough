name := "typed-tagless-final-interpreters"

scalaVersion := "2.12.1"

scalacOptions ++= Seq(
  "-deprecation",
  "-encoding", "UTF-8",
  "-feature",
  "-language:existentials",
  "-language:experimental.macros",
  "-language:higherKinds",
  "-language:implicitConversions",
  "-unchecked",
  "-Xfatal-warnings",
  "-Xfuture",
  "-Xlint",
  "-Xlog-reflective-calls",
  "-Xmax-classfile-name","78",
  "-Ydelambdafy:method",
  "-Yno-adapted-args",
  "-Ypartial-unification",
  "-Ypatmat-exhaust-depth", "20",
  "-Ywarn-dead-code",
  "-Ywarn-inaccessible",
  "-Ywarn-unused-import",
  "-Ywarn-value-discard"
)
