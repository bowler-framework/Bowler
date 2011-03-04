import sbt._

class BowlerParentProject(info: ProjectInfo) extends ParentProject(info)
{
   def toolConfigurationFile = path("config")

   def subProject(path: Path, name: String) =
      project(path, name, new ExampleSubProject(_))

   lazy val core = subProject("core", "BowlerCoreProject")
   lazy val testing = subProject("testing", "TestingProject")
   lazy val persistence = subProject("persistence-mapper", "PersistenceProject")
   lazy val squeryl = subProject("squeryl-mapper", "SquerylProject")
   lazy val jpa = subProject("jpa-mapper", "JpaProject")
   //lazy val subB = subProject("subB", "Sub Project B")

   class ExampleSubProject(info: ProjectInfo) extends DefaultProject(info){}



  Credentials(Path.userHome / ".ivy2" / ".credentials", log)

  val publishTo = {
	if(version.toString.endsWith("-SNAPSHOT"))
		"Sonatype Nexus Snapshots" at "https://oss.sonatype.org/content/repositories/snapshots"
	else "Sonatype Nexus Staging" at "https://oss.sonatype.org/service/local/staging/deploy/maven2"
  }

  override def managedStyle = ManagedStyle.Maven

  //override def deliverProjectDependencies = Nil


  override def deliverProjectDependencies = Nil
	//override def packageDocsJar = defaultJarPath("-javadoc.jar")
  //override def packageSrcJar= defaultJarPath("-sources.jar")
  lazy val sourceArtifact = Artifact.sources(artifactID)
  lazy val docsArtifact = Artifact.javadoc(artifactID)

 //override def packageToPublishActions = super.packageToPublishActions ++ Seq(packageDocs, packageSrc)
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
}