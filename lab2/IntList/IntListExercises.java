package IntList;

public class IntListExercises {

    /**
     * Part A: (Buggy) mutative method that adds a constant C to each
     * element of an IntList
     *
     * @param lst IntList from Lecture
     */
    public static void addConstant(IntList lst, int c) {
        IntList head = lst;
        while (head.rest != null) {
            head.first += c;
            head = head.rest;
        }
        head.first += c;
    }

    /**
     * Part B: Buggy method that sets node.first to zero if
     * the max value in the list starting at node has the same
     * first and last digit, for every node in L
     *
     * @param L IntList from Lecture
     */
    public static void setToZeroIfMaxFEL(IntList L) {
        IntList p = L;
        while (p != null) {
            int currentMax = max(p);
            boolean firstEqualsLast = firstDigitEqualsLastDigit(currentMax);
            if (firstEqualsLast) {
                p.first = 0;
            }
            p = p.rest;
        }
    }

    /** Returns the max value in the IntList starting at L. */
    public static int max(IntList L) {
        int max = L.first;
        IntList p = L.rest;
        while (p != null) {
            if (p.first > max) {
                max = p.first;
            }
            p = p.rest;
        }
        return max;
    }

    /** Returns true if the last digit of x is equal to
     *  the first digit of x.
     */
    public static boolean firstDigitEqualsLastDigit(int x) {
        int lastDigit = x % 10;
        while (x >= 10) {
            x = x / 10;
        }
        int firstDigit = x % 10;
        return firstDigit == lastDigit;
    }

    /**
     * Part C: (Buggy) mutative method that squares each prime
     * element of the IntList.
     *
     * @param lst IntList from Lecture
     * @return True if there was an update to the list
     */
    public static boolean squarePrimes(IntList lst) {
        // Base Case: we have reached the end of the list
/*        if (lst == null) {
            return false;
        }

        boolean currElemIsPrime = Primes.isPrime(lst.first);

        if (currElemIsPrime) {
            lst.first *= lst.first;
        }

        return currElemIsPrime || squarePrimes(lst.rest);*/
        IntList p = lst;
        boolean currElemIsPrime = false;
        boolean changed = false;
        for(int i=0; i<lst.size(); i++){
            currElemIsPrime = Primes.isPrime(p.first);
            if(currElemIsPrime){
                p.first *= p.first;
                changed = true;
            }
            p = p.rest;
        }
       /* while (p.rest != null){
            currElemIsPrime = Primes.isPrime(p.first);
            if(currElemIsPrime){
                p.first *= p.first;
                changed = true;
            }
            p = p.rest;
        }*/
        return changed;
    }

    public static void  main(String[] args){

        IntList lst = IntList.of(14, 15, 16, 17, 18);
        System.out.println(lst.toString());
// Output: 14 -> 15 -> 16 -> 17 -> 18
        boolean changed = squarePrimes(lst);
        System.out.println(lst.toString());
// Output: 14 -> 15 -> 16 -> 289 -> 18
        System.out.println(changed);
// Output: true
    }
}
