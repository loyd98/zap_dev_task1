import java.util.*;

public class State3 extends State {

    @Override
    public ResultState process(String input, String command) {
        String[] commands = CommandList.getCommands();
        ResultState resultState = new ResultState();
        String[] entries = input.split(" ");
        String[] keys = CommandList.getKeys();
        String[] modifiedEntries = Arrays.copyOfRange(entries, 1, entries.length);
        int n = CommandList.validateKeywords(command, modifiedEntries);
        
        // to do
        // validate branches or merchant names with spaces
        if (Arrays.asList(keys).contains(entries[0].toUpperCase())) {
            for (int i = 0; i < keys.length; i++) {
                if(entries[0].toUpperCase().equals(keys[i])) {
                	entries[0] = commands[i];
                }
            }
            int m = CommandList.validateKeywords(entries[0], modifiedEntries);
            if (modifiedEntries.length == m) {
                Main.setData(modifiedEntries);
                resultState.setNextState(3);
                resultState.setCommand(input);
                resultState.setNextMessage("Confirming request to " + input + ".");
            } else if (modifiedEntries.length < m){
                resultState.setNextState(2);
                resultState.setCommand(entries[0]);
                resultState.setNextMessage("You lack a field kindly re-enter the requested information.");
            } else {
                resultState.setNextState(2);
                resultState.setCommand(entries[0]);
                resultState.setNextMessage("Invalid input. Please try again.");
            }
        }
        else if (Arrays.asList(commands).contains(input)) {
            resultState.setNextState(2);
            resultState.setCommand(input.toLowerCase());
            resultState.setNextMessage(CommandList.printCommandResponse(input));
        } else if (modifiedEntries.length == n) {
            Main.setData(modifiedEntries);
            resultState.setNextState(3);
            resultState.setCommand(command);
            resultState.setNextMessage("Confirming request to " + input + ".");
        } else if (modifiedEntries.length < n){
            resultState.setNextState(2);
            resultState.setCommand(command);
            resultState.setNextMessage("You lack a field kindly re-enter the requested information.");
        } else {
            resultState.setNextState(2);
            resultState.setCommand(command);
            resultState.setNextMessage("Invalid input. Please try again.");
        }

        return resultState;
    }

}
