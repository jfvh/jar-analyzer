package checks;

import model.Dependency;
import utils.PrintUtils;

import java.util.ArrayList;
import java.util.List;

public class DoubleDependencyCheck implements Check {

    private List<Dependency> dependencies;


    public DoubleDependencyCheck(List<Dependency> dependencies) {
        this.dependencies = dependencies;
    }

    @Override
    public String checkMessage() {
        return "Checking duplicate dependencies";
    }

    @Override
    public List<String> checkOutput() {
        List<Dependency> doubles = new ArrayList<>();
        List<Dependency> allDependencies = new ArrayList<>();
        List<String> messages = new ArrayList<>();
        for (Dependency dependency : dependencies) {
            if(allDependencies.contains(dependency)){
                doubles.add(dependency);
            }else{
                allDependencies.add(dependency);
            }
        }
        if(doubles.size()==0){
            messages.add("No duplicate dependencies were found");
        }else{
            messages.add("Duplicate dependencies were found:");
            for (Dependency dependency : doubles) {
                messages.add(" . "+PrintUtils.getPrettyDependencyString(dependency));
            }
        }
        return messages;
    }
}
