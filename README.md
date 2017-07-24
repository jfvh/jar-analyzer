# jar-analyzer
This **maven plugin** checks all the (nested) dependencies in your project and prints the anlysis results. This is especially useful if problems with clashing dependencies occur (Dependency Hell). 

Current checks:
 - Package Check: Checks if multiple dependencies have equal package and class names. This can indicate a duplicate dependency, even if the groupId has been changed over time.
 - Version Check: Checks if dependencies with equal groupIds, have different version numbers. This can indicate problems with colliding versions.
 
 Note that the analysis output can help you solving dependeny problems,  but not all output indicates actual problems!
 
Suggestions for other checks, or input in any other way is very welcome! Please contribute and share.

**How to use this plugin:**

This plugin is currently not in a public repository, so you should build it yourself.
- clone/fork this repo
- $mvn package
    - The plugin is now in your local repo
- add the lines below to the pom.xml of the project you want to analyse

``` maven
    <build>
         <plugins>
             <plugin>
                 <groupId>nl.jfvh</groupId>
                 <artifactId>jar-analyzer</artifactId>
                 <version>1.0</version>
             </plugin>
         </plugins>
     </build>
```



**Todo**:
- test and fix for Windows
- Add parameter settings
    - Write log to separate file
    - Disable a check
    - ..?
   