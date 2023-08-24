package IntList;

import static org.junit.Assert.*;
import org.junit.Test;

public class SquarePrimesTest {

    /**
     * Here is a test for isPrime method. Try running it.
     * It passes, but the starter code implementation of isPrime
     * is broken. Write your own JUnit Test to try to uncover the bug!
     */
    @Test
    public void testSquarePrimesSimple() {
        IntList lst = IntList.of(14, 15, 16, 17, 18);
        boolean changed = IntListExercises.squarePrimes(lst);
        assertEquals("14 -> 15 -> 16 -> 289 -> 18", lst.toString());
        assertTrue(changed);
    }
    @Test
    public void testSquarePrime1(){
        IntList list1 = IntList.of(1,2,3,4,5);
        boolean changed = IntListExercises.squarePrimes(list1);
        String expected = "1 -> 4 -> 9 -> 4 -> 25";
        String SquaredList = list1.toString();
        assertEquals(expected,SquaredList);
    }

    @Test
    public void testSquarePrimesLong() {
        IntList lst = IntList.of(1, 7, 9, 23, 14, 15, 16, 17, 18);
        boolean changed = IntListExercises.squarePrimes(lst);
        assertEquals("1 -> 49 -> 9 -> 529 -> 14 -> 15 -> 16 -> 289 -> 18", lst.toString());
        assertTrue(changed);
    }





}
