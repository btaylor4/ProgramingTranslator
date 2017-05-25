import java.util.HashMap;

/**
 * Created by bryant on 5/24/17.
 */
public class Variables {
    private HashMap<String, Integer> variables;
    private FileCreator currentFile;

    public Variables() {
        variables = new HashMap<>();
    }

    private boolean contains(String varName) {
        if (variables.containsKey(varName))
            return true;
        else
            return false;
    }

    public boolean add(String varName) {
        if (!contains(varName)) {
            variables.put(varName, 1);
            return true;
        } else
            return false;
    }

    public void addLinePlace(String varName, FileCreator file) {
        currentFile = file;
        variables.put(varName, file.currentPlace());
    }
}
