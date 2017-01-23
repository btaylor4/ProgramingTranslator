import java.util.ArrayList;


public class Word 
{
	String word;
    ArrayList<String> syns = new ArrayList<String>();
    
	public Word()
	{
		
	}

    ArrayList<String> getSyns()
    {
    	return syns;
    }
    
    String getWord()
    {
    	return word;
    }
    
    void setWord(String word)
    {
    	this.word = word;
    }
    
    void addSyns (String word)
    {
    	syns.add(word);
    }
}
