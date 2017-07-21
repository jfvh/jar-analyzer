public class Main {

    public static void main(String[] args) {
        Main instance = new Main();
        instance.doStuff();
    }

    public void doStuff(){
        JarAnalyzer jarAnalyzer =  new JarAnalyzer();
        jarAnalyzer.AnalyseJar("/Users/jvanheijst/.m2/repository/commons-io/commons-io/2.5/commons-io-2.5.jar");
    }
}
