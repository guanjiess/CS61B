import java.util.HashSet;
import java.util.Set;

public class Iterators {
    public static void main(String[] args){
        Set<Integer> javaset = new HashSet<>();
        javaset.add(5);
        javaset.add(6);
        javaset.add(88);
        for (int i : javaset){
            System.out.println(i);
        }

        Set<String> s = new HashSet<>();
        s.add("Tokyo");
        s.add("Lagos");
        for (String city : s) {
            System.out.println(city);
        }

    }
}
