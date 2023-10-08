package gitlet;

import java.util.*;
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

    public static boolean BranchCheck(String name, String cmd){
        File refs = Repository.refs;
        File [] listOfFiles = refs.listFiles();
        HashSet<String> filenames = new HashSet<>();
        for (File file : listOfFiles){
            filenames.add(file.getName());
        }

        String message1 = "A branch with that name does not exist.";
        String message2 = "No such branch exists.";
        String message3 = "Can not remove the current branch.";
        String message4 = "No need to checkout the current branch.";
        String MESSAGE1 = message1;
        String MESSAGE2 = message3;
        if(cmd.equals("rm-branch")){
            MESSAGE1 = message1;
            MESSAGE2 = message3;
        }

        if(cmd.equals("checkout")){
            MESSAGE1 = message2;
            MESSAGE2 = message4;
        }
        try {
            if(!filenames.contains(name)){
                throw new GitletException(MESSAGE1);
            }
        }
        catch (GitletException exception){
            System.out.println(MESSAGE1);
            return false;
        }

        if(cmd.equals("rm-branch")){
            String contents = readContentsAsString(Repository.HEAD);
            String [] headcontents = contents.split("/");
            String currentBranch = headcontents[1];
            try {
                if(currentBranch.equals(name)){
                    throw new GitletException(MESSAGE2);
                }
            }
            catch (GitletException exception){
                System.out.println(MESSAGE2);
                return false;
            }
        }

        if(cmd.equals("checkout")){
            String contents = readContentsAsString(Repository.HEAD);
            String [] headcontents = contents.split("/");
            String currentBranch = headcontents[1];
            try {
                if(currentBranch.equals(name)){
                    throw new GitletException(MESSAGE2);
                }
            }
            catch (GitletException exception){
                System.out.println(MESSAGE2);
            }
        }
        return true;
    }


    public static boolean fileCheck(String name, HashMap<String, String> tree){
        boolean exist = tree.containsKey(name);
        try{
            if(!exist) throw new GitletException("File does not exist in that commit.");
        }
        catch (GitletException exception){
            System.out.println("File does not exist in that commit.");
            return false;
        }
        return exist;
    }
    public static boolean checkoutFileCompare(HashMap<String, String> tree, List<String> files){
        String name = "";
        boolean contains = true;
        /**take each file's name and compare it*/
        for (int i = 0; i < files.size(); i++){
            name = files.get(i);
            if(!tree.containsKey(name)){
                contains = false;
                break;
            }
        }
        try{
            if(!contains) throw new GitletException("error");
        }
        catch (GitletException exception){
            System.out.println("There is an untracked file in the way; delete it, or add and commit it first.");
            return false;
        }
        return true;
    }


    public static boolean commitExist(String commit){
        boolean exist = false;
        File objects = join(Repository.GITLET_DIR, "commits");
        String allCommits = readContentsAsString(objects);
        String[] files = allCommits.split("\n");
        for (int i = 0; i < files.length; i++ ){
            if(files[i].equals(commit)) exist = true;
        }
        try {
            if(!exist)
                throw new GitletException("No commit with that id exists.");
        }
        catch (GitletException exception){
            System.out.println("No commit with that id exists.");
            return false;
        }
        return true;
    }



}
