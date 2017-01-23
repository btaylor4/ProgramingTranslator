import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;


public class General 
{
	ArrayList<String> basic = new ArrayList<String>();
    int linePlace = 0;
    
    public General()
    {
    	basic.add("#include<iostream>");
        linePlace++;
        
        basic.add("using namespace std;");
        linePlace++;
        
        basic.add("int main()");
        linePlace++;
        
        basic.add("{");
        linePlace++;
        
        basic.add("\treturn 0;");
        basic.add("}");
    }
    
    public void writeToFile(String name)
    {
    	try
    	{
        	File file = new File(name+".txt");
    		file.createNewFile();
    		
    		FileWriter fw = new FileWriter(file.getAbsoluteFile());
    		BufferedWriter bw = new BufferedWriter(fw);
    		
            for(int i = 0; i < basic.size(); i++)
            {
                bw.write(basic.get(i));
                bw.write("\n");
            }
            
            bw.close();
    	}
    	
    	catch(Exception e)
    	{
    		
    	}
    }
    
    public void addToBasic(String line)
    {
    	//basic.insert(basic.begin()+linePlace, line);
    	basic.add(linePlace, line);
    	linePlace++;
    }
}
