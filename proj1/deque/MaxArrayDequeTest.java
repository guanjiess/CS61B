package deque;
import org.junit.Test;
import static org.junit.Assert.*;

import java.util.Comparator;

public class MaxArrayDequeTest {
    @Test
    public void maxTest(){
        Comparator<Integer> compInteger = new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                int result = 0;
                if(o1 > o2){
                    result = 1;
                }
                if(o1 == o2){
                    result = 0;
                }
                if(o1 < o2){
                    result = -1;
                }
                return result;
            }
        };

        Comparator<String> comString = new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                int result = 0;
                if(o1.length() > o2.length()){
                    result = 1;
                }
                if(o1.length() == o2.length()){
                    result = 0;
                }
                if(o1.length() < o2.length()){
                    result = -1;
                }
                return result;
            }
        };


        MaxArrayDeque<Integer> MAD1= new MaxArrayDeque<>(compInteger);
        for (int i=0; i<18 ;i++){
            MAD1.addLast(i);
        }
        MAD1.addFirst(24);
        Object max = MAD1.max();
        assertEquals(24, max);

        MaxArrayDeque<String> MAD2= new MaxArrayDeque<>();
        MAD2.addFirst("MAD2");
        MAD2.addFirst("D2");
        MAD2.addFirst("dsadMAD2");
        MAD2.addFirst("MAD2dsadasdad");
        Object max2 = MAD2.max(comString);
        assertEquals("MAD2dsadasdad" ,max2);

    }

}
