package randomizedtest;

import edu.princeton.cs.algs4.StdRandom;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Created by hug.
 */
public class TestBuggyAList {
  // YOUR TESTS HERE
    @Test
    public void testThreeAddThreeRemove(){
        AListNoResizing<Integer> Alist1 = new AListNoResizing<>();
        BuggyAList<Integer> Alist2 = new BuggyAList<>();
        Alist1.addLast(1);
        Alist2.addLast(1);
        Alist1.addLast(2);
        Alist2.addLast(2);
        Alist1.addLast(3);
        Alist2.addLast(3);
        boolean same = false;

        assertEquals("Size is not the same", Alist1.size(), Alist2.size());
        assertEquals("The out put of 2 lists is not the same",Alist1.removeLast(),Alist2.removeLast());
        assertEquals("The out put of 2 lists is not the same",Alist1.removeLast(),Alist2.removeLast());
        assertEquals("The out put of 2 lists is not the same",Alist1.removeLast(),Alist2.removeLast());

//        assertTrue("The out put of 2 lists is not the same", Alist1.removeLast() == Alist2.removeLast());
//        assertTrue("The out put of 2 lists is not the same", same);
//        assertTrue("The out put of 2 lists is not the same", same);
   }
   @Test
    public void randomizedTest(){
       AListNoResizing<Integer> L = new AListNoResizing<>();
       BuggyAList<Integer> L2 = new BuggyAList<>();
       int N = 5000;
       for (int i = 0; i < N; i += 1) {
           int operationNumber = StdRandom.uniform(0, 4);
           if (operationNumber == 0) {
               // addLast
               int randVal = StdRandom.uniform(0, 100);
               L.addLast(randVal);
               L2.addLast(randVal);
//               System.out.println("addLast(" + randVal + ")");
           } else if (operationNumber == 1) {
               // size
               int size = L.size();
               int size2 = L2.size();
               assertEquals("Size is not the same",size, size2);
           } else if (operationNumber == 2 & L.size()>0) {
               // getlast
               int last1 = L.getLast();
               int last2 = L2.getLast();
               assertEquals("Something wrong with getlast", last1, last2);
           } else if (operationNumber == 3 & L.size()>0) {
               int removed1 = L.removeLast();
               int removed2 = L2.removeLast();
               assertEquals("Something wrong with removeLast", removed2, removed1);
           }
       }
    }

}
