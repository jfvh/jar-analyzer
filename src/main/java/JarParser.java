
import model.*;
import model.Package;
import org.apache.commons.lang3.StringUtils;

import java.io.FileInputStream;
import java.text.ParseException;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;


public class JarParser {

    /**
     * Gives a list of packages based on the filepath to a .jar file
     * @param filepath
     */
    public Dependency parseJar(String filepath) throws ParseException {
        List<String> classes = getClasses(filepath);
        List<Package> packages = parseClassesList(classes);
        String[] jarDefinition = parseJarPath(filepath);
        ImmutableDependency dependency = ImmutableDependency.builder()
                .groupId(jarDefinition[0])
                .artifactId(jarDefinition[1])
                .version(jarDefinition[2])
                .packages(packages)
                .classPaths(classes)
                .build();


        return dependency;


    }

    private List<String> getClasses(String path){
        List<String> classNames = new ArrayList<String>();
        try {
            ZipInputStream zip = new ZipInputStream(new FileInputStream(path));
            for (ZipEntry entry = zip.getNextEntry(); entry != null; entry = zip.getNextEntry()) {
                if (!entry.isDirectory() && entry.getName().endsWith(".class")) {
                    // This ZipEntry represents a class. Now, what class does it represent?
                    String className = entry.getName().replace('/', '.'); // including ".class"
                    classNames.add(className.substring(0, className.length() - ".class".length()));
                }
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        return classNames;
    }

    private List<Package> parseClassesList(List<String> classes) {
        Map<String, List<ClassName>> map = new HashMap<String, List<ClassName>>();
        for (String aClass : classes) {
            String packagename = getPackageName(aClass);
            if (map.get(packagename) == null) {
                map.put(packagename, new ArrayList<ClassName>());
            }
            map.get(packagename).add(getClassName(aClass)); //adds value to list.
        }
        List<Package> packages = new ArrayList<>();
        for (String packagename : map.keySet()) {
            ImmutablePackage aPackage = ImmutablePackage.builder()
                    .packageName(packagename)
                    .classes(map.get(packagename))
                    .build();
            packages.add(aPackage);

        }

        return packages;
    }

    private Set<String> getPackageNames(List<String> classes) {
        HashSet<String> packageNames = new HashSet<String>();
        for (String aClass : classes) {
            packageNames.add(getPackageName(aClass));
        }
        return packageNames;

    }

    private String getPackageName(String classPath) {
        int i = StringUtils.lastIndexOf(classPath, '.');
        return StringUtils.substring(classPath, 0, i);
    }

    private ClassName getClassName(String classPath) {
        int i = StringUtils.lastIndexOf(classPath, '.');
        String classname = StringUtils.substring(classPath, i);
        classname = StringUtils.remove(classname,'.');
        return ImmutableClassName.builder().name(classname).build();
    }

    private String[] parseJarPath(String path) throws ParseException{
//               /Users/jvanheijst/.m2/repository/com/bol/axle/axle-spring-boot-starter-service/0.7.0/axle-spring-boot-starter-service-0.7.0.jar
        int i = StringUtils.indexOf(path,".m2");
        String jarPath =  StringUtils.substring(path,i);
        String[] split = StringUtils.split(jarPath, '/');
        String version = split[split.length-2];
        String artifactId = split[split.length-3];
        String groupId = "";
        for (int i1 = 2; i1 < split.length-3; i1++) {
            if(groupId.equals("")){
                groupId = split[i1];
            }else {
                groupId = groupId +"." +split[i1];
            }

        }
        String[] definition = new String[]{groupId,artifactId,version};
        return definition;
    }




}
