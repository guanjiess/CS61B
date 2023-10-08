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
                if(!folderCheck()) return;
                if(!validateNumArgs("add", args, 2)) return;
                String name = args[1];
                newRepo.add(name);
                break;
            case "commit":
                if(!folderCheck()) return;
                if(!commitValid(args)) return;
                String message = args[1];
                newRepo.commit(message);
                break;
            case "rm":
                if(!folderCheck()) return;
                if(!validateNumArgs("rm", args, 2)) return;
                String delete = args[1];
                newRepo.rm(delete);
                break;
            case "log":
                if(!folderCheck()) return;
                newRepo.log();
                break;
            case "global-log":
                if(!folderCheck()) return;
                newRepo.globallog();
                break;
            case "find":
                if(!folderCheck()) return;
                if(!validateNumArgs("find", args, 2)) return;
                String requiredMessage = args[1];
                newRepo.find(requiredMessage);
                break;
            case "status":
                if(!folderCheck()) return;
                newRepo.status();
                break;
            case "checkout":
                if(!folderCheck()) return;
                newRepo.checkout(args);
                break;
            case "branch":
                if(!folderCheck()) return;
                if(!validateNumArgs("branch", args, 2)) return;
                String branch = args[1];
                newRepo.branch(branch);
                break;
            case "rm-branch":
                if(!folderCheck()) return;
                if(!validateNumArgs("rm-branch", args, 2)) return;
                String thisIsBad = args[1];
                newRepo.deleteBranch(thisIsBad, "rm-branch");
                break;
            case "reset":
                if(!folderCheck()) return;
                if(!validateNumArgs("reset", args, 2)) return;
                String resetTo = args[1];
                newRepo.reset(resetTo);
                break;
            case "merge":

        }
    }
}
