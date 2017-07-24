package checks;

import java.util.List;

public interface Check {

    String checkMessage();

    List<String> checkOutput();
}
