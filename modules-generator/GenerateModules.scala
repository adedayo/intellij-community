import java.io.File
import java.nio.file.attribute.BasicFileAttributes
import java.nio.file._
import java.util.Collections

import org.apache.maven.shared.invoker.{DefaultInvocationRequest, DefaultInvoker}

import scala.io.Source
import scala.xml.XML

/**
 * @author Adedayo Adetoye
 *
 */

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

setupProjects
invokeBuild

def setupProjects = {
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

  val pom =
    <project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
             xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/maven-v4_0_0.xsd">
      <modelVersion>4.0.0</modelVersion>
      <groupId>com.github.adedayo.intellij.sdk</groupId>
      <artifactId>{project}</artifactId>
      <version>{version.replace(".SNAPSHOT", "-SNAPSHOT")}</version>
      <packaging>jar</packaging>
      <name>{project}</name>
      <url>http://www.jetbrains.com/idea</url>

      <licenses>
        <license>
          <name>Apache License, Version 2.0</name>
          <url>http://www.apache.org/licenses/LICENSE-2.0.txt</url>
        </license>
      </licenses>

      <scm><connection>scm:git:git@github.com:adedayo/intellij-community.git</connection></scm>

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
            <artifactId>maven-source-plugin</artifactId>
            <version>2.2.1</version>
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
            <version>2.9.1</version>
            <executions>
              <execution>
                <id>attach-javadocs</id>
                <goals>
                  <goal>jar</goal>
                </goals>
              </execution>
            </executions>
          </plugin>
        </plugins>
      </build>

    </project>
  pom

}

def invokeBuild = {

  directories.par.foreach(dir => {
    val invoker = new DefaultInvoker
    val request = new DefaultInvocationRequest
    request.setPomFile(new File(s"out_maven/$dir/pom.xml"))
    request.setGoals(Collections.singletonList("package"))
    invoker.execute(request)
  })
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
