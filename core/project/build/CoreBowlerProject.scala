import sbt._


class CoreBowlerProject(info: ProjectInfo) extends DefaultProject(info){//}  with ChecksumPlugin{
	
	//override def scanDirectories = Nil

  val slf4jVersion = "1.6.0"
	val scalatest = "org.scalatest" % "scalatest" %  "1.2" % "test"

  val scalatraTest = "org.scalatra" %% "scalatra-scalatest" % "2.0.0.M2" % "test"

	val jetty6 = "org.mortbay.jetty" % "jetty" % "6.1.14" % "test"
	
	val scalaCompiler = "org.scala-lang" % "scala-compiler" % "2.8.1"
	
	val servletApi = "javax.servlet" % "servlet-api" % "2.5" % "provided"
	val scalatra = "org.scalatra" %% "scalatra" % "2.0.0.M2"
  
  val scalatraFileUpload = "org.scalatra" %% "scalatra-fileupload" % "2.0.0.M2"

	val commons = "com.recursivity" % "recursivity-commons_2.8.1" % "0.3"
  	val scalate = "org.fusesource.scalate" % "scalate-core" % "1.3.2"


  val liftJson = "net.liftweb" % "lift-json_2.8.1" % "2.2"

  	val sfl4japi = "org.slf4j" % "slf4j-api" % slf4jVersion
  	val sfl4jnop = "org.slf4j" % "slf4j-nop" % slf4jVersion % "runtime"

  Credentials(Path.userHome / ".ivy2" / ".credentials", log)

  val publishTo = {
	if(version.toString.endsWith("-SNAPSHOT"))
		"Sonatype Nexus Snapshots" at "https://oss.sonatype.org/content/repositories/snapshots"
	else "Sonatype Nexus Staging" at "https://oss.sonatype.org/service/local/staging/deploy/maven2"
  }

  override def managedStyle = ManagedStyle.Maven

  //override def deliverProjectDependencies = Nil


  override def deliverProjectDependencies = Nil
	override def packageDocsJar = defaultJarPath("-javadoc.jar")
  override def packageSrcJar= defaultJarPath("-sources.jar")
  lazy val sourceArtifact = Artifact.sources(artifactID)
  lazy val docsArtifact = Artifact.javadoc(artifactID)

override def packageToPublishActions = super.packageToPublishActions ++ Seq(packageDocs, packageSrc)
 override def pomExtra = {

    // If these aren't lazy, then the build crashes looking for
    // ${moduleName}/project/build.properties.


	(
	    <name>{name}</name>
	    <description>Bowler Framework Core  Project POM</description>
	    <url>http://bowlerframework.org</url>
	    <inceptionYear>2010</inceptionYear>
	    <organization>
	      <name>Bowler Framework Core Project</name>
	      <url>http://bowlerframework.org</url>
	    </organization>
	    <licenses>
	      <license>
	        <name>BSD</name>
	        <url>http://github.com/wfaler/Bowler/LICENSE</url>
	        <distribution>repo</distribution>
	      </license>
	    </licenses>
		<mailingLists>
		    <mailingList>
		      <name>Bowler user group</name>
		      <archive>http://groups.google.com/group/bowler-users</archive>
		      <post>bowler-users@googlegroups.com</post>
		      <subscribe>bowler-users+subscribe@googlegroups.com</subscribe>
		      <unsubscribe>bowler-users+unsubscribe@googlegroups.com</unsubscribe>
		    </mailingList>
		  </mailingLists>
	    <scm>
	      <connection>scm:git:git://github.com/wfaler/Bowler.git</connection>
	      <url>http://github.com/wfaler/Bowler</url>
	    </scm>
	    <developers>
	      <developer>
	        <id>wfaler</id>
	        <name>Wille Faler</name>
	        <url>http://blog.recursivity.com</url>
	      </developer>
	    </developers>)
  }



	val sonatypeNexusSnapshots = "Sonatype Nexus Snapshots" at "https://oss.sonatype.org/content/repositories/snapshots"
	val sonatypeNexusReleases = "Sonatype Nexus Releases" at "https://oss.sonatype.org/content/repositories/releases"

  	val scalateRepo = "scalate repo" at "http://repo.fusesource.com/nexus/content/repositories/public/"

  val scalaToolsRepo = "Scala-Tools repo" at "http://scala-tools.org/repo-releases/"
}