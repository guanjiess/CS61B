package gitlet;

import static gitlet.CommandChecker.*;
import static gitlet.Repository.*;

/** Driver class for Gitlet, a subset of the Git version-control system.
 *  @author TODO
 */
public class Main {

    /** Usage: java gitlet.Main ARGS, where ARGS contains
     *  <COMMAND> <OPERAND1> <OPERAND2> ... 
     */
    public static void main(String[] args) {
        // TODO: what if args is empty?
        if(!commandExists(args)){
            return;
        }
        String firstArg = args[0];
        Repository newRepo = new Repository();
        switch(firstArg) {
            case "init":
                if(!validateNumArgs("init", args, 1)) return;
                newRepo.init();
                break;
            case "add":
                if(!validateNumArgs("add", args, 2)) return;
                String name = args[1];
                newRepo.add(name);
                break;
            case "commit":
                if(!commitValid(args)) return;
                String message = args[1];
                newRepo.commit(message);
                break;
            case "rm":

            case "log":

            case "global-log":

            case "find":

            case "status":

            case "checkout":

            case "branch":

            case "rm-branch":

            case "reset":

            case "merge":

        }
    }
}
