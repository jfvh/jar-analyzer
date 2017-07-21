import org.apache.commons.lang3.StringUtils;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class JarAnalyzer {

    public void AnalyseJar(String filepath){
        JarParser jarParser = new JarParser();
        List<String> classes = jarParser.getClasses(filepath);
        Set<String> packageNames = getPackageNames(classes);
        for (String packageName : packageNames) {
            System.out.println(packageName);
        }

    }

    private Set<String> getPackageNames(List<String> classes){
        HashSet<String> packageNames = new HashSet<String>();
        for (String aClass : classes) {
            packageNames.add(getPackageName(aClass));
        }
        return packageNames;

    }

    private String getPackageName(String classPath){
        int i = StringUtils.lastIndexOf(classPath, '.');
        return StringUtils.substring(classPath,0,i);
    }


}
