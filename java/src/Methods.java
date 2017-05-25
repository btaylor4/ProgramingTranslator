import java.io.File;
import java.util.ArrayList;

/**
 * Created by bryant on 5/24/17.
 */
public class Methods {
    private int linePlace;
    private String returnType;
    private String accessType;
    private String methodName;
    private ArrayList<String> parameters;

    public Methods()
    {
        linePlace = 0;
        returnType = "";
        parameters = new ArrayList<>();
    }

    public void linePlace(FileCreator file)
    {
        linePlace = file.methodPlace();
    }

    public void setReturnType(String returnType) { this.returnType = returnType; }

    public void setAccessType(String accessType) { this.accessType = accessType; }

    public void setParamaters(String parameters) {
        String temp = "";
        String addToList = "";
        ArrayList<String> type = new ArrayList<>();

        for(int i = 0; i < parameters.length(); i++)
        {
            if(parameters.charAt(i) == ' ')
            {
                if(type.size() < 2)
                {
                    temp = temp.trim();
                    type.add(temp);
                    temp = "";
                }
            }

            if(type.size() == 2)
            {
                addToList = type.get(0) + " " + type.get(1);
                this.parameters.add((addToList));
                type.clear();
                addToList = "";
            }

            temp += parameters.charAt(i);
        }

        if(!temp.isEmpty())
        {
           temp = temp.trim();
           type.add(temp);addToList = type.get(0) + " " + type.get(1);
           this.parameters.add((addToList));
        }
    }

    public void addToFile(FileCreator file)
    {
        String methodHeader = "\t" + accessType + " " + returnType + " " + methodName + "(" +
                returnParameters() + "){}";
        file.insertToBasic(linePlace, methodHeader);
    }

    private String returnParameters() {
        String temp = "";
        for (String str : parameters) {
            if(this.parameters.indexOf(str) == parameters.size() - 1)
                temp += str;
            else
                temp += str + ", ";
        }

        return temp;
    }

    public void setName(String name) {
        methodName = name;
    }
}
