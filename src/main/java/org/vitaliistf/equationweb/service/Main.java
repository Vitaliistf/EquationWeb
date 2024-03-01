package org.vitaliistf.equationweb.service;

import jakarta.ejb.Stateless;

import java.util.Arrays;
import java.util.OptionalDouble;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;

@Stateless
public class Main {
    /**
     * Constant representing a numerical value (2.3) used in the mathematical function.
     */
    public static final double CONSTANT_A = 2.3;

    /**
     * A small constant used for comparison in the mathematical function.
     */
    public static final double EPSILON = 1e-4;


    /**
     * The run method that coordinates the execution of various operations on arrays of x and y values.
     * It calculates x and y arrays, finds the minimum and maximum elements, and prints relevant information.
     */
    private void run() {
        double start = 0.2;
        double end = 2.8;
        double step = 0.002;

        double[] xArray = createXArray(start, end, step);
        double[] yArray = createYArray(xArray);

        int minIndex = getMinElementIndex(yArray);
        int maxIndex = getMaxElementIndex(yArray);

        System.out.println("X values: " + Arrays.toString(xArray));
        System.out.println("Y values: " + Arrays.toString(yArray));
        System.out.println("Sum of Y values: " + calculateSum(yArray));
        System.out.println("Average of Y values: " + calculateAverage(yArray));
        printMinElement(minIndex, yArray);
        printMaxElement(maxIndex, yArray);
    }

    /**
     * Calculates a specific mathematical function based on the input value x.
     *
     * @param x Input value for the mathematical function.
     * @return Result of the mathematical function for the given x value.
     */
    public double calculateFunction(double x) {
        if (x <= 0.3 + EPSILON) {
            return 1.5 * CONSTANT_A * Math.pow(Math.cos(x), 2);
        } else if (x > 2.3 + EPSILON) {
            return 3 * CONSTANT_A * Math.tan(x);
        } else {
            return Math.pow((x - 2), 2) + 6 * CONSTANT_A;
        }
    }

    /**
     * Calculates the number of steps needed to generate x values within a specified range and step size.
     *
     * @param start Starting value for x.
     * @param end   Ending value for x.
     * @param step  Step size for x values.
     * @throws IllegalArgumentException if step is equal to 0.
     * @return Number of steps required to cover the specified range with the given step size.
     */
    public int calculateSteps(double start, double end, double step) {
        if (start > end) {
            return 0;
        } else if (step == 0) {
            throw new IllegalArgumentException("Step should not be equal to 0");
        }
        return (int) Math.round((end - start) / step) + 1;
    }

    /**
     * Creates an array of x values within a specified range and step size.
     *
     * @param start Starting value for x.
     * @param end   Ending value for x.
     * @param step  Step size for x values.
     * @return Array of x values.
     */
    public double[] createXArray(double start, double end, double step) {
        return IntStream
                .range(0, calculateSteps(start, end, step))
                .mapToDouble(i -> start + (i * step))
                .toArray();
    }

    /**
     * Creates an array of y values based on the corresponding x values.
     *
     * @param x Array of x values.
     * @return Array of y values.
     */
    public double[] createYArray(double[] x) {
        return DoubleStream
                .of(x)
                .map(this::calculateFunction)
                .toArray();
    }

    /**
     * Finds the index of the maximum element in the given array.
     *
     * @param arr Array of double values.
     * @return Index of the maximum element, or -1 if the array is null or empty.
     */
    public int getMaxElementIndex(double[] arr) {
        if (arr == null || arr.length == 0) {
            return -1;
        }

        int max = 0;
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] > arr[max]) {
                max = i;
            }
        }
        return max;
    }

    /**
     * Finds the index of the minimum element in the given array.
     *
     * @param arr Array of double values.
     * @return Index of the minimum element, or -1 if the array is null or empty.
     */
    public int getMinElementIndex(double[] arr) {
        if (arr == null || arr.length == 0) {
            return -1;
        }

        int min = 0;
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] < arr[min]) {
                min = i;
            }
        }
        return min;
    }

    /**
     * Calculates the sum of elements in the given array.
     *
     * @param arr Array of double values.
     * @return Sum of elements in the array.
     */
    public double calculateSum(double[] arr) {
        return DoubleStream
                .of(arr)
                .sum();
    }

    /**
     * Calculates the average of elements in the given array.
     *
     * @param arr Array of double values.
     * @throws IllegalArgumentException if array is empty or null.
     * @throws RuntimeException if average is not calculated.
     * @return Average of elements in the array, or 0.0 if the array is empty.
     */
    public double calculateAverage(double[] arr) {
        if (arr.length == 0) {
            throw new IllegalArgumentException("Array should have at least one element");
        }
        OptionalDouble result = DoubleStream
                .of(arr)
                .average();
        if (result.isPresent()) {
            return result.getAsDouble();
        } else {
            throw new RuntimeException("Average can't be calculated");
        }
    }

    /**
     * Prints information about the minimum element in the array.
     *
     * @param index Index of the minimum element.
     * @param arr   Array of double values.
     */
    public void printMinElement(int index, double[] arr) {
        System.out.printf("Min element is: %f with index %d\n", arr[index], index);
    }

    /**
     * Prints information about the maximum element in the array.
     *
     * @param index Index of the maximum element.
     * @param arr   Array of double values.
     */
    public void printMaxElement(int index, double[] arr) {
        System.out.printf("Max element is: %f with index %d\n", arr[index], index);
    }
}
