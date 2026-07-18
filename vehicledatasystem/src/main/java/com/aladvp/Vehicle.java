package com.aladvp;

/**
 * Represents one record from vehicles.csv.
 *
 * Each Vehicle object stores the six fields found in one CSV row:
 * ID, Name, Fuel, Location, Postcode and Value.
 */
public class Vehicle {

    // Fields corresponding to the six CSV columns
    private int id;
    private String name;
    private String fuel;
    private String location;
    private int postcode;
    private long value;

    // Constructor used to create a Vehicle object from one CSV record
    public Vehicle(int id, String name, String fuel,
            String location, int postcode, long value) {

        this.id = id;
        this.name = name;
        this.fuel = fuel;
        this.location = location;
        this.postcode = postcode;
        this.value = value;
    }

    // Getter methods provide read access to the private fields

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getFuel() {
        return fuel;
    }

    public String getLocation() {
        return location;
    }

    public int getPostcode() {
        return postcode;
    }

    public long getValue() {
        return value;
    }

    // Setter methods provide controlled write access to the private fields

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setFuel(String fuel) {
        this.fuel = fuel;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setPostcode(int postcode) {
        this.postcode = postcode;
    }

    public void setValue(long value) {
        this.value = value;
    }

    // Returns all the vehicle details as one readable String
    @Override
    public String toString() {
        return "Vehicle [ID=" + id
                + ", Name=" + name
                + ", Fuel=" + fuel
                + ", Location=" + location
                + ", Postcode=" + postcode
                + ", Value=" + value
                + "]";
    }

}
