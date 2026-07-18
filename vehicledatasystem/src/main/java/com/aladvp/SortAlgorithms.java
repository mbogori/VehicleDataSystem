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

    /**
     * Question 3: Sorts an array into ascending order using Quick Sort.
     *
     * This public method starts the recursive sorting process.
     *
     * @param data       the array to sort
     * @param comparator the comparison rule used by the algorithm
     */
    public static <T> void quickSort(
            T[] data,
            Comparator<? super T> comparator) {

        /*
         * An empty array or an array containing one item is already sorted.
         */
        if (data.length <= 1) {
            return;
        }

        // Start by processing the complete array
        quickSort(
                data,
                0,
                data.length - 1,
                comparator);
    }

    /**
     * Recursively sorts the section between the low and high indexes.
     */
    private static <T> void quickSort(
            T[] data,
            int low,
            int high,
            Comparator<? super T> comparator) {

        /*
         * Base case: a section containing zero or one item
         * does not require any further sorting.
         */
        if (low >= high) {
            return;
        }

        /*
         * The left and right indexes scan towards each other
         * while partitioning the current section.
         */
        int left = low;
        int right = high;

        /*
         * Select the record at the middle index as the pivot.
         * The pivot is the reference point used to divide the section.
         */
        T pivot = data[low + (high - low) / 2];

        /*
         * Partition the current section by moving the two indexes
         * towards each other until they cross.
         */
        while (left <= right) {

            /*
             * Move left past records that already belong
             * on the smaller side of the pivot.
             */
            while (comparator.compare(data[left], pivot) < 0) {
                left++;
            }

            /*
             * Move right past records that already belong
             * on the larger side of the pivot.
             */
            while (comparator.compare(data[right], pivot) > 0) {
                right--;
            }

            /*
             * If the indexes have not crossed, the two records
             * are on the wrong sides and should exchange positions.
             */
            if (left <= right) {
                swap(data, left, right);

                /*
                 * Move both indexes after the swap. This also prevents
                 * an infinite loop when a record equals the pivot.
                 */
                left++;
                right--;
            }
        }
        /*
         * Recursively sort the remaining left section.
         * This section extends from low to right.
         */
        if (low < right) {
            quickSort(data, low, right, comparator);
        }

        /*
         * Recursively sort the remaining right section.
         * This section extends from left to high.
         */
        if (left < high) {
            quickSort(data, left, high, comparator);
        }
    }







    
}