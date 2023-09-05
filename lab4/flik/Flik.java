package flik;

/** An Integer tester created by Flik Enterprises.
 * @author Josh Hug
 * */
public class Flik {
    /** @param a Value 1
     *  @param b Value 2
     *  @return Whether a and b are the same */
    public static boolean isSameNumber(Integer a, Integer b) {
//        System.out.print("a, b is "+ a + " " + b);
        boolean answer = (a - b == 0);
//        System.out.println(a == b);
//        System.out.println(answer);
        return answer;
    }
}
