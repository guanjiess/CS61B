package gitlet;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

import static gitlet.Utils.*;

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
        writeContents(HEAD, "ref: refs/heads/master");
        Commit initial = new Commit("initial commit", null);
        initial.saveCommit();

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
        // take the file content and hashes it
        String fileContent = readContentsAsString(newFile);
        String fileHash = sha1(fileContent);
        // create and save the new blob object
        Blob blob = new Blob(fileHash, fileContent, name);
        blob.saveBlob();

        String BlobInfo = blob.getID() + " " + blob.getfileHash() + " " +blob.getName();
        writeIndex(BlobInfo);
    }
    private void writeIndex(String blobInfo){
        // save all the required blob information in index.
        // include identifier, SHA-1 code, file name
        // may be
        File index = join(GITLET_DIR, "index");
        String newIndex;
        if(!index.exists()){
            newIndex = blobInfo;
        } else {
            newIndex = readContentsAsString(index) + "\n" + blobInfo;
        }
        writeContents(index, newIndex);
        System.out.println(newIndex);
    }

    public void commit(String message){
        // read from computer the head commit object and the staging area
        // clone the head commit
        // modify its message and timestamp according to user input
        // use the staging area in order to modify the files tracked by the new commit

        // write back any new object made or any modified objects read earlier.
        // what is serialize in java? how to save them in files?
        // how to construct a commit tree?
        Commit newCommit = new Commit(message, null);

    }
}
