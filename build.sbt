organization := "com.example"

name := "scalarest"

version := "0.1.0-SNAPSHOT"

resolvers += "releases"  at "http://oss.sonatype.org/content/repositories/releases"

libraryDependencies ++= Seq(
   "net.databinder"  %% "unfiltered-netty-server" % "0.7.1",
   "net.databinder" %% "unfiltered-filter" % "0.7.1",
   "javax.servlet" % "servlet-api" % "2.5" % "provided",
   "org.eclipse.jetty" % "jetty-plus" % "9.1.2.v20140210" % "container",
   "org.eclipse.jetty" % "jetty-webapp" % "9.1.2.v20140210" % "container",
   "org.clapper" %% "avsl" % "1.0.1",
   "net.liftweb" %% "lift-json" % "2.6-M2",
   "org.squeryl" %% "squeryl" % "0.9.6-RC2",
   "mysql" % "mysql-connector-java" % "5.1.29",
   "net.databinder" %% "unfiltered-spec" % "0.6.4" % "test",
   "junit" % "junit" % "4.11" % "test",
   // so that sbt will find plain old junit scala test classes
   "com.novocode" % "junit-interface" % "0.10-M2" % "test",
   "org.easymock" % "easymock" % "3.2" % "test"
)

seq(webSettings :_*)
