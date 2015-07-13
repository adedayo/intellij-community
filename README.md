# More modular IntelliJ CE SDK components
## Motivation
 
 The idea of this fork of the IntelliJ IDEA Community Edition is to provide scripts to build various components of IntelliJ
as separate jar libraries available on Maven central repositories making their usage easier.

My use case is a small static analysis library using IntelliJ SDK components, but which should not have a dependency
 on the full IntelliJ IDE environment. So, I could run the tool for example on CI servers.  

The hope is that these bite-size components may pave way for future headless tools and libraries built on top of the IntelliJ
 platform but that can run outside of the InteliJ IDE, providing many of the awesome goodness of the IntelliJ SDK and plugins 
 in custom, potentially headless, IDE-independent environments. 

## Build and deploy
 Note to self: to build and deploy, just issue *mvn package* from the root