package gitlet;
import java.io.File;
import java.io.Serializable;
import static gitlet.Utils.*;

public class Blob implements Serializable{
    /** ID is used to identify blob and commit objects
     *  fileHash is a 40 bit SHA-1
     *  contents is read from source file
     * */
    private final String ID = "blob";
    private String fileHash;
    private String contents;
    private String fileName;

    public Blob(){}
    public Blob(String fileHash, String contents, String name){
        this.fileName = name;
        this.fileHash = fileHash;
        this.contents = contents;
    }

    public String getID(){ return this.ID;}
    public String getfileHash(){return this.fileHash;}
    public String getContents(){return this.contents;}
    public String getName(){return this.fileName;}

    public String saveBlob(){
        String dir = this.fileHash.substring(0,2);
        String fileHash = this.fileHash.substring(2);
        File BLOB_FOLDER = join(Repository.GITLET_DIR, "objects", dir);
        if(!BLOB_FOLDER.exists())
        {
            BLOB_FOLDER.mkdir();
        }
        File blob = join(BLOB_FOLDER, fileHash);
        writeObject(blob, this);
        return this.fileHash;
    }

    public static Blob loadBlob(String hash){
        String dir = hash.substring(0,2);
        String fileHash = hash.substring(2);
        File target = join(Repository.GITLET_DIR, "objects", dir, fileHash);
        return readObject(target, Blob.class);
    }

    public static boolean deleteBlob(String fileHash){
        String dir = fileHash.substring(0,2);
        String hashName = fileHash.substring(2);
        File target = join(Repository.GITLET_DIR, "objects", dir, hashName);
        return target.delete();
    }

    public static void main(String[] args){
        File newFile = join(Repository.CWD, "test.txt");
        String fileContent = readContentsAsString(newFile);
        String fileHash = sha1(fileContent);
        System.out.println("File content is: " + fileContent);
        System.out.println("File SHA-1 is: " + fileHash);
        // create and save the new blob object
        Blob blob = new Blob(fileHash, fileContent, "test2.txt");
        blob.saveBlob();
        Blob fromFile = loadBlob("5d03965084a5db13c178cbb1ffc120b360353685");
        System.out.println("Read from file: " + fromFile.ID);
        System.out.println("Read from file: " + fromFile.contents);
        System.out.println("Read from file: " + fromFile.fileHash);
    }
}
