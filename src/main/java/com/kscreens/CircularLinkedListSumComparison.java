package com.kscreens;

import java.math.BigInteger;
import java.util.concurrent.TimeUnit;

public class CircularLinkedListSumComparison
{

    public static void main(String[] args) {
        // Create CircularLinkedList of 1000 BigInteger numbers
        CircularLinkedList<BigInteger> circularLinkedList = new CircularLinkedList<>();
        for (int i = 0; i < 100000; i++) {
            circularLinkedList.add(BigInteger.valueOf(i));
        }

        // Measure time taken to sum using stream
        long startTimeStream = System.nanoTime();
        BigInteger sumStream = circularLinkedList.stream()
                .reduce(BigInteger.ZERO, BigInteger::add);
        long endTimeStream = System.nanoTime();

        // Measure time taken to sum using parallel stream
        long startTimeParallelStream = System.nanoTime();
        BigInteger sumParallelStream = circularLinkedList.parallelStream()
                .reduce(BigInteger.ZERO, BigInteger::add);
        long endTimeParallelStream = System.nanoTime();

        // Calculate time taken in milliseconds
        long timeStream = TimeUnit.NANOSECONDS.toMillis(endTimeStream - startTimeStream);
        long timeParallelStream = TimeUnit.NANOSECONDS.toMillis(endTimeParallelStream - startTimeParallelStream);

        // Display results
        System.out.println("Sum using Stream: " + sumStream);
        System.out.println("Time taken using Stream: " + timeStream + " milliseconds");
        System.out.println("Sum using ParallelStream: " + sumParallelStream);
        System.out.println("Time taken using ParallelStream: " + timeParallelStream + " milliseconds");
    }
}
