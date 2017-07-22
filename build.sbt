name := "TwitterStream"

version := "1.0"

scalaVersion := "2.11.2"

libraryDependencies ++= Seq("org.apache.spark" %% "spark-core" % "2.10","org.apache.spark" %% "spark-mllib" % "2.10","org.scalatest" % "scalatest_2.11" % "3.0.0","org.apache.bahir" %% "spark-streaming-twitter" % "2.0.0")
resolvers += Resolver.url("bintray-sbt-plugins", url("http://dl.bintray.com/sbt/sbt-plugin-releases"))(Resolver.ivyStylePatterns)

addSbtPlugin("com.eed3si9n" % "sbt-assembly" % "0.14.0")