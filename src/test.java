import java.io.BufferedReader; 
import java.nio.*;
import java.io.DataOutputStream; 
import java.io.InputStreamReader; 
import java.net.HttpURLConnection; 
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import src.main.java.org.apache.commons.codec.binary.*;
import src.main.java.org.apache.commons.io.*;
import javax.sound.sampled.*;
import java.io.*;
import com.oschrenk.io.*;
import org.apache.commons.codec.binary.Base64;
import javaFlacEncoder.FLAC_FileEncoder;

public class test 
{
	static final long RECORD_TIME = 6500;  // 1 minute	
	 
    // path of the wav file
    static File wavFile = new File("RecordAudio.wav");
 
    // format of audio file
    AudioFileFormat.Type fileType = AudioFileFormat.Type.WAVE;
 
    // the line from which audio data is captured
    TargetDataLine line;
 
    /**
     * Defines an audio format
     */
    AudioFormat getAudioFormat() 
    {
        float sampleRate = 16000;
        int sampleSizeInBits = 8;
        int channels = 1;
        boolean signed = true;
        boolean bigEndian = true;
        AudioFormat format = new AudioFormat(sampleRate, sampleSizeInBits,
                                             channels, signed, bigEndian);
        return format;
    }
 
    /**
     * Captures the sound and record into a WAV file
     */
    void start() 
    {
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
            line.start();   // start capturing
 
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
    void finish() 
    {
        line.stop();
        line.close();
        System.out.println("Finished");
    }
    
	public static void main(String[] args)
	{
		try
		{
			holder();
		}
		
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public static String buildJSON(String s)
	{
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
	    	//add later
	    }

	    return bytesArray;
	}
	
	public static String testM() throws Exception
	{
		//records and processes your input
		final test recorder = new test();
		 
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
        
        byte[] encodedBytes = Base64.encodeBase64(fileToBytes(outputFile));
        
        String byteString = new String(encodedBytes);
        String url = "https://speech.googleapis.com/v1beta1/speech:syncrecognize?key=AIzaSyDDNIg18IbR6SzzkHMUqnk9Lxsxgso5rjs"; //https:postman-echo.com/post";
		
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
	
	public static void holder()
	{
		Map<String, Integer> instCheck = new HashMap<String, Integer>();
		General startup = new General();
		Dictionary c1 = new Dictionary();
		Scanner console = new Scanner(System.in);
		
		String speech = "";
		String projectName = "";
		String temp = "";
		ArrayList <String> insts = new ArrayList<String>();
		String num = "";
		
		String single[] = {"zero", "one", "two", "three", "four", "five", "six", "seven", "eight", "nine"};
	    String doub[] = {"ten", "eleven", "tweleve", "thirteen", "fourteen", "fifteen", "sixteen", "seventeen", "eighteen", "nineteen"};
	    String tens[] = {"twenty", "thirty", "fourty", "fifty", "sixty", "seventy", "eighty", "ninety"};
	    String ending[] = { "hundred", "thousand", "million", "billion"};
		Map<String, Integer> nums = new HashMap<String, Integer>();

		for(int i = 0; i < 10; i++)
	    {
	        nums.put(single[i], i);
	    }
	    
	    for(int i = 0; i < 10; i++)
	    {
	        nums.put(doub[i], i + 10);
	    }
	    
	    for(int i = 0; i < 8; i++)
	    {
	        nums.put(tens[i], (i+1) * 10 + 10);
	    }
	    
	    for(int i = 0; i < 4; i++)
	    {
	        nums.put(ending[i], (int)Math.pow(10, (i+1)) * 10);
	    }
	    
		Word word = new Word();
	    word.setWord("int");
	    word.addSyns("integer");
	    word.addSyns("integers");
	    
	    Word s = new Word();
	    s.setWord("string");
	    s.addSyns("strings");
	    
	    Word c = new Word();
	    c.setWord("char");
	    c.addSyns("chars");
	    c.addSyns("character");
	    c.addSyns("characters");
	    
	    Word d = new Word();
	    d.setWord("double");
	    d.addSyns("doubles");
	    
	    Word f = new Word();
	    f.setWord("float");
	    f.addSyns("floats");
	    
	    Word v = new Word();
	    v.setWord("void");
	    v.addSyns("voids");
	    c1.addWord(word);
	    c1.addWord(d);
	    c1.addWord(f);
	    c1.addWord(v);
	    c1.addWord(s);
	    c1.addWord(c);
	    
	    Word a = new Word();
	    a.setWord("array");
	    a.addSyns("arrays");
	    
	    Word ve = new Word();
	    ve.setWord("vector");
	    ve.addSyns("vectors");
	    c1.addWord(a);
	    c1.addWord(ve);
	    
	    //category 1 : every simple type
	    //category 2 : 
		
		int eles = 0;
	    int iters = 0;
	    int wordCount = 0;
	    int var = 0;
	    int find = 0;
	    int init = 0;
	    String tab = "     ";
		
		try
		{
			System.out.println("What would you like to do?");
			System.out.println("1. Create a New Project");
			System.out.println("2. Open an Existing Project");
			
			//speech = testM();
			speech = console.nextLine();
			
			if(speech.contains("new project") || speech.contains("project"))
			{
				//for implementing in different languages
				/*
					System.out.println("1. Java Project?");
					System.out.println("2. C++?");
					speech = testM();
				*/
				
				System.out.println("What will your program be called?");
				//projectName = testM();
				projectName = "demotest";
				
				startup.writeToFile(projectName);
				
				while(!speech.equalsIgnoreCase("quit") || !speech.equalsIgnoreCase("quick"))
				{
					System.out.println("Command:");
					speech = console.nextLine();
					
					if(speech.equalsIgnoreCase("quit") || speech.equalsIgnoreCase("quick"))
					{
						System.exit(0);
					}

					for(int i = 0; i < speech.length(); i++) //Processing every word of instruction
	                {
	                    if(speech.charAt(i) == ' '  || speech.charAt(i) == '-')
	                    {
	                        insts.add(temp);
	                        instCheck.put(temp, wordCount);
	                        temp = "";
	                        wordCount++;
	                        continue;
	                    }
	                    
	                    temp += speech.charAt(i);
	                }
	                
	                //needs to be done one last time
	                wordCount++;
	                insts.add(temp);
	                instCheck.put(temp, wordCount);
	                temp = "";
	                
	                if(instCheck.containsKey("int"))
	                {
	                	var = instCheck.get("int") + 1;
	                	
	                	if(instCheck.containsKey("equals"))
	                	{
	                		init = instCheck.get("equals") + 1;
	                		String addition = tab + c1.getType(insts.get(var - 1)) + " " + insts.get(var) + " = " + nums.get(insts.get(init)) + ";";
	                		startup.addToBasic(addition);
	                		startup.writeToFile(projectName);
	                	}
	                	
	                	else
	                	{
	                		String addition = tab + c1.getType(insts.get(var - 1)) + " " + insts.get(var) + ";";
	                		startup.addToBasic(addition);
	                		startup.writeToFile(projectName);
	                	}
	                }
	                
	                else if(instCheck.containsKey("float"))
	                {
	                	var = instCheck.get("float") + 1;
	                	
	                	if(instCheck.containsKey("equals"))
	                	{
	                		init = instCheck.get("equals") + 1;
	                		String addition = tab + c1.getType(insts.get(var - 1)) + " " + insts.get(var) + " = " + insts.get(init) +";";
	                		startup.addToBasic(addition);
	                		startup.writeToFile(projectName);
	                	}
	                	
	                	else
	                	{
	                		String addition = tab + c1.getType(insts.get(var - 1)) + " " + insts.get(var) + ";";
	                		startup.addToBasic(addition);
	                		startup.writeToFile(projectName);
	                	}
	                }
	                
	                else if(instCheck.containsKey("double"))
	                {
	                	var = instCheck.get("double") + 1;
	                	
	                	if(instCheck.containsKey("equals"))
	                	{
	                		init = instCheck.get("equals") + 1;
	                		String addition = tab + c1.getType(insts.get(var - 1)) + " " + insts.get(var) + " = " + insts.get(init) +";";
	                		startup.addToBasic(addition);
	                		startup.writeToFile(projectName);
	                	}
	                	
	                	else
	                	{
	                		String addition = tab + c1.getType(insts.get(var - 1)) + " " + insts.get(var) + ";";
	                		startup.addToBasic(addition);
	                		startup.writeToFile(projectName);
	                	}
	                }
	                
	                else if(instCheck.containsKey("char"))
	                {
	                	var = instCheck.get("char") + 1;
	                	
	                	if(instCheck.containsKey("equals"))
	                	{
	                		init = instCheck.get("equals") + 1;
	                		String addition = tab + c1.getType(insts.get(var - 1)) + " " + insts.get(var) + " = " + "'" + insts.get(init) + "';";
	                		startup.addToBasic(addition);
	                		startup.writeToFile(projectName);
	                	}
	                	
	                	else
	                	{
	                		String addition = tab + c1.getType(insts.get(var - 1)) + " " + insts.get(var) + ";";
	                		startup.addToBasic(addition);
	                		startup.writeToFile(projectName);
	                	}
	                }
	                
	                else if(instCheck.containsKey("string"))
	                {
	                	var = instCheck.get("string") + 1;
	                	
	                	if(instCheck.containsKey("equals"))
	                	{
	                		init = instCheck.get("equals") + 1;
	                		String addition = tab + c1.getType(insts.get(var - 1)) + " " + insts.get(var) + " = " + "\"" + insts.get(init) + "\";";
	                		startup.addToBasic(addition);
	                		startup.writeToFile(projectName);
	                	}
	                	
	                	else
	                	{
	                		String addition = tab + c1.getType(insts.get(var - 1)) + " " + insts.get(var) + ";";
	                		startup.addToBasic(addition);
	                		startup.writeToFile(projectName);
	                	}
	                }
	                
	                else if(instCheck.containsKey("if"))
	                {	                	
                		String begin = "";
	                	String fP = tab +"{\n";
	                	String sP = tab +"}\n";
	                	
	                	speech = console.nextLine();
	                	
	                	/*        
	                	 * next line will not work correctly if they have a method named equals or 
	                	 * a string variable initialized to equals or string called equals
	                	 */
	                	                	
	                	while(!speech.equalsIgnoreCase("end if") && !speech.equalsIgnoreCase("end statement"))
	                	{
	                		if(speech.contains("equals"))
		                	{	
	                			String setVarTo = speech.substring(speech.indexOf("equals") + 7, speech.indexOf("then") - 1);
		                		speech = speech.replaceAll("equals", "=="); 
		                		begin = tab + "if("+ speech.substring(3,speech.indexOf("==") + 3) + nums.get(setVarTo) + ")";
		                		startup.addToBasic(begin);
			                	startup.addToBasic(fP);
			                	startup.writeToFile(projectName);
			                	
			                	if(speech.contains("then"))
			                	{
			                		setVarTo = speech.substring(speech.indexOf("==", speech.indexOf("==") + 3) + 3, speech.length());
			                		speech = speech.replaceAll("==", "="); 
			                		String statement = tab + tab + speech.substring(speech.indexOf("then") + 4, speech.indexOf(setVarTo)) + nums.get(setVarTo)+ ";";
			                		startup.addToBasic(statement);
				                	startup.writeToFile(projectName);
			                	}
		                	}
		                	
		                	else if(speech.contains("less than"))
		                	{	
		                		speech = speech.replaceAll("less than", "<"); 
		                		begin = tab + "if("+ speech.substring(3,speech.indexOf("then")) + ")";
		                		startup.addToBasic(begin);
			                	startup.addToBasic(fP);
			                	startup.writeToFile(projectName);
		                	}
		                	
		                	else if(speech.contains("greater than"))
		                	{	
		                		speech = speech.replaceAll("greater than", ">"); 
		                		begin = tab + "if("+ speech.substring(3,speech.indexOf("then")) + ")";
		                		startup.addToBasic(begin);
			                	startup.addToBasic(fP);
			                	startup.writeToFile(projectName);
		                	}
	                		
	                		speech = console.nextLine();
	                	}
	                	
	                	if(speech.equalsIgnoreCase("end if") || speech.equalsIgnoreCase("end statement"))
	                	{
	                		startup.addToBasic(sP);
	                		startup.writeToFile(projectName);
	                	}
	                }
	                
	                else if(instCheck.containsKey("while loop"))
	                {
	                	
	                }
	                
	                
	                else if(instCheck.containsKey("for loop"))
	                {
	                	
	                }
	                
	                
	                else if(instCheck.containsKey("do while loop"))
	                {
	                	
	                }
	                
	                /*for(int i = 1; i < insts.size(); i++) //check each of those words to figure what to do
                    {
                        if(c1.contains(insts.get(i))) //check if in cat1 of words
                        {
                            if(instCheck.containsKey("array")) // are we making an array
                            {
                                //initializing arrays are harder
                                
                                if(instCheck.containsKey("initialize")) //if user wants var initialized
                                {
                                    if(instCheck.containsKey("called")) //what is the array called?
                                    {
                                        var = instCheck.containsKey("called");
                                        find = instCheck.containsKey("initialize"];
                                        String addition = tab + c2.getType(insts[var - 1]) + " " + insts[var + 1] + " = " + insts[find + 3] + ";";
                                        startup.addToBasic(addition);
                                        startup.writeToFile(name);
                                    }
                                    
                                    else //if called was not stated, var name is not explicit, make random generated name??
                                    {
                                        cout << "No variable name specified" << endl;
                                    }
                                }
                                
                                else 
                            	
                                if(instCheck.containsKey("called") || instCheck.containsKey("call")) // chose not to initialize
                                {
                                    if(instCheck.containsKey("with"))
                                    {
                                        eles = instCheck.get("with");
                                        
                                        try
                                        {
                                        	var = instCheck.get("called");
                                        }
                                        
                                        catch(Exception e)
                                        {
                                        	var = instCheck.get("call");
                                        }                                   

                                        var = instCheck.get("called");
                                        String addition = tab + c1.getType(insts.get(i)) + " " + insts.get(var + 1) + "[" + nums.get(insts.get(eles + 1)) + "];";
                                        startup.addToBasic(addition);
                                        startup.writeToFile(projectName);
                                    }
                                }
                                
                                else
                                {
                                	System.out.println("No variable name specified");
                                }
                            }
                            
                            else if(instCheck.containsKey("initialize")) //if user wants var initialized
                            {
                                if(instCheck.containsKey("called") || instCheck.containsKey("call"))
                                {
                                	try
                                    {
                                    	var = instCheck.get("called");
                                    }
                                    
                                    catch(Exception e)
                                    {
                                    	var = instCheck.get("call");
                                    }
                                	
                                    find = instCheck.get("initialize");
                                    if(nums.containsKey(insts.get(find + 3)))
                                    {
                                    	num = nums.get(insts.get(find + 3)) + "";
                                    }
                                    
                                    else
                                    	num = insts.get(find +3);
                                    
                                    String addition = tab + c1.getType(insts.get(var - 1)) + " " + insts.get(var + 1) + " = " + num + ";";
                                    startup.addToBasic(addition);
                                    startup.writeToFile(projectName);
                                }
                                
                                else
                                {
                                	System.out.println("No variable name specified");
                                }
                            }
                            
                            else if(instCheck.containsKey("called") || instCheck.containsKey("call"))
                            {
                                var = instCheck.get("called");
                                String addition = tab +c1.getType(insts.get(var - 1)) + " " + insts.get(var + 1) + ";";
                                startup.addToBasic(addition);
                                startup.writeToFile(projectName);
                            }
                            
                            else
                            {
                            	System.out.println("No variable name specified");
                            }
                            
                            break;
                        }
                    }*/
	                
	                wordCount = 0;
	                instCheck.clear();
	                insts.clear();
	                speech = "";
				}
			}
		}
		
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}

//Get requests!
/*String name = "";
for(int i = 0; i < response.length(); i++)
{
	if(response.charAt(i) >= 48 && response.charAt(i) <= 57)
	{
		name = name + response.charAt(i);
	}
}

System.out.println(name);

url = "https://speech.googleapis.com/v1beta1/operations/" + name +"?key=AIzaSyDDNIg18IbR6SzzkHMUqnk9Lxsxgso5rjs";
obj = new URL(url);
con = (HttpURLConnection) obj.openConnection(); // Setting basic post request '
con.setRequestMethod("GET");  
responseCode = con.getResponseCode(); 

System.out.println("\nSending 'GET' request to URL : " + url);  
System.out.println("Response Code : " + responseCode); 
in = new BufferedReader( new InputStreamReader(con.getInputStream())); 
response = new StringBuffer(); 
while ((output = in.readLine()) != null)
{ 
	response.append(output); 
} 

in.close(); //printing result from response 
System.out.println(response.toString());*/