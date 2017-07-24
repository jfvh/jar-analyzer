package checks;

import model.Dependency;
import utils.PrintUtils;

import java.util.*;

public class VersionCheck implements Check{

private List<Dependency> dependencies;

    public VersionCheck(List<Dependency> dependencies) {
        this.dependencies = dependencies;
    }

    @Override
    public String checkMessage() {
        return "Checking the version numbers of the dependencies";
    }

    @Override
    public List<String> checkOutput() {
        List<String> messages = new ArrayList<>();
        Map<String,Map<String,List<Dependency>>> groupIds = new HashMap<>();

        //make a tree grouped per groupId and Version
        for (Dependency dependency : dependencies) {
            if(groupIds.containsKey(dependency.groupId())){
                Map<String, List<Dependency>> dependenciesWithGroupId = groupIds.get(dependency.groupId());
                List<Dependency> dependenciesPerVersion = new ArrayList<>();
                if(dependenciesWithGroupId.containsKey(dependency.version())){
                    dependenciesPerVersion = dependenciesWithGroupId.get(dependency.version());
                }
                dependenciesPerVersion.add(dependency);
                dependenciesWithGroupId.put(dependency.version(),dependenciesPerVersion);
                groupIds.put(dependency.groupId(),dependenciesWithGroupId);
            }else{ //groupId not present
                List<Dependency> dependenciesPerVersion = new ArrayList<>();
                dependenciesPerVersion.add(dependency);
                Map<String, List<Dependency>> dependenciesWithGroupId = new HashMap<>();
                dependenciesWithGroupId.put(dependency.version(),dependenciesPerVersion);
                groupIds.put(dependency.groupId(),dependenciesWithGroupId);
            }
        }

        //create message output
        for (String key : groupIds.keySet()) {
            Map<String, List<Dependency>> versionMap = groupIds.get(key);
            if(versionMap.size()>1){
                messages.add("Multiple versions found for groupId {"+key+"}");
                for (String k2 : versionMap.keySet()) {
                    messages.add(" . With version {"+k2+"}, the following dependencies were found:");
                    for (Dependency dependency : versionMap.get(k2)) {
                        messages.add(" . . "+PrintUtils.getPrettyDependencyString(dependency));
                    }

                }

            }
        }
        if(messages.size()==0){
            messages.add("No version number collisions found");
        }
        return messages;
    }

}
