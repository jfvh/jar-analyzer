# jar-analyzer
This MAVEN plugin checks all the (nested) dependencies in your project and prints the anlysis results. This is especially useful if problems with clashing dependencies occur (Dependency Hell). 

Current checks:
 - Package Check: Checks if multiple dependencies have equal package and class names. This can indicate a duplicate dependency, even if the groupId has been changed over time.
 - Version Check: Checks if dependencies with equal groupIds, have different version numbers. This can indicate problems with colliding versions.
 
 Note that the analysis output can help you solving dependeny problems,  but not all output indicates actual problems!
 
Suggestions for other checks, or input in any other way is very welcome! Please contribute and share.

Todo:
- test and fix for Windows
- Add parameter settings
    - Write log to separate file
    - Disable a check
    - ..?
   