package timingtest;
import edu.princeton.cs.algs4.Stopwatch;

import java.nio.DoubleBuffer;

/**
 * Created by hug.
 */
public class TimeAList {
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
        timeAListConstruction();
    }

    public static void timeAListConstruction() {
        // TODO: YOUR CODE HERE
        int [] N = {1000, 2000, 4000, 8000, 16000, 32000, 64000, 128000};
        int size = N.length;
        AList<Integer> Ns = new AList<Integer>();
        AList<Integer> opCounts = new AList<Integer>();
        AList<Double> times = new AList<Double>();
        for(int k=0; k<N.length; k++){
            Ns.addLast(N[k]);
            opCounts.addLast(N[k]);
            Stopwatch sw = new Stopwatch();
            AList<Integer> newList = new AList<Integer>();
            for(int j = 0; j < N[k]; j++){
                newList.addLast(j);
            }
            double timeInSeconds = sw.elapsedTime();
            times.addLast(timeInSeconds);
        }
        printTimingTable(Ns, times, opCounts);

    }
}
