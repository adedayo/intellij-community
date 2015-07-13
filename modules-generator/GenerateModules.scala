import java.io.File
import java.net.URI
import java.nio.file._
import java.nio.file.attribute.BasicFileAttributes

import org.apache.maven.shared.invoker.{DefaultInvocationRequest, DefaultInvoker}

import scala.collection.mutable.ListBuffer
import scala.io.Source
import scala.xml.XML

/**
 * @author Adedayo Adetoye
 *
 */


val release = 1
val directories = List("ByteCodeViewer",
  "IntelliLang",
  "IntelliLang-java",
  "IntelliLang-python",
  "IntelliLang-xml",
  "RegExpSupport",
  "ShortcutPromoter",
  "adt-branding",
  "analysis-api",
  "analysis-impl",
  "android",
  "android-annotations",
  "android-common",
  "android-gradle-jps",
  "android-jps-plugin",
  "android-rt",
  "annotations",
  "annotations-common",
  "annotations-java8",
  "ant",
  "ant-jps-plugin",
  "antlayout",
  "appEngine-runtime",
  "assetstudio",
  "boot",
  "bootstrap",
  "build",
  "built-in-server",
  "built-in-server-api",
  "colorSchemes",
  "commander",
  "common",
  "common-eclipse-util",
  "common-javaFX-plugin",
  "community-resources",
  "compiler-impl",
  "compiler-openapi",
  "copyright",
  "core-api",
  "core-impl",
  "course-creator",
  "course-creator-python",
  "coverage",
  "coverage-common",
  "coverage_rt",
  "cucumber-jvm-formatter",
  "cucumber-test-runner",
  "cvs-core",
  "cvs-plugin",
  "ddmlib",
  "debugger-impl",
  "debugger-openapi",
  "devkit",
  "devkit-jps-plugin",
  "diff-api",
  "diff-impl",
  "dom-impl",
  "dom-openapi",
  "draw9patch",
  "duplicates-analysis",
  "dvcs-api",
  "dvcs-impl",
  "dvlib",
  "eclipse",
  "eclipse-jps-plugin",
  "editor-ui-api",
  "editor-ui-ex",
  "editorconfig",
  "educational",
  "execution-impl",
  "execution-openapi",
  "extensions",
  "external-system-api",
  "external-system-impl",
  "forms-compiler",
  "forms_rt",
  "git4idea",
  "git4idea-rt",
  "github",
  "google-app-engine",
  "google-app-engine-jps-plugin",
  "gradle",
  "gradle-jps-plugin",
  "gradle-tooling-extension-api",
  "gradle-tooling-extension-impl",
  "groovy-jps-plugin",
  "groovy-psi",
  "groovy-rt-constants",
  "groovy_rt",
  "hg4idea",
  "icons",
  "idea-ui",
  "images",
  "indexing-api",
  "indexing-impl",
  "instrumentation-util",
  "intellilang-jps-plugin",
  "interactive-learning",
  "interactive-learning-python",
  "ipnb",
  "java-analysis-api",
  "java-analysis-impl",
  "java-decompiler-engine",
  "java-decompiler-plugin",
  "java-i18n",
  "java-impl",
  "java-indexing-api",
  "java-indexing-impl",
  "java-psi-api",
  "java-psi-impl",
  "java-runtime",
  "java-structure-view",
  "javaFX",
  "javaFX-CE",
  "javaFX-jps-plugin",
  "javac2",
  "javacvs-src",
  "jetgroovy",
  "jira",
  "jps-builders",
  "jps-launcher",
  "jps-model-api",
  "jps-model-impl",
  "jps-model-serialization",
  "jps-plugin-system",
  "jps-standalone-builder",
  "json",
  "jsp-base-openapi",
  "jsp-openapi",
  "jsp-spi",
  "junit",
  "junit_rt",
  "lang-api",
  "lang-impl",
  "layoutlib-api",
  "lint-api",
  "lint-checks",
  "lvcs-api",
  "lvcs-impl",
  "manifest",
  "manifest-merger",
  "maven",
  "maven-artifact-resolver-m2",
  "maven-artifact-resolver-m3",
  "maven-artifact-resolver-m31",
  "maven-jps-plugin",
  "maven-server-api",
  "maven2-server-impl",
  "maven3-server-common",
  "maven30-server-impl",
  "maven32-server-impl",
  "ninepatch",
  "openapi",
  "perflib",
  "platform-api",
  "platform-impl",
  "platform-main",
  "platform-resources",
  "platform-resources-en",
  "projectModel-api",
  "projectModel-impl",
  "properties",
  "properties-psi-api",
  "properties-psi-impl",
  "protocol-reader-runtime",
  "python-community",
  "python-community-build",
  "python-community-plugin",
  "python-edu-build",
  "python-educational",
  "python-helpers",
  "python-ide-community",
  "python-openapi",
  "python-plugin-tests",
  "python-psi-api",
  "python-pydev",
  "python-rest",
  "relaxng",
  "remote-servers-agent-rt",
  "remote-servers-api",
  "remote-servers-git",
  "remote-servers-impl",
  "remote-servers-java-api",
  "remote-servers-java-impl",
  "resources",
  "resources-en",
  "rest",
  "script-debugger-backend",
  "script-debugger-ui",
  "sdk-common",
  "sdklib",
  "smRunner",
  "smartcvs-src",
  "spellchecker",
  "structuralsearch",
  "structuralsearch-groovy",
  "structuralsearch-java",
  "structure-view-api",
  "structure-view-impl",
  "svn4idea",
  "tasks-api",
  "tasks-core",
  "tasks-java",
  "terminal",
  "testFramework",
  "testFramework-java",
  "testRunner",
  "testng",
  "testng_rt",
  "tests_bootstrap",
  "typeMigration",
  "ui-designer",
  "ui-designer-core",
  "ui-designer-jps-plugin",
  "usageView",
  "util",
  "util-rt",
  "vcs-api",
  "vcs-api-core",
  "vcs-impl",
  "vcs-log-api",
  "vcs-log-graph",
  "vcs-log-graph-api",
  "vcs-log-impl",
  "xdebugger-api",
  "xdebugger-impl",
  "xml",
  "xml-analysis-api",
  "xml-analysis-impl",
  "xml-openapi",
  "xml-psi-api",
  "xml-psi-impl",
  "xml-structure-view-api",
  "xml-structure-view-impl",
  "xpath",
  "xslt-debugger",
  "xslt-debugger-engine",
  "xslt-debugger-engine-impl",
  "xslt-rt")
val bundledLibraries = List(
  ("asm-all", "asm5-src.zip"),
  ("trove4j", "trove4j_src.jar")
)
bundledLibraries.foreach(lib => {
  val (name, archiveName) = lib
  buildBundledLibrary(name, archiveName)
})

var classToSourceMap = Map[Path, Path]()
setupProjects
mapSources
invokeBuild



def buildBundledLibrary(name:String,archiveName:String) = {
  val srcPath = Paths.get(s"lib/src/$archiveName").toAbsolutePath
  val srcDir = Paths.get(s"out_maven/$name/src/main/java")
  if (Files.exists(srcDir)) {
    delete(srcDir.toFile)
    Files.createDirectories(srcDir)
  } else {
    Files.createDirectories(srcDir)
  }
  import scala.collection.JavaConversions._
  val zipFS = FileSystems.newFileSystem(URI.create("jar:file:" + srcPath.toUri.getPath), Map[String, String]())
  Files.walkFileTree(zipFS.getPath("/"), new SimpleFileVisitor[Path] {

    override def preVisitDirectory(dir: Path, attrs: BasicFileAttributes) = {
      val dirToCreate = Paths.get(srcDir.toString, dir.toString)
      if (Files.notExists(dirToCreate)) {
        Files.createDirectory(dirToCreate);
      }
      FileVisitResult.CONTINUE
    }

    override def visitFile(file: Path, attr: BasicFileAttributes) = {
      val destFile = Paths.get(srcDir.toString, file.toString)
      Files.copy(file, destFile, StandardCopyOption.REPLACE_EXISTING);
      FileVisitResult.CONTINUE
    }

  })

  XML.save(s"out_maven/$name/pom.xml", generatePOM(name))

  val invoker = new DefaultInvoker
  val request = new DefaultInvocationRequest
  request.setPomFile(new File(s"out_maven/$name/pom.xml"))
  import scala.collection.JavaConversions._
  val goals = List("compile",
    "org.apache.maven.plugins:maven-jar-plugin:jar",
    "org.apache.maven.plugins:maven-source-plugin:jar-no-fork",
    "org.apache.maven.plugins:maven-javadoc-plugin:jar",
    "org.apache.maven.plugins:maven-gpg-plugin:sign",
    "org.apache.maven.plugins:maven-install-plugin:2.3.1:install"
  )
  request.setGoals(goals)
  invoker.execute(request)
}

def setupProjects = {
  println("Setting up ...")
  directories.par.foreach(dir => {
    val destination = s"out/classes/production/$dir"
    val target = Paths.get(s"out_maven/$dir/target/classes")
    val source = Paths.get(destination)

    if (Files.exists(target)) {
      delete(target.toFile)
    } else {
      Files.createDirectories(target)
      Files.walkFileTree(source, new CopyDirVisitor(source, target))
      XML.save(s"out_maven/$dir/pom.xml", generatePOM(dir))
    }
  })
}

def delete(file: File): Unit = {
  if (file.isDirectory) {
    val content = file.listFiles()
    if (content.isEmpty)
      file.delete
    else {
      content.foreach(delete)
    }
  } else file.delete
}

def generatePOM(project: String, version: String = Source.fromFile("build.txt").getLines.next) = {
  val branch = version.replace(".SNAPSHOT", "")
  val pom =
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/maven-v4_0_0.xsd">
  <modelVersion>4.0.0</modelVersion>
  <groupId>com.github.adedayo.intellij.sdk</groupId>
  <artifactId>{project}</artifactId>
  <version>{branch}.{release}</version>
  <packaging>jar</packaging>
  <name>{project}</name>
  <url>http://www.jetbrains.com/idea</url>
  <description>A packaging of the IntelliJ Community Edition {project} library.
  This is release number {release} of trunk branch {branch}.
  </description>

  <licenses>
    <license>
      <name>Apache License, Version 2.0</name>
      <url>http://www.apache.org/licenses/LICENSE-2.0.txt</url>
    </license>
  </licenses>


  <properties>
    <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
    <maven.javadoc.failOnError>false</maven.javadoc.failOnError>
  </properties>

  <developers>
    <developer>
      <id>adedayo</id>
      <name>Adedayo Adetoye</name>
      <email>dayo.dev@gmail.com</email>
    </developer>
  </developers>

  <scm>
    <connection>scm:git:git@github.com:adedayo/intellij-community.git</connection>
    <developerConnection>scm:git:git@github.com:adedayo/intellij-community.git</developerConnection>
    <url>https://github.com/adedayo/intellij-community.git</url>
  </scm>

  <distributionManagement>
    <snapshotRepository>
      <id>ossrh</id>
      <url>https://oss.sonatype.org/content/repositories/snapshots</url>
    </snapshotRepository>
    <repository>
      <id>ossrh</id>
      <url>https://oss.sonatype.org/service/local/staging/deploy/maven2/</url>
    </repository>
  </distributionManagement>

  <build>
    <plugins>
      <plugin>
        <groupId>org.apache.maven.plugins</groupId>
        <artifactId>maven-jar-plugin</artifactId>
        <version>2.6</version>
      </plugin>
      <plugin>
        <groupId>org.apache.maven.plugins</groupId>
        <artifactId>maven-source-plugin</artifactId>
        <version>2.4</version>
        <executions>
          <execution>
            <id>attach-sources</id>
            <goals>
              <goal>jar-no-fork</goal>
            </goals>
          </execution>
        </executions>
      </plugin>
      <plugin>
        <groupId>org.apache.maven.plugins</groupId>
        <artifactId>maven-javadoc-plugin</artifactId>
        <version>2.10.3</version>
        <executions>
          <execution>
            <id>attach-javadocs</id>
            <goals>
              <goal>jar</goal>
            </goals>
          </execution>
        </executions>
      </plugin>
      <plugin>
        <groupId>org.apache.maven.plugins</groupId>
        <artifactId>maven-gpg-plugin</artifactId>
        <version>1.6</version>
        <executions>
          <execution>
            <id>sign-artifacts</id>
            <phase>verify</phase>
            <goals>
              <goal>sign</goal>
            </goals>
          </execution>
        </executions>
      </plugin>
      <plugin>
        <groupId>org.sonatype.plugins</groupId>
        <artifactId>nexus-staging-maven-plugin</artifactId>
        <version>1.6.5</version>
        <extensions>true</extensions>
        <configuration>
          <serverId>ossrh</serverId>
          <nexusUrl>https://oss.sonatype.org/</nexusUrl>
          <autoReleaseAfterClose>true</autoReleaseAfterClose>
        </configuration>
      </plugin>
    </plugins>
  </build>
</project>

  pom
}

def invokeBuild = {
  println("Packaging Projects ...")
  directories.par.foreach(dir => {
    val invoker = new DefaultInvoker
    val request = new DefaultInvocationRequest
    request.setPomFile(new File(s"out_maven/$dir/pom.xml"))
    import scala.collection.JavaConversions._
    val goals = List("org.apache.maven.plugins:maven-jar-plugin:jar",
      "org.apache.maven.plugins:maven-source-plugin:jar-no-fork",
      "org.apache.maven.plugins:maven-javadoc-plugin:jar",
    "org.apache.maven.plugins:maven-gpg-plugin:sign",
    "org.apache.maven.plugins:maven-install-plugin:2.3.1:install"
    )
    request.setGoals(goals)
    invoker.execute(request)
  })
}

def mapSources = {

  val classPaths = directories.map(dir=>{Paths.get(s"out_maven/$dir/target/classes")})
  val classes = findJavaClasses(classPaths: _*).filter(!_.toString.contains("$"))
  val sourcePath = Paths.get("")
  val sources = findJavaAndGroovySources(sourcePath)

  var sourceMap = Map[String, Path]()
  val pkg = "\\s*package\\s+(.*)".r
  sources.foreach(src => {
    try {
      val file = Source.fromFile(src.toFile)
      val package_ = file.getLines.find(line => line.matches("\\s*package\\s+.*")) match {
        case Some(line) => {
          val pkg(data) = line
          data
        }
        case None => ""
      }
      file.close
      val name = package_.trim.replace('.', '/').replaceAll(";","")
      if(!name.isEmpty) {
       sourceMap.synchronized({
         val fileName = src.getFileName.toString
         if (fileName.endsWith(".java"))
           sourceMap += ((name + "/" + fileName.dropRight(5), src))
         else if (fileName.endsWith(".groovy"))
           sourceMap += ((name + "/" + fileName.dropRight(7), src))
         else
           sourceMap += ((name + "/" + fileName, src))
       })
      }
    } catch {
      case ex: Throwable =>println(ex.getMessage + " in " + src)
    }
  })

  classes.foreach(cls => {
    val path = cls.toString.dropRight(6).replaceFirst(".*/target/classes/","")
    try {
      classToSourceMap += ((cls, sourceMap(path)))
    }catch{
      case ex:Throwable =>
  }
  })

  directories.par.foreach(dir => {
    val classes = findJavaClasses(Paths.get(s"out_maven/$dir/target/classes")).filter(!_.toString.contains("$"))
    classes.par.foreach(clz => {
      val parent = clz.toString.replaceFirst(".*/target/classes/", "")
      val index = parent.lastIndexOf('.')
      val path = if (index > 0) parent.substring(0, index) else parent.dropRight(6)
      try {
        if (sourceMap.contains(path)) {
          val indx = path.lastIndexOf('/')

          val target = Paths.get(s"out_maven/$dir/src/main/java/${path.substring(0, indx)}")
          if (!Files.exists(target)) {
            Files.createDirectories(target)
          }
          val file = sourceMap(path)
          val src = file.getParent
          println(s"Copying file $file")
          Files.copy(file, target.resolve(src.relativize(file)), StandardCopyOption.REPLACE_EXISTING)
        }
      } catch {
        case ex: Throwable =>
      }
    })
  })
}

def findJavaClasses(paths: Path*): List[Path] = {
  find(paths: _*)("glob:*.class")
}

def findJavaAndGroovySources(paths: Path*): List[Path] = {
  find(paths: _*)("glob:*.{java,groovy}")
}

def find(paths: Path*)(glob: String): List[Path] = {
  val files = ListBuffer[Path]()
  def append(name: Path): Unit = files.synchronized {
    files += name
  }
  paths.par.foreach(path => Files.walkFileTree(path, new FileFinder(append)(glob)(FileSystems.getDefault)))
  files.toList
}

class FileFinder(callback: Path => Unit)(ext: String)(fileSystem: FileSystem = FileSystems.getDefault)
  extends SimpleFileVisitor[Path] {
  val matcher = fileSystem.getPathMatcher(ext)

  override def visitFile(path: Path, attr: BasicFileAttributes): FileVisitResult = {
    val name = path.getFileName
    if (name != null && matcher.matches(name)) callback(path)
    FileVisitResult.CONTINUE
  }
}

class CopyDirVisitor(src: Path, dest: Path) extends SimpleFileVisitor[Path] {
  override def preVisitDirectory(dir: Path, attrs: BasicFileAttributes) = {
    val target = dest.resolve(src.relativize(dir))
    println("Copying files to " + dest)
    if (!Files.exists(target)) Files.createDirectory(target)
    FileVisitResult.CONTINUE
  }

  override def visitFile(file: Path, attrs: BasicFileAttributes) = {
    Files.copy(file, dest.resolve(src.relativize(file)), StandardCopyOption.REPLACE_EXISTING)
    FileVisitResult.CONTINUE
  }
}
