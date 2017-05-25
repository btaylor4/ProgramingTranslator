import Synonyms.Dictionary;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by bryant on 5/23/17.
 */
public class DictionaryTests
{
    private Dictionary dictionary;

    @Before
    public void setUp() { dictionary = new Dictionary(); }

    @Test
    public void testWordGeneration(){
        assertEquals("int", dictionary.getWords().get(0).getWord());
    }
}
