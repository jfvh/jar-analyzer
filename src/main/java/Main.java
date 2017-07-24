import model.Dependency;

import java.text.ParseException;

public class Main {

    //just for testing
    public static void main(String[] args) {
        Main instance = new Main();
        instance.doStuff();
    }

    public void doStuff(){
        JarParser jarParser =  new JarParser();
        try {
            Dependency dependency = jarParser.parseJar("/Users/jvanheijst/.m2/repository/commons-io/commons-io/2.5/commons-io-2.5.jar");
            System.out.println(dependency);

        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
