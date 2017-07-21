import com.sun.tools.corba.se.idl.StringGen;
import model.ClassName;
import model.ImmutableClassName;
import model.ImmutablePackage;
import model.Package;
import org.apache.commons.lang3.StringUtils;

import java.util.*;


public class JarAnalyzer {

    /**
     * Gives a list of packages based on the filepath to a .jar file
     * @param filepath
     */
    public void AnalyseJar(String filepath) {
        JarParser jarParser = new JarParser();
        List<String> classes = jarParser.getClasses(filepath);
        List<Package> packages = parseClassesList(classes);
        for (Package aPackage : packages) {
            System.out.println(aPackage);
        }


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


}
