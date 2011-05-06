import sbt._

class BowlerParentProject(info: ProjectInfo) extends ParentProject(info) {
  def toolConfigurationFile = path("config")

  lazy val core = project("core", "core", new CoreProject(_))
  lazy val persistence = project("persistence-mapper", "persistence-mapper", new PersistenceProject(_), core)
  lazy val squeryl = project("squeryl-mapper", "squeryl-mapper", new SquerylProject(_), persistence)
  lazy val jpa = project("jpa-mapper", "jpa-mapper", new JpaProject(_),
    persistence)

    val sonatypeNexusSnapshots = "Sonatype Nexus Snapshots" at "https://oss.sonatype.org/content/repositories/snapshots"
    val sonatypeNexusReleases = "Sonatype Nexus Releases" at "https://oss.sonatype.org/content/repositories/releases"
    val scalateRepo = "scalate repo" at "http://repo.fusesource.com/nexus/content/repositories/public/"
    val scalaToolsRepo = "Scala-Tools repo" at "http://scala-tools.org/repo-releases/"


  class BaseProject(info: ProjectInfo) extends DefaultProject(info){//} with ChecksumPlugin{
    val slf4jVersion = "1.6.0"
    val scalatest = "org.scalatest" % "scalatest" % "1.3" % "test"
    val scalatraTest = "org.scalatra" %% "scalatra-scalatest" % "2.0.0.M3" % "test"
    val jetty6 = "org.mortbay.jetty" % "jetty" % "6.1.14" % "test"
    val scalaCompiler = "org.scala-lang" % "scala-compiler" % "2.8.1"
    val servletApi = "javax.servlet" % "servlet-api" % "2.5" % "provided"
    val sfl4japi = "org.slf4j" % "slf4j-api" % slf4jVersion
    val sfl4jnop = "org.slf4j" % "slf4j-nop" % slf4jVersion % "runtime"



    lazy val sourceArtifact = Artifact.sources(artifactID)
    lazy val docsArtifact = Artifact.javadoc(artifactID)

    override def packageDocsJar = defaultJarPath("-javadoc.jar")

    override def packageSrcJar = defaultJarPath("-sources.jar")

    override def packageToPublishActions = super.packageToPublishActions ++ Seq(packageDocs, packageSrc)

    override def managedStyle = ManagedStyle.Maven

    //  override def deliverProjectDependencies = Nil

    override def pomExtra = {
      // If these aren't lazy, then the build crashes looking for
      // ${moduleName}/project/build.properties.
      (
        <name>
          {name}
        </name>
          <description>Bowler Framework Project POM</description>
          <url>http://bowlerframework.org</url>
          <inceptionYear>2010</inceptionYear>
          <organization>
            <name>Bowler Framework Project</name>
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

  class CoreProject(info: ProjectInfo) extends BaseProject(info) {
    val scalatra = "org.scalatra" %% "scalatra" % "2.0.0.M3"
    val scalatraFileUpload = "org.scalatra" %% "scalatra-fileupload" % "2.0.0.M3"
    val commons = "com.recursivity" % "recursivity-commons_2.8.1" % "0.5.3"
    val scalate = "org.fusesource.scalate" % "scalate-core" % "1.4.1"
    val liftJson = "net.liftweb" % "lift-json_2.8.1" % "2.3"
  }

  class PersistenceProject(info: ProjectInfo) extends BaseProject(info) {
    val c3p0 = "c3p0" % "c3p0" % "0.9.1.2" % "test"
    val jettyWebapp = "org.eclipse.jetty" % "jetty-webapp" % "7.2.0.v20101020" % "test"
    val squeryl = "org.squeryl" % "squeryl_2.8.1" % "0.9.4-RC6" % "test"
    val h2database = "com.h2database" % "h2" % "1.2.144" % "test"
    //	val bowlerCore = "org.bowlerframework" % "core_2.8.1" % projectVersion
  }

  class SquerylProject(info: ProjectInfo) extends BaseProject(info) {
    val c3p0 = "c3p0" % "c3p0" % "0.9.1.2"
    val jettyWebapp = "org.eclipse.jetty" % "jetty-webapp" % "7.2.0.v20101020" % "test"
    val squeryl = "org.squeryl" % "squeryl_2.8.1" % "0.9.4-RC6"

    lazy val bowlerDep = persistence

    val h2database = "com.h2database" % "h2" % "1.2.144" % "test"

  }

  class JpaProject(info: ProjectInfo) extends BaseProject(info) {
    val hibernateEntityManager = "org.hibernate" % "hibernate-entitymanager" % "3.6.1.Final" % "provided"
    lazy val bowlerDep = persistence
    //val c3p0 = "c3p0" % "c3p0" % "0.9.1.2"
    val jpa = "com.recursivity" % "recursivity-jpa_2.8.1" % "1.0"
    val h2database = "hsqldb" % "hsqldb" % "1.8.0.7" % "test"
    val jbossRepo = "JBoss Repo" at "https://repository.jboss.org/nexus/content/repositories/releases/"
  }


  Credentials(Path.userHome / ".ivy2" / ".credentials", log)

  val publishTo = {
    if (version.toString.endsWith("-SNAPSHOT"))
      "Sonatype Nexus Snapshots" at "https://oss.sonatype.org/content/repositories/snapshots"
    else "Sonatype Nexus Staging" at "https://oss.sonatype.org/service/local/staging/deploy/maven2"
  }


}