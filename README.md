# More modular IntelliJ CE SDK components

## Motivation
 
I use IntelliJ extensively for software development and I have recently found that its platform SDK libraries are awesome
for building various tools: in my case, I am piggybacking on the platform to develop various security-centric static analyses. However,
it doesn't appear that the community has prioritised the use of the platform SDK outside of the IntelliJ IDE and its derivatives. 
My use cases do not always require a full IDE and may even be run as small, headless tools. While I plan to write plugins 
for the IntelliJ IDE and potentially other IDEs, there are some aspects of my tools that make sense
on a continuous integration server and in other headless environments. Having bite-sized libraries from the IntelliJ platform
SDK that I can build upon is a good starting point for writing such tools. So, I have decided to package those libraries and 
to make them available on (Maven) Central, which someone else in a similar situation may find useful. Locate them under the 
group id *com.github.adedayo.intellij.sdk* at http://search.maven.org/
   
   
The idea of this fork of the IntelliJ IDEA Community Edition is simply to provide build scripts to package the components of IntelliJ
platform and plugins SDK as individual jar libraries available on (Maven) Central repositories making their usage outside the IDE easier. 
As I have found out, there is a bit of learning curve in navigating the IntelliJ SDK libraries (documentation is getting better though),
and knowing where the classes you need reside may involve a bit of searching, but hopefully having small bite-sized packaging of the libraries
will facilitate using the components that you need without having to bundle almost the entire IDE. It is also the hope that 
these bite-sized components may pave way for more future headless tools and libraries built on top of the awesome IntelliJ platform,
but which can run outside of the IntelliJ IDE, providing many of the goodness of the IntelliJ platform SDK and plugins in custom, 
potentially headless, IDE-independent environments. 

## Version on (Maven) Central
The artifact version number matches the trunk revision number on the IntelliJ CE repository. So, for example, the artifact
with the coordinate *com.github.adedayo.intellij.sdk:java-psi-impl:142.1* is the build of the *java-psi-impl* library from the
trunk revision *142*, and the moniker *1* is just for my own sequential numbering of the deployed version on Central. 
Newer builds, if necessary, of the same trunk version will simply have the release number increased to 2, 3, ...

**Note**: I've tried to be a good citizen on Central, providing both the libraries and the associated sources on Central. But it is only best effort,
because there were a *few* source files that I couldn't map to the relevant class file. These were only just a few - perhaps they were auto-generated during IntelliJ build, or
I couldn't somehow find them. Look at the Scala script https://github.com/adedayo/intellij-community/blob/master/modules-generator/GenerateModules.scala
that contains the core logic of my packaging and build to get an idea of what was done. Ideas and improvements to the packaging are indeed welcome, 
or better still if the IntelliJ community can take up the packaging, that would be awesome and make my fork unnecessary. 
That would be more desirable to me! :-)

## Build and deploy
 Note to self: to build and deploy, just issue *mvn package* from the root