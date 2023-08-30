package timingtest;
import edu.princeton.cs.algs4.Stopwatch;

import java.nio.DoubleBuffer;

/**
 * Created by hug.
 */
public class TimeSLList {
    private static void printTimingTable(AList<Integer> Ns, AList<Double> times, AList<Integer> opCounts) {
        System.out.printf("%12s %12s %12s %12s\n", "N", "time (s)", "# ops", "microsec/op");
        System.out.printf("------------------------------------------------------------\n");
        for (int i = 0; i < Ns.size(); i += 1) {
            int N = Ns.get(i);
            double time = times.get(i);
            int opCount = opCounts.get(i);
            double timePerOp = time / opCount * 1e6;
            System.out.printf("%12d %12.2f %12d %12.2f\n", N, time, opCount, timePerOp);
        }
    }

    public static void main(String[] args) {
        timeGetLast();
    }

    public static void timeGetLast() {
        // TODO: YOUR CODE HERE
        int [] N = {1000, 2000, 4000, 8000, 16000, 32000, 64000, 128000};
        int size = N.length;
        AList<Integer> Ns = new AList<Integer>();
        AList<Integer> opCounts = new AList<Integer>();
        AList<Double> times = new AList<Double>();
        int M = 10000;
        for(int k = 0; k < size; k++ ){
            SLList newList = new SLList();
            Ns.addLast(N[k]);
            opCounts.addLast(M);
            for(int i = 0; i < N[k]; i ++){
                newList.addLast(i);
            }
            Stopwatch sw = new Stopwatch();
            for(int i = 0; i < M; i++){
                int lastTerm = 0;
                lastTerm = (int) newList.getLast();
            }
            double time = sw.elapsedTime();
            times.addLast(time);
        }
        printTimingTable(Ns, times, opCounts);
    }
}
