import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by bryant on 5/23/17.
 */
public class Instruction {
    private String command;
    private ArrayList<String> instructions;
    private Map<String, Integer> instructionMap;
    private CommandTypes commandType;

    public Instruction()
    {
        command = "";
        instructions = new ArrayList<>();
        instructionMap = new HashMap<>();
        commandType = CommandTypes.NONE;
    }

    public void clear(){
        command = "";
        instructions.clear();
        instructionMap.clear();
        commandType = CommandTypes.NONE;
    }

    public String getVarName(){ return instructions.get(1); }
    public boolean contains(String keyword){
        if(command.contains(keyword))
            return true;
        else
            return false;
    }

    private void setCommandType(String keyword){
        switch(keyword.toLowerCase()) {
            case "class":
                commandType = CommandTypes.CLASS;
                break;

            case "method":
                commandType = CommandTypes.METHOD;
                break;

            case "switch":
            case "if":
                commandType = CommandTypes.CONDTIONAL;
                break;

            case "for":
            case "while":
            case "do while":
                commandType = CommandTypes.LOOP;
                break;

            default:
                commandType = CommandTypes.SIMPLE;
        }
    }

    public void addToInstructions() {
        String temp = "";
        int wordCount = 0;

        for(int i = 0; i < command.length(); i++) {
            if (command.charAt(i) == ' ' || command.charAt(i) == '-') {
                instructions.add(temp);
                instructionMap.put(temp, wordCount);
                temp = "";
                wordCount++;
                continue;
            }

            temp += command.charAt(i);
        }

        instructions.add(temp);
        instructionMap.put(temp, wordCount);
    }

    public void setCommand(String string) {
        setCommandType(string);
        this.command = string;
    }

    public void setInstructions(ArrayList<String> instructions){
        this.instructions = instructions;
    }

    public void setInstructionMap(HashMap<String, Integer> instructionMap){
        this.instructionMap = instructionMap;
    }

    public CommandTypes getCommandType() { return commandType; }
}
