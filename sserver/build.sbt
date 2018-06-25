name := "ctest"
description := "A demo application to showcase sentiment analysis using Stanford CoreNLP and Scala"
version  := "1.0.0"

scalaVersion := "2.11.7"

libraryDependencies += "ch.qos.logback" % "logback-classic" % "1.2.3"
libraryDependencies += "edu.stanford.nlp" % "stanford-corenlp" % "3.5.2" artifacts (Artifact("stanford-corenlp", "models"), Artifact("stanford-corenlp"))
libraryDependencies += "org.scalatest" %% "scalatest" % "2.2.6" % "test"
libraryDependencies += "org.erlang.otp" % "jinterface" % "1.6.1"

assemblyOption in assembly := (assemblyOption in assembly).value.copy(cacheUnzip = false)

assemblyOption in assembly := (assemblyOption in assembly).value.copy(cacheOutput = false)

javaOptions in assembly += "-Xmx4g -Xss20M"

mainClass in assembly := Some("com.nodalweb.ctest.Ctest")

assemblyJarName in assembly := "sserver.jar"

//export SBT_OPTS="-Xmx4G -XX:+UseConcMarkSweepGC -XX:+CMSClassUnloadingEnabled -Xss20M"

