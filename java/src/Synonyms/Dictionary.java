package Synonyms;

import Synonyms.Word;
import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.FileReader;

public class Dictionary
{
    private ArrayList <Word> words;

    public Dictionary()
    {
        words = new ArrayList<>();
        generateWords();
    }

    public boolean contains(String word)
    {
        ArrayList<String> check;

        for (int i = 0; i < words.size(); i++)
        {
            if (words.get(i).getWord() == word)
            {
                return true;
            }

            else
            {
                check = words.get(i).getSyns();

                for (int j = 0; j < check.size(); j++)
                {
                    if (check.get(j).equalsIgnoreCase(word))
                    {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public String getType(String word)
    {
        for (Word keyword : words)
        {
            if(word.contains(keyword.getWord()))
                return keyword.getWord();

            else
            {
                for (String str : keyword.getSyns())
                {
                    if (word.contains(str))
                    {
                        return keyword.getWord();
                    }
                }
            }
        }

//        //try getting to n log n?
//        ArrayList<String> check;
//
//        for (int i = 0; i < words.size(); i++)
//        {
//            if (words.get(i).getWord().equalsIgnoreCase(word))
//            {
//                return words.get(i).getWord();
//            }
//
//            else
//            {
//                check = words.get(i).getSyns();
//
//                for (int j = 0; j < check.size(); j++)
//                {
//                    if (check.get(j).equalsIgnoreCase(word))
//                    {
//                        return words.get(i).getWord();
//                    }
//                }
//            }
//        }

        return "";
    }

    private void generateWords()
    {
        String temp = "";
        BufferedReader br;
        FileReader fr;
        Word word = new Word();

        try
        {
            fr = new FileReader("/Users/bryant/Desktop/src/Dictionary.txt");
            br = new BufferedReader(fr);

            String words;

            br = new BufferedReader(new FileReader("/Users/bryant/Desktop/src/Dictionary.txt"));

            while((words = br.readLine()) != null)
            {
                for(int i = 0; i < words.length(); i++)
                {
                    if(words.charAt(i) == ' ' || words.charAt(i) == '\n')
                    {
                        if(word.getWord().equals(""))
                            word.setWord(temp);
                        else
                            word.addSyns(temp);

                        temp = "";
                        continue;
                    }

                    temp += words.charAt(i);
                }

                this.words.add(word);
                word = new Word();
            }
        }

        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public ArrayList<Word> getWords(){return words;}
}