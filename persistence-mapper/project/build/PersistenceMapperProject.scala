import sbt._


class PersistenceMapperProject(info: ProjectInfo) extends DefaultProject(info) {//with ChecksumPlugin{
	  val bowler = "org.bowlerframework" % "bowler-core_2.8.1" % "0.2.1"

	  val slf4jVersion = "1.6.0"

	  val sfl4jnop = "org.slf4j" % "slf4j-nop" % slf4jVersion % "test"
	
	val scalaLang = "org.scala-lang" % "scala-library" % "2.8.1"

  	  val c3p0 = "c3p0" % "c3p0" % "0.9.1.2" % "test"


	// allows you to use an embedded/in-JVM jetty-server to run unit-tests.
	  val scalatraTest = "org.scalatra" %% "scalatra-scalatest" % "2.0.0.M2" % "test"

	  val jetty6 = "org.eclipse.jetty" % "jetty-server" % "7.2.0.v20101020" % "test"
		val jettyWebapp = "org.eclipse.jetty" % "jetty-webapp" % "7.2.0.v20101020" % "test"

	  val servletApi = "javax.servlet" % "servlet-api" % "2.5" % "provided"
	
	  val squeryl = "org.squeryl" % "squeryl_2.8.0" % "0.9.4-RC3" % "test"

	  val h2database = "com.h2database" % "h2" % "1.2.144" % "test"

	  val sonatypeNexusSnapshots = "Sonatype Nexus Snapshots" at "https://oss.sonatype.org/content/repositories/snapshots" 
	  val sonatypeNexusReleases = "Sonatype Nexus Releases" at "https://oss.sonatype.org/content/repositories/releases"

	  val scalaToolsRepo = "Scala-Tools repo" at "http://scala-tools.org/repo-releases/"
	
	
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
		    <description>Bowler Framework Persistence Mapper  Project POM</description>
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
}