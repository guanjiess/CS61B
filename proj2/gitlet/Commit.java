package gitlet;

// TODO: any imports you need here

import java.io.File;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date; // TODO: You'll likely use this in this class
import java.util.HashMap;

import static gitlet.Utils.*;

/** Represents a gitlet commit object.
 *  @author Gsx
 */
public class Commit implements Serializable {
    /**
     * TODO: add instance variables here.
     *
     * List all instance variables of the Commit class here with a useful
     * comment above them describing what that variable represents and how that
     * variable is used. We've provided one example for `message`.
     */

    /** The message of this Commit. */
    private final String ID = "commit";
    private String message;

    /** time stamp of each commit*/
    private String timestamp;
    /** the parent reference of a commit, parent2 is used for merges
     *  content of parent is hash of the parent commit
     * */
    private String parent;
    private String parent2;
    private String CommitName;
    /** tree is used to store mapping between filename and their SHA-1
     *  */
    private HashMap<String, String> tree = new HashMap<>();

    public Commit(){
        this.message = "initial commit";
        this.timestamp = "01/01/1970 00:00:00";
        this.tree = new HashMap<>();
        this.CommitName = getHash();
    }

    public Commit(String newMessage, String parentHash, HashMap<String, String> newTree){
        this.parent = parentHash;
        this.message = newMessage;
        this.timestamp = getTime();
        this.tree = newTree;
        this.CommitName = getHash();
    }

    public String getMessage(){
        return this.message;
    }

    public String getTimestamp(){
        return this.timestamp;
    }
    public String getParent(){ return this.parent;}

    /** save the commit object to ./gitlet/objects folder according to the object's hash
    // each hash is calculated using it's metadata and reference, plus a symbol "commit "
     */
    public String saveCommit(){
        String hash;
        String content;
        if(this.message == "initial commit"){
            content = this.message + this.timestamp;
        } else {
            content = this.message + this.timestamp + this.parent + this.tree;
        }
        hash = this.getCommitName();
        String subDirectory = hash.substring(0,2);
        String name = hash.substring(2);

        File COMMIT_FOLDER = join(Repository.GITLET_DIR, "objects", subDirectory);
        if(!COMMIT_FOLDER.exists()){
            COMMIT_FOLDER.mkdir();
        }
        File newCommit = join(COMMIT_FOLDER, name);
        writeObject(newCommit, this);
        return hash;
    }

    public static Commit loadCommit(String hash){
        String dir = hash.substring(0,2);
        String name = hash.substring(2);
        File target = join(Repository.GITLET_DIR, "objects", dir, name);
        return readObject(target, Commit.class);
    }

    private String getTime(){
        Date currentDate = new Date();
        String format = "dd/MM/yyyy HH:mm:ss";
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(currentDate);
    }

    public String getHash(){
        if(this.message .equals("initial commit") ){
            return sha1("commit ", this.message, this.timestamp);
        }
        String content = this.message + this.timestamp + this.parent + this.tree;
        return sha1(content);
//        return sha1("commit ", this.message, this.timestamp, this.parent, this.tree);
    }

    public String getCommitName(){
        return this.CommitName;
    }

    public HashMap<String , String> getTree(){
        return this.tree;
    }

    public static Commit getCurrentConmmit(){
        String hash;
        File HEAD = join(Repository.GITLET_DIR, "HEAD");
        String branch = readContentsAsString(HEAD);
        File latestCommit = join(Repository.GITLET_DIR, branch);
        hash = readContentsAsString(latestCommit);
        return loadCommit(hash);
    }
    public static void setCurrentConmmit(String hash){
        File HEAD = join(Repository.GITLET_DIR, "HEAD");
        String branch = readContentsAsString(HEAD);
        File latestCommit = join(Repository.GITLET_DIR, branch);
        writeContents(latestCommit, hash);
    }

    public static void main(String[] args){
    }

}
