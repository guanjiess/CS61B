package deque;

import org.checkerframework.checker.units.qual.A;
import org.junit.Assert;
import org.junit.Test;
import static org.junit.Assert.*;
public class ArrayDequeTest {

    @Test
    public void addIsEmptySizeTest(){
        ArrayDeque L = new ArrayDeque(-1, 0, 8);
        assertTrue("This empty deque should return true", L.isEmpty());
        L.addFirst(1);
        assertEquals(1, L.size());
        L.addFirst(2);
        assertEquals(2,L.size());
        L.addFirst(3);
        assertEquals(3, L.size());
        L.addLast(4);
        L.addLast(5);
        L.addLast(6);
        assertEquals(6, L.size());
        L.printDeque();
    }

    @Test
    public void getTest(){
        ArrayDeque L = new ArrayDeque(-1, 0, 8);
        for (int i=0; i<4; i++){
            L.addLast(i);
            System.out.println("Current size is: "+L.size());
        }
        for (int i=0; i<4; i++){
            L.addFirst(i);
            System.out.println("Current size is: "+L.size());
        }
        for (int i=0; i<8; i++){
            int target = L.get(i);
//            assertEquals(i, target);
        }
    }

    @Test
    public void removeTest(){
        ArrayDeque L = new ArrayDeque(-1, 0, 8);
        for (int i=0; i<8; i++){
            L.addLast(i);
        }

        for(int i=0; i<8; i++){
            int term = L.removeFirst();
            assertEquals(term, i);
        }

        for (int i=0; i<8; i++){
            L.addLast(i);
        }

        for(int i=0; i<8; i++){
            int term = L.removeLast();
            assertEquals(term, 8-i-1);
        }
    }

    @Test
    public void printTest(){
        ArrayDeque L = new ArrayDeque(-1, 0, 8);
        for (int i=0; i<8; i++){
            L.addLast(i);
        }
        L.printDeque();
        ArrayDeque L2 = new ArrayDeque(-1, 0, 8);
        for (int i=0; i<4; i++){
            L2.addLast(i);
        }
        for (int i=0; i<4; i++){
            L2.addFirst(i);
        }
        L2.printDeque();
        ArrayDeque L3 = new ArrayDeque(-1,0, 33);
        for(int i=0; i<33; i++){
            L3.addFirst(i);
        }
        L3.printDeque();
        for (int k=0; k<6; k++){
            L3.removeLast();
        }
        L3.printDeque();
        for (int k=0; k<6; k++){
            L3.removeFirst();
            L3.printDeque();
        }
    }
    @Test
    public void addTest(){
        ArrayDeque a1 = new ArrayDeque(2, 3, 15);
        for(int i=0; i<7; i++){
            String message = String.format("Inserted item %d to first", i);
            System.out.println(message);
            a1.addFirst(i);
            a1.printDeque();
            a1.printItems();
        }

    }

    @Test
    public void resizeTest(){
        ArrayDeque a1 = new ArrayDeque(2, 3, 15);
        for(int i=0; i<15; i++){
            a1.addFirst(i+1);
        }
        a1.printDeque();
        a1.addFirst(666);
        a1.printDeque();
        a1.printItems();
        a1.addFirst(777);
        a1.printDeque();
        a1.printItems();
        a1.addFirst(888);
        a1.printDeque();
        a1.printItems();
        for(int i=0; i<12; i++){
            a1.addFirst(i+1);
        }
        a1.printItems();
        a1.addLast(95527);
        a1.printItems();
    }


}
