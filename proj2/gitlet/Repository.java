package gitlet;

import jh61b.junit.In;

import java.io.File;
import java.util.*;

import static gitlet.Utils.*;
import static gitlet.CommandChecker.*;

// TODO: any imports you need here

/** Represents a gitlet repository.
 *  This repo is used to save the detailed operations for each command of gitlet
 *  e.g. add, commit, init ... ...
 *  @author GSX
 */
public class Repository {
    /**
     * TODO: add instance variables here.
     *
     * List all instance variables of the Repository class here with a useful
     * comment above them describing what that variable represents and how that
     * variable is used. We've provided two examples for you.
     */

    /** The current working directory. */
    public static final File CWD = new File(System.getProperty("user.dir"));
    /** The .gitlet directory. */
    public static final File GITLET_DIR = join(CWD, ".gitlet");
    public static final File refs = join(GITLET_DIR, "refs");
    public static final File objects = join(GITLET_DIR, "objects");
    public static final File logs = join(GITLET_DIR, "logs");
    public static final File HEAD = join(GITLET_DIR, "HEAD");
    public static final File index = join(GITLET_DIR, "index");
    public static final File commits = join(GITLET_DIR, "commits");

    /** set up the .gitlet directory, init command.*/
    private class RepoExistException extends Exception{
        public RepoExistException(String message){
            super(message);
        }
    }
    public void init(){
        if(GITLET_DIR.exists()){
            System.out.println("A Gitlet version control system already exists in the current directory");
            System.exit(0);
        }
        GITLET_DIR.mkdir();
        refs.mkdir();
        objects.mkdir();
        logs.mkdir();
        Commit initial = new Commit();
        String hash = initial.saveCommit();
        writeContents(commits, hash);
        writeContents(HEAD, "refs/master");
        File master = join(refs, "master");
        writeContents(master, hash);
    }

    public void add(String name){
        // java gitlet.Main add [filename]
        // adds a copy of the file to the staging area
        // staging an already staged file, overwrite the old version with new contents
        // if current working version of the file is the same as the version in commit, do not stage, and remove it
        File newFile = join(CWD, name);
        try {
            if(!newFile.exists()) throw new GitletException("File does not exist");
        }
        catch (GitletException exception){
            System.out.println("File does not exist.");
            return;
        }
        //get current staging area
        if(!index.exists()){
            StagingArea stage = new StagingArea();
            stage.saveStage();
        }
        StagingArea stage = readObject(index, StagingArea.class);
        HashMap<String, String> stageAdd = stage.getStageAdd();

        /**take the file content and hashes it, then put it into staging area.*/
        String fileContent = readContentsAsString(newFile);
        String fileHash = sha1(fileContent);

        /** compare current working version with version of current commit, if
         * the same, do not add, remove if it is already there. TO BE DONE.*/
        Commit currentCommit = Commit.getCurrentConmmit();
        HashMap<String, String> tree = currentCommit.getTree();

        //files that already exists in last commit
        for (String key : tree.keySet()){
            String keyHash = tree.get(key);
            boolean exists = checkAlreadyExists(stageAdd, keyHash, key);
            if(exists){
                stageAdd.remove(key);
            }
            if(fileHash.equals( keyHash)){
                break;
            } else {
                stageAdd.put(name, fileHash);
            }
        }
        // files not in last commit
        if(!tree.containsKey(name)){
            stageAdd.put(name, fileHash);
        }
        stage.setStageAdd(stageAdd);
        stage.saveStage();
        Blob blob = new Blob(fileHash, fileContent, name);
        blob.saveBlob();
//        System.out.println("add, "+"stageAdd is: " + stageAdd);
    }
    private boolean checkAlreadyExists(HashMap<String, String> stageAdd, String hash, String name){
        for (String key : stageAdd.keySet()){
            if(key.equals(name) & stageAdd.get(key).equals(hash))
                return true;
        }
        return false;
    }

    public String commit(String newMessage){
        // how to construct a commit tree?
        // load parent (head)commit
        Commit parentCommit = Commit.getCurrentConmmit();
        String parentCommitHash = parentCommit.getCommitName();

        /**read staging area data, use the staging area in order to modify the
         * files tracked by the new commit*/
        StagingArea stage = readObject(index, StagingArea.class);
        HashMap<String, String> stageAdd = stage.getStageAdd();
        HashMap<String, String> stageRemove = stage.getStageRemove();

        /**read parentCommit tree*/
        HashMap<String , String > parentTree = parentCommit.getTree();
        String parentCommitMessage = parentCommit.getMessage();
        // update parentTree, write back any new object made or any modified objects read earlier.
        if(parentCommitMessage.equals("initial commit")){
            parentTree = stageAdd;
        }
        /** put files in staging area*/
        for(String key : stageAdd.keySet()){
            parentTree.put(key, stageAdd.get(key));
        }
        /** remove files in staging area*/
        for(String key : stageRemove.keySet()){
            parentTree.remove(key);
        }
        Commit newCommit = new Commit(newMessage, parentCommitHash, parentTree);
        String newCommitHash = newCommit.getCommitName();
        newCommit.saveCommit();
        /** clear staging area and save it.*/
        stageAdd.clear();
        stageRemove.clear();
        stage.setStageAdd(stageAdd);
        stage.setStageRemove(stageRemove);
        stage.saveStage();
        /** modefy current commit*/
        Commit.setCurrentConmmit(newCommitHash);
        writeCommits(newCommitHash);
        return newCommitHash;
    }

    private void writeCommits(String hash){
        String contents = readContentsAsString(commits);
        contents = contents + "\n" + hash;
        writeContents(commits, contents);
    }

    public void rm(String name){
        /** read staging area and current commit*/
        File file = join(CWD, name);
        StagingArea stage = readObject(index, StagingArea.class);
        HashMap<String, String> stageAdd = stage.getStageAdd();
        HashMap<String, String> stageRemove = stage.getStageRemove();
        Commit currentCommit = Commit.getCurrentConmmit();
        HashMap<String, String> tree = currentCommit.getTree();
        /** check if file exists*/
        boolean fileExist = rmFileCheck(stageAdd, tree, name);
        if(!fileExist){
//            System.out.println("rm," + "stageAdd is: " + stageAdd);
//            System.out.println("rm," + "stageRemove is: " + stageRemove);
            return;
        }
        boolean stageAddContains = stageAdd.containsKey(name);
        boolean treeContains = tree.containsKey(name);
        String fileHash = stageAdd.get(name);
        /**added to stage but not committed, just remove the blob*/
        if(stageAddContains && !treeContains ){
            Blob.deleteBlob(fileHash);
            stageAdd.remove(name);
        }
        /**if file was comitted before, remove file and move it to staging area */
        if(treeContains){
            String fileHashinTree = tree.get(name);
            stageRemove.put(name, fileHashinTree);
            if(file.exists()){
                file.delete();
            }
        }
        stage.setStageRemove(stageRemove);
        stage.setStageAdd(stageAdd);
        stage.saveStage();
//        System.out.println("rm," + "stageAdd is: " + stageAdd);
//        System.out.println("rm," + "stageRemove is: " + stageRemove);
    }

    public void log(){
        Commit currentCommit = Commit.getCurrentConmmit();
        String message = currentCommit.getMessage();
        String currentCommitHash = currentCommit.getCommitName();
        while (!message.equals("initial commit")){
            /**TODO merge situation not implemented*/
            System.out.println("===");
            System.out.println("commit"+" "+currentCommitHash);
            System.out.println(currentCommit.getTimestamp());
            System.out.println(message);
            System.out.println();
            currentCommitHash = currentCommit.getParent();
            currentCommit = Commit.loadCommit(currentCommitHash);
            message = currentCommit.getMessage();
        }
        /** out of while loop is initial commit*/
        System.out.println("===");
        System.out.println("commit"+" "+currentCommitHash);
        System.out.println(currentCommit.getTimestamp());
        System.out.println(message);
    }

    public void globallog(){
        String allCommits = readContentsAsString(objects);
        String[] files = allCommits.split("\n");
        Commit current;
        for (String fileHash : files){
            current = Commit.loadCommit(fileHash);
            System.out.println("===");
            System.out.println("commit"+" "+current.getCommitName());
            System.out.println(current.getTimestamp());
            System.out.println(current.getMessage());
            System.out.println();
        }
    }

    public void find(String message){
        File objects = join(GITLET_DIR, "commits");
        String allCommits = readContentsAsString(objects);
        String[] files = allCommits.split("\n");
        boolean equals = true;
        boolean exists = false;
        for (String fileHash : files){
            Commit current = Commit.loadCommit(fileHash);
            equals = current.getMessage().equals(message);
            if(equals){
                exists = true;
                String hash = current.getCommitName();
                System.out.println(hash);
            }
        }
        findChecker(exists);
    }

    public void status(){
        /**Branches*/
        File [] listOfFiles = refs.listFiles();
        LinkedList<String > nameFiles = new LinkedList<>();
        String contents = readContentsAsString(HEAD);
        String [] headcontents = contents.split("/");
        String currentBranch = headcontents[1];
        System.out.println("=== Branches ===");
        System.out.println("*"+currentBranch);

        List<String> fileName = new ArrayList<>();
        if(listOfFiles.length>1){
            for (int i = 1; i < listOfFiles.length; i ++){
                String name = listOfFiles[i].getName();
                fileName.add(name);
//                System.out.println(name);
            }
            Collections.sort(fileName);
            printList(fileName);
        }

        /**Staged files*/
        StagingArea stage = readObject(index, StagingArea.class);
        HashMap<String, String> stageAdd = stage.getStageAdd();
        System.out.println("=== Staged Files ===");
        List<String> stageName = new ArrayList<>();
        for (String filename : stageAdd.keySet()){
            stageName.add(filename);
        }
        printList(stageName);

        /**Removed files*/
        HashMap<String, String> stageRemove = stage.getStageRemove();
        System.out.println("=== Removed Files ===");
        List<String> removeName = new ArrayList<>();
        for (String filename : stageRemove.keySet()){
            removeName.add(filename);
        }
        printList(removeName);

        /**Extra credit, not interested*/
        System.out.println("=== Modifications Not Staged For Commit ===");
        System.out.println();
        System.out.println("=== Untracked Files ===");
        System.out.println();
    }

    public void branch(String name){
        Commit currentCommit = Commit.getCurrentConmmit();
        String commitID = currentCommit.getCommitName();
        File branch = join(refs, name);
        writeContents(branch, commitID);
    }

    public void deleteBranch(String name, String cmd){
        boolean valid = BranchCheck(name, "rm-branch");
        File BadBranch = join(refs, name);
        if(valid){
            BadBranch.delete();
        }
    }

    public void checkout(String[] args){
        String filename;
        switch (args.length){
            case 2 :
                String branch = args[1];
                checkout3(branch);
                break;
            case 3:
                filename = args[2];
                Commit currentCommit = Commit.getCurrentConmmit();
                checkout1(filename, currentCommit);
                break;
            case 4:
                String commitHash = args[1];
                filename = args[3];
                checkout2(commitHash, filename);
                break;
        }
    }

    private void checkout1(String file, Commit commit){
        /** update the file in current commit, put it in working directory*/
        HashMap<String , String> files = commit.getTree();
        boolean exist = fileCheck(file, files);
        if(!exist) return;
        String fileBlobHash = files.get(file);
        Blob commitFile = Blob.loadBlob(fileBlobHash);
        String contents = commitFile.getContents();
        File thisFile = join(CWD, file);
        writeContents(thisFile, contents);
    }
    private void checkout2(String commitHash, String name){
        boolean exist = commitExist(commitHash);
        if(!exist) return;
        Commit requiredCommit = Commit.loadCommit(commitHash);
        checkout1(name, requiredCommit);
    }
    private void checkout3(String branch){
        /**check if there's no such branch or already in the branch*/
        boolean branchExist = BranchCheck(branch, "checkout");
        if(!branchExist){
            return;
        }
        /**check if there's working file untracked in current commit*/
        Commit currentCommit = Commit.getCurrentConmmit();
        HashMap<String, String> tree = currentCommit.getTree();
        List<String> filesCWD = plainFilenamesIn(CWD);
        boolean checkCWD = checkoutFileCompare(tree, filesCWD);
        if(!checkCWD){
            return;
        }
        /**put files in CWD*/
        File branchFile = join(refs, branch);
        String branchhead = readContentsAsString(branchFile);
        Commit branchHEAD = Commit.loadCommit(branchhead);
        HashMap<String, String> branchHeadFile = branchHEAD.getTree();
        /** delete files that do not exist in branchHEAD*/
        for (String file : filesCWD){
            if(!branchHeadFile.containsKey(file)){
                rm(file);
            }
        }
        writeFiles(branchHeadFile);
        String newHEAD = "refs/"+branch;
        writeContents(HEAD, newHEAD);
        StagingArea stage = readObject(index, StagingArea.class);
        stage.clearStage();
        stage.saveStage();

    }
    private void writeFiles(HashMap<String, String> files){
        Set<String> fileNames = files.keySet();
        for (String name : fileNames){
            String blobHash = files.get(name);
            Blob blob = Blob.loadBlob(blobHash);
            String contents = blob.getContents();
            File newFile = join(CWD, name);
            writeContents(newFile, contents);
        }
    }
    public void reset(String commit){

    }

    public void merge(String branch){
        StagingArea stage = readObject(index, StagingArea.class);
        stageIsEmpty(stage);
        branchExist(branch);
        branchSameAsCurr(branch);
        /** get split point*/
        String currentBranch = getCurrentBranch();
        Commit currentCommit = Commit.getCurrentConmmit();
        Commit branchCommit = getBranchCommit(branch);
        Commit splitCommit = getSplitCommit(currentCommit, branchCommit );

        /**find 8 situations*/


        /**set*/

    }
    private Commit getBranchCommit(String branch){
        File theBranch = join(refs, branch);
        String commitName = readContentsAsString(theBranch);
        Commit branchCommit = Commit.loadCommit(commitName);
        return branchCommit;
    }
    public Commit getSplitCommit(Commit commitA, Commit commitB){
        HashMap<String, Integer> commitTreeA = commitTree(commitA);
        HashMap<String, Integer> commitTreeB = commitTree(commitB);
        int min = Integer.MAX_VALUE;
        String split = "";
        for (String commit : commitTreeA.keySet()){
            if(commitTreeB.containsKey(commit)){
                if(commitTreeA.get(commit) < min){
                    min = commitTreeA.get(commit);
                    split = commit;
                }
            }
        }
        Commit splitCommit = Commit.loadCommit(split);
        return splitCommit;
    }

    private HashMap<String, Integer> commitTree(Commit start){
        HashMap<String, Integer> commitInBranch = new HashMap<>();
        int depth = 1;
        String commitID = start.getCommitName();
        commitInBranch.put(commitID, depth);
        String parentID = start.getParent();
        Commit parent = Commit.loadCommit(parentID);
        while (parentID != null){
            depth ++;
            commitInBranch.put(parentID, depth);
            parent = Commit.loadCommit(parentID);
            parentID = parent.getParent();
        }
        depth ++;
        commitInBranch.put(parentID, depth);
        return commitInBranch;
    }


    public static void main(String[] args){


//        repo1.merge(args2);
//        repo1.merge(args3);
//        repo1.merge(args4);
//        repo1.merge(args3);

    }
}
