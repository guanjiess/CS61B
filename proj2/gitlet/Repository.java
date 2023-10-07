package gitlet;

import java.io.File;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

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

    /** set up the .gitlet directory, init command.*/
    private class RepoExistException extends Exception{
        public RepoExistException(String message){
            super(message);
        }
    }
    public void init(){
        try {
            if(GITLET_DIR.exists()) throw new RepoExistException("RepoExistException, Gitlet folder already exists!");
        }
        catch (RepoExistException exception){
            System.out.println("A Gitlet version control system already exists in the current directory");
            return;
        }
        File refs = join(GITLET_DIR, "refs");
        File objects = join(GITLET_DIR, "objects");
        File logs = join(GITLET_DIR, "logs");
        File HEAD = join(GITLET_DIR, "HEAD");
        File index = join(GITLET_DIR, "index");
        GITLET_DIR.mkdir();
        refs.mkdir();
        objects.mkdir();
        logs.mkdir();
        Commit initial = new Commit();
        String hash = initial.saveCommit();
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
        File index = join(GITLET_DIR, "index");
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
        System.out.println("add, "+"stageAdd is: " + stageAdd);
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
        File index = join(GITLET_DIR, "index");
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
        return newCommitHash;
    }

    public void rm(String name){
        /** read staging area and current commit*/
        File index = join(GITLET_DIR, "index");
        File file = join(CWD, name);
        StagingArea stage = readObject(index, StagingArea.class);
        HashMap<String, String> stageAdd = stage.getStageAdd();
        HashMap<String, String> stageRemove = stage.getStageRemove();
        Commit currentCommit = Commit.getCurrentConmmit();
        HashMap<String, String> tree = currentCommit.getTree();
        /** check if file exists*/
        boolean fileExist = rmFileCheck(stageAdd, tree, name);
        if(!fileExist){
            System.out.println("rm," + "stageAdd is: " + stageAdd);
            System.out.println("rm," + "stageRemove is: " + stageRemove);
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
        System.out.println("rm," + "stageAdd is: " + stageAdd);
        System.out.println("rm," + "stageRemove is: " + stageRemove);
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

    }

    public static void main(String[] args){

    }
}
