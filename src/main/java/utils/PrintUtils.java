package utils;

import model.Dependency;
import org.apache.commons.lang3.StringUtils;

public class PrintUtils {

    public static String getPrettyDependencyString(Dependency dependency) {
        StringBuilder stringBuilder = new StringBuilder();
        return stringBuilder.append("Dependency:{").append("")
                .append(valueOrEmptyIfNull(dependency.groupId())).append(" - ")
                .append(valueOrEmptyIfNull(dependency.artifactId())).append(" - ")
                .append(valueOrEmptyIfNull(dependency.version())).toString();
    }

    public static String valueOrEmptyIfNull(String input) {
        if (input == null) {
            return "";
        }
        return input;
    }

    public static String getPackageName(String classpath) {
        int lastIndexOf = StringUtils.lastIndexOf(classpath, '.');
        return StringUtils.substring(classpath,0, lastIndexOf);
    }
}
