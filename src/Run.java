import Model.Graph;

import java.util.Arrays;

/**
 * Created by Tomek on 2017-03-30.
 */
public class Run {
    public static int N = 14;
    public static final int TIMES = 1;
    public static void main(String... args) {

        long startTime;
        long endTime;

        long times[] = new long[TIMES];
        System.out.println("Backtracking");
        for(int i=2; i < N; i++) {
            for(int j=0; j < TIMES; j++) {
                Graph graph = new Graph();
                graph.generate(i);
                startTime = System.currentTimeMillis();
                graph.forwardCheckingAlgorithm();
                endTime = System.currentTimeMillis();
                times[j] = endTime - startTime;
            }

            System.out.println(i + "," + Arrays.stream(times).average().getAsDouble());
        }
/*

        System.out.println("Forward checking");
        for(int i=2; i < N; i++) {
            for(int j=0; j < TIMES; j++) {
                Graph graph = new Graph();
                graph.generate(i);
                startTime = System.currentTimeMillis();
                graph.forwardCheckingAlgorithm();
                endTime = System.currentTimeMillis();
                times[j] = endTime - startTime;
            }

            System.out.println(i + "," + Arrays.stream(times).average().getAsDouble());
        }

        System.out.println("Forward Checking most");
        for(int i=2; i < N; i++) {
            for(int j=0; j < TIMES; j++) {
                Graph graph = new Graph();
                graph.generate(i);
                startTime = System.currentTimeMillis();
                graph.forwardCheckingAlgorithmWithHeuristic(graph.MostConstrainingVariable());
                endTime = System.currentTimeMillis();
                times[j] = endTime - startTime;
            }

            System.out.println(i + "," + Arrays.stream(times).average().getAsDouble());
        }

        System.out.println("Forward Checking MIn");
        for(int i=2; i < N; i++) {
            for(int j=0; j < TIMES; j++) {
                Graph graph = new Graph();
                graph.generate(i);
                startTime = System.currentTimeMillis();
                graph.forwardCheckingAlgorithmWithHeuristic(graph.MinimumRemainingValues());
                endTime = System.currentTimeMillis();
                times[j] = endTime - startTime;
            }

            System.out.println(i + "," + Arrays.stream(times).average().getAsDouble());
        }

        System.out.println("Backtracking Most");
        for(int i=2; i < N; i++) {
            for(int j=0; j < TIMES; j++) {
                Graph graph = new Graph();
                graph.generate(i);
                startTime = System.currentTimeMillis();
                graph.backtrackingAlgorithmWithHeuristic(graph.MostConstrainingVariable());
                endTime = System.currentTimeMillis();
                times[j] = endTime - startTime;
            }

            System.out.println(i + "," + Arrays.stream(times).average().getAsDouble());
        }

        System.out.println("Backtracking Min");
        for(int i=2; i < N; i++) {
            for(int j=0; j < TIMES; j++) {
                Graph graph = new Graph();
                graph.generate(i);
                startTime = System.currentTimeMillis();
                graph.backtrackingAlgorithmWithHeuristic(graph.MinimumRemainingValues());
                endTime = System.currentTimeMillis();
                times[j] = endTime - startTime;
            }

            System.out.println(i + "," + Arrays.stream(times).average().getAsDouble());
        }
*/


    }
}