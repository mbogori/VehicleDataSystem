package com.aladvp;

import java.util.Comparator;
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
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

        } catch (IOException exception) {
            System.err.println(
                    "The vehicle data could not be loaded: "
                            + exception.getMessage());
        }
    }

    /**
     * Loads all records from vehicles.csv into a Vehicle array.
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

}