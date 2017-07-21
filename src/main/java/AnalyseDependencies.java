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

        //print stuff
        for (Dependency dependency : dependencies) {

                System.out.println("-------------------------------");
                System.out.println(dependency.groupId());
                System.out.println(dependency.artifactId());
                System.out.println(dependency.version());

        }



    }
}
