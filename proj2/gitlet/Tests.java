package gitlet;

import org.junit.Test;

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


}
