package checks;

import model.Dependency;
import utils.PrintUtils;

import java.util.*;


public class PackageCheck implements Check {


    private List<Dependency> dependencies;

    public PackageCheck(List<Dependency> dependencies) {
        this.dependencies = dependencies;
    }

    @Override
    public String checkMessage() {
        return "Doing a check on duplicate package+classpath from different dependencies.";
    }

    @Override
    public List<String> checkOutput() {
        return doCheck();
    }


    private List<String> doCheck() {
        List<String> messages = new ArrayList<>();
        Map<String, List<Dependency>> classpaths = new HashMap<>();
        List<String> doubles = new ArrayList<>();
        for (Dependency dependency : dependencies) {
            for (String classpath : dependency.classPaths()) {
                if (classpaths.containsKey(classpath)) {
                    doubles.add(classpath);
                    List<Dependency> presentDependencies = classpaths.get(classpath);
                    presentDependencies.add(dependency);
                    classpaths.put(classpath, presentDependencies);
                } else {
                    List<Dependency> value = new ArrayList<>();
                    value.add(dependency);
                    classpaths.put(classpath, value);
                }
            }
        }
        if (doubles.isEmpty()) {
            messages.add("No duplicates were found.");
        } else {
            //reduce doubles to package level
            Map<String, List<Dependency>> packages = new HashMap<>();
            for (String doubleClassPath : doubles) {
                String packageName = PrintUtils.getPackageName(doubleClassPath);
                List<Dependency> dependencies = classpaths.get(doubleClassPath);
                if(packages.containsKey(packageName)){
                    List<Dependency> duplicateDependencies = packages.get(packageName);
                    for (Dependency dependency : dependencies) {
                        if(!duplicateDependencies.contains(dependency)){
                            duplicateDependencies.add(dependency);
                        }
                    }
                    packages.put(packageName,duplicateDependencies);
                }else{
                    packages.put(packageName,dependencies);
                }
            }
            Set<String> keys = packages.keySet();
            for (String key : keys) {
                List<Dependency> dependencies = packages.get(key);
                messages.add("Duplicates for "+key);
                int i =1;
                for (Dependency dependency : dependencies) {
                    messages.add("\t"+i++ +". "+PrintUtils.getPrettyDependencyString(dependency));
                }
            }
        }
        return messages;
    }




}
