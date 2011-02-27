import sbt._


class BowlerJpaProject(info: ProjectInfo) extends DefaultProject(info){//} with ChecksumPlugin{
	  val bowler = "org.bowlerframework" % "persistence-mapper_2.8.1" % "0.2.2-SNAPSHOT"

	  val slf4jVersion = "1.6.0"

	  val sfl4jnop = "org.slf4j" % "slf4j-nop" % slf4jVersion % "test"
	
	val hibernateEntityManager = "org.hibernate" % "hibernate-entitymanager" % "3.5.1-Final" % "provided"

  val scalatest = "org.scalatest" % "scalatest" % "1.2" % "test"

      //val c3p0 = "c3p0" % "c3p0" % "0.9.1.2"
	
	  val jpa = "com.recursivity" % "recursivity-jpa_2.8.1" % "1.0"

	  val h2database = "hsqldb" % "hsqldb" % "1.8.0.7" % "test"
	
		val jbossRepo = "JBoss repo" at "http://repository.jboss.com/maven2/"

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
		    <description>Bowler Framework JPA Mapper  Project POM</description>
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