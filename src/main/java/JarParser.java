import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class JarParser {

    public List<String> getClasses(String path){
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

}
