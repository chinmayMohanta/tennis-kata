name := "tennis"

version := "1.0"

scalaVersion := "2.12.6"

resolvers ++= Seq(
  "Typesafe Repository" at "http://repo.typesafe.com/typesafe/releases/",
  "Confluent" at "http://packages.confluent.io/maven/",
  "pub" at "https://repo1.maven.org/maven2/"
)

libraryDependencies += "org.scalatest" %% "scalatest" % "3.0.5" % "test"




assemblyMergeStrategy in assembly := {
  case m if m.toLowerCase.endsWith("manifest.mf") => MergeStrategy.discard
  case m if m.toLowerCase.matches("meta-inf.*\\.sf$") => MergeStrategy.discard
  case "log4j.properties" => MergeStrategy.discard
  case m if m.toLowerCase.startsWith("meta-inf/services/") =>
    MergeStrategy.filterDistinctLines
  case "reference.conf" => MergeStrategy.concat
  case _ => MergeStrategy.first
}
