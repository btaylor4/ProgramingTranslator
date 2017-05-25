import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;

/**
 * Created by bryant on 5/23/17.
 */
public class FileCreator {
    private ArrayList<String> className;
    private ArrayList<String> basic = new ArrayList<String>();
    private int linePlace = 0;

    public FileCreator(String className)
    {
        basic.add("public class " + className + " {");
        linePlace++;

        basic.add("\tpublic static void main(String[] args)");
        linePlace++;

        basic.add("\t{");
        linePlace++;

        basic.add("\t}");
        basic.add("}");
    }

    public FileCreator()
    {
        basic.add("public class " + className + " {");
        linePlace++;

        basic.add("\tpublic static void main(String[] args)");
        linePlace++;

        basic.add("\t{");
        linePlace++;

        basic.add("\t}");
        basic.add("}");
    }

    public void writeToFile(String name) {
        try
        {
            File file = new File(name + ".txt");
            file.createNewFile();

            FileWriter fw = new FileWriter(file.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fw);

            for (int i = 0; i < basic.size(); i++)
            {
                bw.write(basic.get(i));
                bw.write("\n");
            }

            bw.close();
        }

        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public void addToBasic(String line)
    {
        //basic.insert(basic.begin()+linePlace, line);
        basic.add(linePlace, line);
        linePlace++;
    }

    public void insertToBasic(int selectStart, String line) {
        basic.add(selectStart, line);
    }

    public int currentPlace(){ return linePlace; }

    public int methodPlace(){ return basic.size() - 1; }
}
