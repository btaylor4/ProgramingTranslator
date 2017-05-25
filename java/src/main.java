import Synonyms.Dictionary;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

/**
 * Created by bryant on 5/23/17.
 */
public class main {
    public static void main(String[] args)
    {
        AudioRecorder recorder = new AudioRecorder();

        String s = recorder.decodeSpeech();
        System.out.println(s);
        System.exit(0);

        Scanner console = new Scanner(System.in);
        String speech;
        String projectName;
        Instruction instruction = new Instruction();
        FileCreator newProject;
        Dictionary types = new Dictionary();
        Variables variables = new Variables();
        Methods method;

        try
        {
            System.out.println("What's the name of your program? ");

            //Create File
            projectName = console.nextLine();
            newProject = new FileCreator(projectName);
            newProject.writeToFile(projectName);

            //Get new Input
            System.out.println("Command: ");
            speech = console.nextLine();
            speech  = speech.toLowerCase();

            while(!speech.equals("quit"))
            {
                System.out.print("Command: ");
                speech = console.nextLine();

                if(speech.equalsIgnoreCase("quit"))
                {
                    System.exit(0);
                }

                instruction.setCommand(speech);
                instruction.addToInstructions();
                instruction.setCommand(speech);

                switch(instruction.getCommandType())
                {
                    case CLASS:
                        break;

                    case METHOD:
                        method = new Methods();
                        method.linePlace(newProject);

                        System.out.println("Enter return type: ");
                        speech = console.nextLine();
                        method.setReturnType(speech);

                        System.out.println("Enter access type: ");
                        speech = console.nextLine();
                        method.setAccessType(speech);

                        System.out.println("Enter parameters");
                        speech = console.nextLine();
                        method.setParamaters(speech);

                        System.out.println("Enter method name: ");
                        speech = console.nextLine();
                        method.setName(speech);

                        method.addToFile(newProject);
                        newProject.writeToFile(projectName);

                        break;

                    case LOOP:
                        break;

                    case CONDTIONAL:
                        break;

                    case SIMPLE:
                        if(variables.add(instruction.getVarName())) {
                            String code = "\t\t" + types.getType(speech) + " " + instruction.getVarName() + ";";
                            variables.addLinePlace(instruction.getVarName(), newProject);
                            newProject.addToBasic(code);
                            newProject.writeToFile(projectName);
                        }

                        else
                            System.out.println("Variable name already exists");
                        break;

                    case NONE:
                        break;
                }

                instruction.clear();
            }
        }

        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
}
