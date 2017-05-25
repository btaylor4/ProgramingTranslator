/**
 * Created by bryant on 5/24/17.
 */
public class API_Handler
{
    public static String buildJSON(String s) {
        String json = "{\"config\": {\"encoding\": \"FLAC\", \"sampleRate\": 16000},\"audio\":{\"content\":\"" + s +"\"}}";
        return json;
    }
}
