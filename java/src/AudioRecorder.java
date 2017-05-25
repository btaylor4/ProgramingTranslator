import javax.sound.sampled.*;
import java.io.*;
import java.util.Base64;
import javaFlacEncoder.FLAC_FileEncoder;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by bryant on 5/24/17.
 */
public class AudioRecorder {
    // record duration, in milliseconds
    private static final long RECORD_TIME = 3000;	// 3 seconds

    // path of the wav file
    private static File wavFile = new File("audio.wav");

    // format of audio file
    private AudioFileFormat.Type fileType = AudioFileFormat.Type.WAVE;

    // the line from which audio data is captured
    private TargetDataLine line;

    /**
     * Defines an audio format
     */
    public AudioFormat getAudioFormat() {
        float sampleRate = 16000;
        int sampleSizeInBits = 8;
        int channels = 2;
        boolean signed = true;
        boolean bigEndian = true;
        AudioFormat format = new AudioFormat(sampleRate, sampleSizeInBits,
                channels, signed, bigEndian);
        return format;
    }

    /**
     * Captures the sound and record into a WAV file
     */
    void start() {
        try {
            AudioFormat format = getAudioFormat();
            DataLine.Info info = new DataLine.Info(TargetDataLine.class, format);

            // checks if system supports the data line
            if (!AudioSystem.isLineSupported(info)) {
                System.out.println("Line not supported");
                System.exit(0);
            }
            line = (TargetDataLine) AudioSystem.getLine(info);
            line.open(format);
            line.start();	// start capturing

            System.out.println("Start capturing...");

            AudioInputStream ais = new AudioInputStream(line);

            System.out.println("Start recording...");

            // start recording
            AudioSystem.write(ais, fileType, wavFile);

        } catch (LineUnavailableException ex) {
            ex.printStackTrace();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    /**
     * Closes the target data line to finish capturing and recording
     */
    void finish() {
        line.stop();
        line.close();
        System.out.println("Finished");
    }

    public String decodeSpeech()
    {
        final AudioRecorder recorder = new AudioRecorder();

        // creates a new thread that waits for a specified
        // of time before stopping
        Thread stopper = new Thread(new Runnable() {
            public void run() {
                try {
                    Thread.sleep(RECORD_TIME);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
                recorder.finish();
            }
        });

        stopper.start();

        // start recording
        recorder.start();

        FLAC_FileEncoder flacEncoder = new FLAC_FileEncoder();
        File outputFile = new File("test.flac");

        flacEncoder.encode(wavFile, outputFile);
        byte[] encodedBytes = Base64.getEncoder().encode(fileToBytes(outputFile));

        String byteString = new String(encodedBytes);
        String url = "https://speech.googleapis.com/v1beta1/speech:syncrecognize?key=AIzaSyA6nSNs67cmJkuNFk3ixj2FQ1T5ShbjnlU"; //https:postman-echo.com/post";

        try
        {
            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection(); // Setting basic post request '
            con.setRequestMethod("POST");
            con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
            con.setRequestProperty("Content-Type","application/json");

            String postJsonData = buildJSON(byteString); // Send post request
            con.setDoOutput(true);
            DataOutputStream wr = new DataOutputStream(con.getOutputStream());
            wr.writeBytes(postJsonData); wr.flush(); wr.close();
            int responseCode = con.getResponseCode();
            System.out.println("\nSending 'POST' request to URL : " + url);
            System.out.println("Post Data : " + postJsonData);
            System.out.println("Response Code : " + responseCode);
            BufferedReader in = new BufferedReader( new InputStreamReader(con.getInputStream()));
            String output;
            StringBuffer response = new StringBuffer();
            while ((output = in.readLine()) != null)
            {
                response.append(output);
            }

            in.close(); //printing result from response
            String input = "";
            if(response.indexOf("transcript\":") != -1)
            {
                System.out.println(response.toString());

                for(int i = response.indexOf("transcript\":") + 14; response.charAt(i) != '"'; i++)
                {
                    input += response.charAt(i);
                }

            }

            return input;
        }

        catch(Exception e)
        {
            e.printStackTrace();
        }

        return "";
    }

    public static String buildJSON(String s) {
        String json = "{\"config\": {\"encoding\": \"FLAC\", \"sampleRate\": 16000},\"audio\":{\"content\":\"" + s +"\"}}";
        return json;
    }

    public static byte[] fileToBytes(File file)
    {
        FileInputStream fis = null;
        //init array with file length
        byte[] bytesArray = new byte[(int) file.length()];

        try
        {
            fis = new FileInputStream(file);
            fis.read(bytesArray); //read file into bytes[]
            fis.close();
        }

        catch(Exception e)
        {
            e.printStackTrace();
        }

        return bytesArray;
    }
}
