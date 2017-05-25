package Synonyms;

import java.util.ArrayList;


public class Word
{
    private String word;
    private ArrayList<String> syns;

    public Word()
    {
        word = "";
        syns = new ArrayList<>();
    }

    public ArrayList<String> getSyns()
    {
        return syns;
    }

    public String getWord()
    {
        return word;
    }

    public void setWord(String word)
    {
        this.word = word;
    }

    public void addSyns (String word)
    {
        syns.add(word);
    }
}