name := "robot"

version := "1.0"

scalaVersion := "2.11.8"

resolvers += Resolver.bintrayRepo("tabdulradi", "maven")

libraryDependencies += "net.liftweb" % "lift-json_2.11" % "2.6.2"

libraryDependencies += "com.typesafe.akka" % "akka-http_2.11" % "10.0.4"

libraryDependencies += "joda-time" % "joda-time" % "2.9.7"

libraryDependencies += "org.scalaj" % "scalaj-http_2.11" % "2.3.0"

libraryDependencies += "org.jsoup" % "jsoup" % "1.10.2"


enablePlugins(JavaAppPackaging)

fork := true

assemblyMergeStrategy in assembly := {
    case PathList(ps @ _*) if ps.last endsWith ".class"=> MergeStrategy.first
    case PathList(ps @ _*) if ps.last endsWith ".properties"=> MergeStrategy.first
    case PathList(ps @ _*) if ps.last endsWith ".xml"=> MergeStrategy.first
    case PathList(ps @ _*) if ps.last endsWith ".thrift"=> MergeStrategy.first
    case PathList(ps @ _*) if ps.last endsWith ".html"=> MergeStrategy.first
    case PathList(ps @ _*) if ps.last endsWith ".js"=> MergeStrategy.first
    case PathList(ps @ _*) if ps.last endsWith ".css"=> MergeStrategy.first
    case PathList(ps @ _*) if ps.last endsWith ".xsd"=> MergeStrategy.first
    case PathList(ps @ _*) if ps.last endsWith ".dtd"=> MergeStrategy.first
    case PathList(ps @ _*) if ps.last endsWith ".java"=> MergeStrategy.first
    case PathList(ps @ _*) if ps.last endsWith ".so"=> MergeStrategy.first
    case PathList(ps @ _*) if ps.last endsWith ".png"=> MergeStrategy.first
    case x =>
       val oldStrategy = (assemblyMergeStrategy in assembly).value
       oldStrategy(x)
}

