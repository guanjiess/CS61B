package gitlet;

import java.io.File;
import java.io.Serializable;
import java.util.HashMap;
import static gitlet.Utils.*;

public class StagingArea implements Serializable {
    public static final File index = join(Repository.GITLET_DIR, "index");
    private String ID;
    private HashMap<String, String> stageAdd;
    private HashMap<String, String> stageRemove;
    public StagingArea(){
        this.stageAdd = new HashMap<>();
        this.stageRemove = new HashMap<>();
    }
    public void setStageAdd(HashMap<String , String> stageAdd){
        this.stageAdd = stageAdd;
    }
    public void setStageRemove(HashMap<String , String> stageRemove){
        this.stageRemove = stageRemove;
    }
    public HashMap<String, String> getStageAdd(){
        return this.stageAdd;
    }
    public HashMap<String, String> getStageRemove(){
        return this.stageRemove;
    }
    public void saveStage(){
        writeObject(index, this);
    }

}
