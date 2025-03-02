package main.java.util;

public class TimerDebug {

    private static long startTime;

    public static void start() {
        startTime = System.nanoTime();
    }

    public static void stop() {
        long endTime = System.nanoTime();
        long durationInMillis = (endTime - startTime) / 1_000_000;
        System.out.println("Execution time: " + durationInMillis + " ms");
    }

    // Alternative version that returns microseconds
    public static void stopMicros() {
        long endTime = System.nanoTime();
        long durationInMicros = (endTime - startTime) / 1_000;
        System.out.println("Execution time: " + durationInMicros + " μs");

    }
}
