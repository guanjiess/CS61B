package gitlet;

import java.util.HashSet;
import java.util.Set;
import java.io.File;
import static gitlet.Utils.*;

public class CommandChecker {
    public CommandChecker(){}
    /** commandChecker checks if the command exists
     *  validateNumArgs checks the number of args for the command
     * */
    public static boolean commandExists(String [] args){
        String [] commands = {"init", "add", "commit", "rm", "log",
                "global-log", "find", "status", "checkout",
                "branch", "rm-branch", "reset", "merge"};
        Set<String> commandSet = new HashSet<>();
        for(String command : commands){
            commandSet.add(command);
        }
        if(args.length == 0){
            System.out.println("Please enter a command.");
            return false;
        }

        if(commandSet.contains(args[0])){
            return true;
        }
        else {
            System.out.println("No command with that name exists.");
        }
        return true;
    }
    public static boolean validateNumArgs(String cmd, String[] args, int n) {
        try {
            if (args.length != n) {
                throw new RuntimeException("Incorrect operands.");
            }
        }
        catch (RuntimeException exp){
            System.out.println("Incorrect operands.");
            return false;
        }
        return true;
    }

    public static boolean commitValid(String[] args){
        try {
            if (args.length != 2) {
                throw new RuntimeException("Please enter a commit message.");
            }
        }
        catch (RuntimeException exp){
            System.out.println("Please enter a commit message.");
            return false;
        }
        File index = join(Repository.GITLET_DIR, "index");
        String stagingArea = readContentsAsString(index);
        try {
            if(stagingArea.length() == 0){
                throw new GitletException("No changes added to the commit.");
            }
        }
        catch (GitletException exception){
            System.out.println("No changes added to the commit.");
            return false;
        }
        return true;
    }
}
