package com.aladvp;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Loads and processes the records contained in vehicles.csv.
 *
 * Student number: 25112651
 * Allocated dataset: vehicles.csv
 * Required sorting order: Value first, then ID when Values match.
 */
public class VehicleDataSystem {

    /**
     * Comparison rule used by Bubble Sort and Quick Sort.
     *
     * Vehicles are compared by Value first. If the Values are equal,
     * ID is used as the secondary sorting field.
     */
    private static final Comparator<Vehicle> VALUE_THEN_ID = (firstVehicle, secondVehicle) -> {

        // Primary comparison: column 6, Value
        int valueComparison = Long.compare(
                firstVehicle.getValue(),
                secondVehicle.getValue());

        // Return immediately when the Values are different
        if (valueComparison != 0) {
            return valueComparison;
        }

        // Tie-breaker: column 1, ID
        return Integer.compare(
                firstVehicle.getId(),
                secondVehicle.getId());
    };

    public static void main(String[] args) {

        Path csvPath = Path.of("vehicles.csv");

        System.out.println("Looking for the CSV file at:");
        System.out.println(csvPath.toAbsolutePath());

        try {
            Vehicle[] vehicles = loadVehicles(csvPath);

            System.out.println("\nRecords loaded: " + vehicles.length);

            if (vehicles.length > 0) {
                System.out.println("\nFirst vehicle:");
                System.out.println(vehicles[0]);

                System.out.println("\nLast vehicle:");
                System.out.println(vehicles[vehicles.length - 1]);
            }
            /*
             * Question 1: Create a copy so the original unsorted data
             * remains available for later performance experiments.
             */
            Vehicle[] bubbleSortedVehicles = Arrays.copyOf(
                    vehicles,
                    vehicles.length);

            // Sort the copied array by Value, then ID
            SortAlgorithms.bubbleSort(
                    bubbleSortedVehicles,
                    VALUE_THEN_ID);

            // Verify that all records were correctly ordered
            boolean sortedCorrectly = SortAlgorithms.isSorted(
                    bubbleSortedVehicles,
                    VALUE_THEN_ID);

            System.out.println(
                    "\nBubble Sort completed correctly: " + sortedCorrectly);

            System.out.println("\nFirst three records after Bubble Sort:");

            int recordsToDisplay = Math.min(3, bubbleSortedVehicles.length);

            for (int index = 0; index < recordsToDisplay; index++) {
                System.out.println(bubbleSortedVehicles[index]);
            }

            /*
             * Question 2: Prepare the Bubble Sort performance experiment.
             * The original unsorted data is supplied to the experiment method.
             */
            runBubbleSortExperiment(vehicles);

        } catch (IOException exception) {
            System.err.println(
                    "The vehicle data could not be loaded: "
                            + exception.getMessage());
        }

    }

    /**
     * Loads all valid records from the Vehicles.csv file.
     *
     * @param csvPath the location of vehicles.csv
     * @return an array containing the loaded Vehicle objects
     * @throws IOException if the file cannot be read or contains invalid data
     */
    public static Vehicle[] loadVehicles(Path csvPath) throws IOException {

        List<Vehicle> loadedVehicles = new ArrayList<>();

        try (BufferedReader reader = Files.newBufferedReader(csvPath)) {

            // Read and check the header
            String header = reader.readLine();

            if (header == null) {
                throw new IOException("The CSV file is empty.");
            }

            String line;
            int lineNumber = 1;

            while ((line = reader.readLine()) != null) {

                lineNumber++;

                if (line.isBlank()) {
                    continue;
                }

                String[] fields = line.split(",", -1);

                if (fields.length != 6) {
                    throw new IOException(
                            "Invalid CSV record at line " + lineNumber);
                }

                try {
                    int id = Integer.parseInt(fields[0].trim());
                    String name = fields[1].trim();
                    String fuel = fields[2].trim();
                    String location = fields[3].trim();
                    int postcode = Integer.parseInt(fields[4].trim());
                    long value = Long.parseLong(fields[5].trim());

                    Vehicle vehicle = new Vehicle(
                            id,
                            name,
                            fuel,
                            location,
                            postcode,
                            value);

                    loadedVehicles.add(vehicle);

                } catch (NumberFormatException exception) {
                    throw new IOException(
                            "Invalid number at CSV line " + lineNumber,
                            exception);
                }
            }
        }

        // Convert the growing ArrayList into a Vehicle array
        return loadedVehicles.toArray(new Vehicle[0]);

    }

    /**
     * Question 2: Prepares the Bubble Sort performance experiment.
     *
     * Each input size is tested three times. A fresh copy of the original
     * unsorted data is created before every run.
     *
     * @param originalVehicles the original unsorted Vehicle array
     */
    private static void runBubbleSortExperiment(
            Vehicle[] originalVehicles) {

        // Input sizes required by the CA1 brief
        int[] inputSizes = { 10, 100, 1000, 5000, 10000 };

        // Each input size must be tested at least three times
        int numberOfRuns = 3;

        System.out.println("\nBUBBLE SORT PERFORMANCE EXPERIMENT");

        // Process each required input size
        for (int inputSize : inputSizes) {

            /*
             * Prevent an invalid copy request if the supplied dataset
             * contains fewer records than the requested input size.
             */
            if (inputSize > originalVehicles.length) {
                System.out.println(
                        "Skipping " + inputSize
                                + " records: insufficient data.");
                continue;
            }

            System.out.println("\nInput size: " + inputSize);

            // Accumulates the elapsed time from all three measured runs
            long totalElapsedTime = 0L;

            // Perform three independent measured runs
            for (int run = 1; run <= numberOfRuns; run++) {

                /*
                 * Create a fresh unsorted copy before starting the timer.
                 * Copying time is not part of the Bubble Sort measurement.
                 */
                Vehicle[] testData = Arrays.copyOf(
                        originalVehicles,
                        inputSize);

                // Record the time immediately before Bubble Sort starts
                long startTime = System.nanoTime();

                SortAlgorithms.bubbleSort(
                        testData,
                        VALUE_THEN_ID);

                // Record the time immediately after Bubble Sort finishes
                long endTime = System.nanoTime();

                // Calculate the duration of this run
                long elapsedTime = endTime - startTime;

                totalElapsedTime += elapsedTime;

                /*
                 * Verify the result after stopping the timer so that the
                 * verification time is not included in the measurement.
                 */
                boolean sortedCorrectly = SortAlgorithms.isSorted(
                        testData,
                        VALUE_THEN_ID);

                if (!sortedCorrectly) {
                    throw new IllegalStateException(
                            "Bubble Sort failed for input size " + inputSize);
                }

                System.out.printf(
                        "Run %d: %d ns (%.6f ms)%n",
                        run,
                        elapsedTime,
                        elapsedTime / 1_000_000.0);
            }

            // Calculate and display the average time for this input size
            double averageElapsedTime = totalElapsedTime / (double) numberOfRuns;

            System.out.printf(
                    "Average time for %d records: %.0f ns (%.6f ms)%n",
                    inputSize,
                    averageElapsedTime,
                    averageElapsedTime / 1_000_000.0);

        }

    }

}