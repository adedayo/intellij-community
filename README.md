# A fork of the IntelliJ for more modular SDK components
## Motivation
 
 The idea of this fork of the IntelliJ IDEA Community Edition is to provide scripts to build various components of IntelliJ
as separate jar libraries available on Maven central repositories making their usage, for example in small (static analysis - my use case) 
tools that do not require the full IDE environment as dependency.  

This may pave way for future headless tools and libraries that can run outside of the InteliJ IDE, providing many of the 
  awesome goodness of the IntelliJ SDK and plugins in custom headless IDE-independent tools. 

