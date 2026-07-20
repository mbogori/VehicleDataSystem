package com.aladvp;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

/**
 * Contains the Binary Search implementation used to search
 * a sorted collection of vehicle records.
 *
 * Question 4 requires the search to return every matching
 * record when duplicate values are present.
 */
public final class BinarySearch {

    /*
     * Prevents objects of this utility class from being created.
     * The class will contain only static search methods.
     */
    private BinarySearch() {
    }

    /**
     * Searches a sorted list and returns every record whose selected
     * field matches the supplied search value.
     *
     * @param sortedData  the records sorted by the field being searched
     * @param searchValue the value that the user wants to find
     * @param fieldReader extracts the selected field from one record
     * @param <T>         the type of record stored in the list
     * @return a list containing all matching records
     */
    public static <T> List<T> searchAll(
            List<T> sortedData,
            String searchValue,
            Function<T, String> fieldReader) {

        // This list will eventually contain every matching record
        List<T> matches = new ArrayList<>();

        // Binary Search begins with the entire list as its search area
        int low = 0;
        int high = sortedData.size() - 1;

        // Stores the position of a match when one is found
        int matchingIndex = -1;

        /*
         * Continue while there is still a valid section of the list
         * left to search.
         */
        while (low <= high) {

            /*
             * Find the middle position (without using (low + high) / 2,
             * which could overflow with extremely large index values).
             */
            int middle = low + (high - low) / 2;

            // Read the selected field from the record in the middle
            String middleValue = fieldReader.apply(sortedData.get(middle));

            /*
             * Compare without considering uppercase and lowercase letters.
             *
             * Negative: middleValue comes before searchValue
             * Zero: the two values match
             * Positive: middleValue comes after searchValue
             */
            int comparison = middleValue.compareToIgnoreCase(searchValue);

            if (comparison == 0) {

                // A match has been found, so remember its position
                matchingIndex = middle;
                break;

            } else if (comparison < 0) {

                /*
                 * The required value must be in the upper half,
                 * so discard the middle and lower positions.
                 */
                low = middle + 1;

            } else {

                /*
                 * The required value must be in the lower half,
                 * so discard the middle and upper positions.
                 */
                high = middle - 1;
            }
        }

        /*
         * If matchingIndex is still -1, Binary Search finished
         * without finding the requested value.
         */
        if (matchingIndex == -1) {
            return matches;
        }

        /*
         * Binary Search may find any one of several duplicate values.
         * Move left until the first matching record is reached.
         */
        int firstMatchingIndex = matchingIndex;

        while (firstMatchingIndex > 0) {

            String previousValue = fieldReader.apply(
                    sortedData.get(firstMatchingIndex - 1));

            if (!previousValue.equalsIgnoreCase(searchValue)) {
                break;
            }

            firstMatchingIndex--;
        }

        /*
         * Starting with the first match, move right and collect every
         * consecutive record containing the same selected-field value.
         */
        int currentIndex = firstMatchingIndex;

        while (currentIndex < sortedData.size()) {

            T currentRecord = sortedData.get(currentIndex);
            String currentValue = fieldReader.apply(currentRecord);

            if (!currentValue.equalsIgnoreCase(searchValue)) {
                break;
            }

            matches.add(currentRecord);
            currentIndex++;
        }

        return matches;
    }

}
