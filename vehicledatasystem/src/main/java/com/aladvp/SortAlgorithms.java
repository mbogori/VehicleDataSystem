package com.aladvp;

import java.util.Comparator;

/**
 * Contains the implementation of the sorting algorithms.
 *
 */
public final class SortAlgorithms {

    // Prevents objects of this utility class from being created.
    private SortAlgorithms() {
    }

    /**
     * Question 1: Sorts an array into ascending order using Bubble Sort.
     *
     * The supplied Comparator determines how two records are ordered.
     *
     * @param data       the array to sort
     * @param comparator the comparison rule used by the algorithm
     */
    public static <T> void bubbleSort(
            T[] data,
            Comparator<? super T> comparator) {

        // After every pass, the largest unsorted item reaches unsortedEnd
        for (int unsortedEnd = data.length - 1; unsortedEnd > 0; unsortedEnd--) {

            // Records whether this pass had to exchange any elements
            boolean swapped = false;

            // Compare each record with the record immediately beside it
            for (int index = 0; index < unsortedEnd; index++) {

                /*
                 * A positive result means the current record should come
                 * after the next record, so their positions must be exchanged.
                 */
                if (comparator.compare(data[index], data[index + 1]) > 0) {
                    swap(data, index, index + 1);
                    swapped = true;
                }
            }
            /*
             * If no records were exchanged, the array is sorted and the algorithm can stop.
             * This is an optimisation that reduces the number of passes when the array is
             * already sorted or nearly sorted.
             */

            if (!swapped) {
                return;

            }
        }

    } // End of bubbleSort

    /*
     * Exchanges the objects stored at two positions in an array.
     *
     * @param data the array containing the objects
     * 
     * @param firstIndex the position of the first object
     * 
     * @param secondIndex the position of the second object
     */
    private static <T> void swap(
            T[] data,
            int firstIndex,
            int secondIndex) {

        T temporary = data[firstIndex];
        data[firstIndex] = data[secondIndex];
        data[secondIndex] = temporary;
    }

    /**
     * Checks whether every element is in ascending order according
     * to the supplied Comparator.
     *
     * @param data       the array to check
     * @param comparator the comparison rule used to check the order
     * @return true if the complete array is sorted; otherwise false
     */
    public static <T> boolean isSorted(
            T[] data,
            Comparator<? super T> comparator) {

        // Start at index 1 so every item can be compared with its predecessor
        for (int index = 1; index < data.length; index++) {

            /*
             * A positive result means the previous item should come
             * after the current item, proving the array is not sorted.
             */
            if (comparator.compare(data[index - 1], data[index]) > 0) {
                return false;
            }
        }

        // No incorrectly ordered adjacent pair was found
        return true;
    }

    




}