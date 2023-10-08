package gitlet;

import org.junit.Test;

import java.io.File;

import static gitlet.Utils.join;
import static gitlet.Utils.writeContents;
import static org.junit.Assert.*;

public class Tests {

    @Test
    public void addTest(){

    }

    @Test
    public void rmTest(){
        Repository repo1 = new Repository();
        repo1.init();
        repo1.add("ttt.txt");
        repo1.add("test1.txt");
        repo1.add("test2.txt");
        repo1.add("test3.txt");
        repo1.rm("test2.txt");
        repo1.rm("test1.txt");
        repo1.commit("saved");
        repo1.rm("test2.txt");
        repo1.rm("test3.txt");
        repo1.rm("ttt.txt");
    }

    @Test
    public void logTest(){
        Repository repo1 = new Repository();
        repo1.init();
        repo1.add("ttt.txt");
        repo1.commit("save ttt.txt");
        repo1.add("test.txt");
        repo1.commit("save test.txt");
        repo1.add("test2.txt");
        repo1.commit("save test2.txt");
        repo1.rm("test2.txt");
        repo1.commit("delete test2.txt");
        repo1.log();
    }

    @Test
    public void checkoutTest1(){
        Repository repo1 = new Repository();
        repo1.init();
        repo1.add("ttt.txt");
        repo1.add("test.txt");
        repo1.commit("save ttt.txt, test.txt");
        String[] args1 = {"test2.txt"};
        /**there is no test2.txt*/
        repo1.checkout(args1);

        /**test2.txt should be recovered*/
        repo1.add("test2.txt");
        repo1.commit("2322");
        repo1.rm("test2.txt");
        repo1.checkout(args1);

        /**test2.txt should be overwrite with old version*/
        repo1.checkout(args1);
    }
    @Test
    public void checkoutTest2(){

        Repository repo1 = new Repository();
        repo1.init();
        repo1.add("ttt.txt");
        repo1.add("test.txt");
        String commit2 = repo1.commit("save ttt.txt, test.txt");
        String[] args1 = {"checkout","--","test2.txt"};
        /**there is no test2.txt*/
        repo1.checkout(args1);

        repo1.add("test2.txt");
        String commit3 = repo1.commit("add test2.txt");
        /**overwrite test2.txt first time*/
        File thisFile = join(Repository.CWD, "test2.txt");
        writeContents(thisFile, "No! It's test3!!!, edited first time");
        repo1.add("test2.txt");
        String commit4 = repo1.commit("edite test2 first time");


        /**overwrite test2.txt second time*/
        writeContents(thisFile, "Should be test4!, edited second time");
        repo1.add("test2.txt");
        String commit5 = repo1.commit("edite test2 first time");

        String[] args2 = {"checkout",commit2,"--","test2.txt"};
        String [] args3 = {"checkout",commit3,"--","test2.txt"};
        String [] args4 = {"checkout",commit4,"--","test2.txt"};
        String [] args5 = {"checkout",commit5,"--","test2.txt"};

        /**contents should be "No! It's test3!!!, edited first time"*/
        repo1.checkout(args2);
        repo1.checkout(args3);
        repo1.checkout(args4);
        repo1.checkout(args5);
    }


}
