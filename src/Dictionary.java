import java.util.ArrayList;


public class Dictionary
{
	ArrayList <Word> words = new ArrayList<Word>();
	
	public Dictionary()
	{
	
	}
	
	void addWord(Word word)
	{
		words.add(word);
	}
	
	boolean contains(String word)
	{
		ArrayList<String> check = new ArrayList<String>();
		
		for(int i = 0; i < words.size(); i++)
	    {
	        if(words.get(i).getWord() == word)
	        {
	            return true;
	        }
	        
	        else
	        {
	            check = words.get(i).getSyns();
	            
	            for(int j = 0; j < check.size(); j++)
	            {
	            	String s = check.get(j);
	                if(check.get(j).equalsIgnoreCase(word));
	                {
	                    return true;
	                }
	            }
	        }
	    }
		return false;
	}
	
	String getType(String word)
	{
		//try getting to n log n?
		ArrayList<String> check = new ArrayList<String>();
		
	    for(int i = 0; i < words.size(); i++)
	    {
	        if(words.get(i).getWord().equalsIgnoreCase(word))
	        {
	            return words.get(i).getWord();
	        }
	        
	        else
	        {
	            check = words.get(i).getSyns();
	            
	            for(int j = 0; j < check.size(); j++)
	            {
	                if(check.get(j).equalsIgnoreCase(word))
	                {
	                    return words.get(i).getWord();
	                }
	            }
	        }
	    }
	    
	    return "";
	}
}
