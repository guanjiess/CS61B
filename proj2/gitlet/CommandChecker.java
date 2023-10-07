package gitlet;

import java.util.HashMap;
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
        try {
            if(!index.exists()){
                throw new GitletException("No changes added to the commit.");
            }
        }
        catch (GitletException exception){
            System.out.println("No changes added to the commit.");
            return false;
        }
        return true;
    }
    public static boolean folderCheck(){
        try {
            if(!Repository.GITLET_DIR.exists()) throw new GitletException("Not in an initialized Gitlet directory.");
        }
        catch (GitletException exp){
            System.out.println("Not in an initialized Gitlet directory.");
            return false;
        }
        return true;
    }

    public static boolean rmFileCheck(HashMap<String ,String > stageAdd, HashMap<String ,String > tree, String name){
        try {
            boolean containsFile = stageAdd.containsKey(name)||tree.containsKey(name);
            if(!containsFile){
                throw new GitletException("No reason to remove the file");
            }
        }
        catch (GitletException exception){
            System.out.println("stageAdd.containsKey(name): " + " " + name + " " + stageAdd.containsKey(name));
            System.out.println("tree.containsKey(name)" +" " + name + " " + tree.containsKey(name) );
            System.out.println("No reason to remove the file.");
            return false;
        }
        return true;
    }

    public static void findChecker(boolean exist){
        try {
            if(!exist){
                throw new GitletException("Found no commit with that message.");
            }
        }
        catch (GitletException exception){
            System.out.println("Found no commit with that message.");
            return;
        }
    }
}
