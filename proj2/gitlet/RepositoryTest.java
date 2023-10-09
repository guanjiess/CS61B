package gitlet;

import edu.princeton.cs.algs4.ST;
import org.checkerframework.checker.units.qual.C;
import org.junit.Assert;
import org.junit.Assert.*;
import org.junit.Test;

import java.io.File;
import java.util.HashMap;
import java.util.List;

import static gitlet.Utils.*;

public class RepositoryTest {
    public static final File CWD = new File(System.getProperty("user.dir"));
    public static final File GITLET_DIR = join(CWD, ".gitlet");
    public static final File testSource = join(CWD, "testing", "src");
    public void deleteGitlet(){
        if(GITLET_DIR.exists()){
            //first clear fils in GITLET_DIR
            deleteDirectory(GITLET_DIR);
            GITLET_DIR.delete();
        }
    }
    public void writeSource(String name){
        List<String> filenames = plainFilenamesIn(testSource);
        File newFile = join(CWD, name);
        File source = join(testSource, name);
        String contents = readContentsAsString(source);
        writeContents(newFile, contents);
    }
    public void deleteSource(){
        List<String> filenames = plainFilenamesIn(testSource);
        List<String> filesCWD = plainFilenamesIn(CWD);
        for (String file : filesCWD){
            if(filenames.contains(file)){
                File remove = join(CWD, file);
                remove.delete();
            }
        }
    }

    @Test
    public void splitPointTest(){
        deleteGitlet();
        deleteSource();
        Repository repo1 = new Repository();
        writeSource("a.txt");
        writeSource("b.txt");
        // init commit
        repo1.init();
        repo1.add("a.txt");
        repo1.add("b.txt");
        String commit1 = repo1.commit("save a.txt, b.txt");

        writeSource("c.txt");
        writeSource("d.txt");

        repo1.add("c.txt");
        repo1.add("d.txt");
        String commit2 = repo1.commit("save c.txt, d.txt, this should be split");

        repo1.branch("branch2");

        writeSource("e.txt");
        repo1.add("e.txt");
        String commit3 = repo1.commit("save e.txt");
        writeSource("f.txt");
        repo1.add("f.txt");
        String commit4 = repo1.commit("save f.txt");

        String[] args1 = {"checkout", "branch2"};
        repo1.checkout(args1);

        writeSource("wug.txt");
        repo1.add("wug.txt");
        String commit5 = repo1.commit("save wug.txt");

        Commit C4 = Commit.loadCommit(commit4);
        Commit C5 = Commit.loadCommit(commit5);
        Commit split = repo1.getSplitCommit(C4, C5);
        System.out.println(split.getMessage());

    }
}
