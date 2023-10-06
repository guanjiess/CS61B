package gitlet;
import java.util.LinkedList;
public class CommitTree {
    private String head;
    private LinkedList<String> branch;
    public CommitTree(String head){
        this.head = head;
        this.branch.addFirst(head);
    }

}
