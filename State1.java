import java.util.*;

public class State1 extends StateS {

    @Override
    public ResultState process(String input, String command) {
        ArrayList<String> commands = CommandList.getCommands();
        ArrayList<String> keys = CommandList.getKeys();
        String[] entries = input.split(" ");
        String[] modifiedEntries = Arrays.copyOfRange(entries, 1, entries.length);
        ResultState resultState = new ResultState();
        
        String list = "List of Request Commands: ";
        for (String str : commands) {
            list = list + "\n" + str;
        }

        resultState.setNextMessage(list);
        resultState.setCommand("");
        resultState.setNextState(0);
	    
	    if (keys.contains(entries[0].toUpperCase())) {
        	for (int i = 0; i < keys.size(); i++) {
    			if(entries[0].toUpperCase().equals(keys.get(i))) {
    				entries[0] = commands.get(i);
    			}
    		}
        	int m = CommandList.validateKeywords(entries[0], modifiedEntries);
        	if (modifiedEntries.length == m || m == 123456) {
                ChatServer.setData(modifiedEntries);
                resultState.setNextState(3);
                resultState.setCommand(entries[0]);
                resultState.setNextMessage("Confirming request to " + input + ".");
            } else if (modifiedEntries.length < m){
                resultState.setNextState(0);
                resultState.setCommand(entries[0]);
                resultState.setNextMessage("You lack a field kindly re-enter the requested information.");
            } else {
                resultState.setNextState(0);
                resultState.setCommand(entries[0]);
                resultState.setNextMessage("Invalid input. Please try again.");
            }
        }
        else if (commands.contains(input.toLowerCase())) {
            resultState.setNextState(1);
            resultState.setCommand(input.toLowerCase());
            resultState.setNextMessage("I would like to confirm if you wish to " + input.toLowerCase() + ".");
        }

        return resultState;
    }


}
