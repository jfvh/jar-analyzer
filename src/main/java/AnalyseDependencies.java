import checks.Check;
import checks.PackageCheck;
import checks.VersionCheck;
import model.Dependency;
import org.apache.maven.artifact.Artifact;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.project.MavenProject;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 *
 * @goal analyseDependencies
 * @threadSafe
 * @requiresDependencyResolution compile
 */
public class AnalyseDependencies extends AbstractMojo{

    /**
     * @parameter property="project"
     * @required
     * @readonly
     */
    protected MavenProject mavenProject;


    public void execute() throws MojoExecutionException
    {
        Set<Artifact> artifacts = mavenProject.getArtifacts();
        JarParser jarParser = new JarParser();
        List<Dependency> dependencies = new ArrayList<>();
        for (Artifact artifact : artifacts) {
            try {
                dependencies.add(jarParser.parseJar(artifact.getFile().getPath()));
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        doChecks(dependencies);

    }

    private void doChecks(List<Dependency> dependencies){
        PackageCheck packageCheck = new PackageCheck(dependencies);
        printCheck(packageCheck);
        VersionCheck versionCheck = new VersionCheck(dependencies);
        printCheck(versionCheck);
    }



    private void printCheck(Check check){
        printWithHead(check.checkMessage());
        for (String message : check.checkOutput()) {
            printWithHead(message);
        }
    }

    private void printWithHead(String string){
        System.out.println("[JAR Analyser] "+string);
    }

}
