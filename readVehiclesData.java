import java.io.File;
import java.util.*;

public class readVehiclesData {

    public static void main(String[] args) throws Exception {
        //parsing and reading the CSV file data into the object array
        File directory = new File("./");
        String name = directory.getAbsolutePath() + "//vehicles.csv";
        Vehicles[] vehicle;
        try (Scanner scanner = new Scanner(new File(name))) {
            vehicle = new Vehicles[10000];
            // this will just print the header in CSV file
            scanner.nextLine();
            int i = 0;
            String sGetData;
            while (scanner.hasNextLine()) {
                sGetData = scanner.nextLine();
                String[] data = sGetData.split(",");
                vehicle[i++] = new Vehicles(Integer.parseInt(data[0]), data[1], data[2], data[3], Integer.parseInt(data[4]), Long.parseLong(data[5]));
            }
            //closes the scanner
        }

        // we can print details due to overridden toString method in the class below
        System.out.println(vehicle[0]);
        System.out.println(vehicle[1]);

        // we can compare objects based on their ID due to overridden CompareTo method in the class below
        System.out.println(vehicle[0] == vehicle[0]);
        System.out.println(vehicle[0] == vehicle[1]);
    }

}

class Vehicles implements Comparable<Object> {
    private int iId;
    private String sName;
    private String sFuel;
    private String sLocation;
    private int iPostcode;
    private long lValue;

    // constructor
    public Vehicles(int iInId, String sInName, String sInFuel, String sInLocation, int iInPostcode, long lInValue) {
        this.iId = iInId;
        this.sName = sInName;
        this.sFuel = sInFuel;
        this.sLocation = sInLocation;
        this.iPostcode = iInPostcode;
        this.lValue = lInValue;
    }

    // the objects can be compared when sorting/searching
    @Override
    public int compareTo(Object obj) {

        /*
		Edit this section so it compares the appropriate
		column you wish to sort by
         */
        Vehicles myVehicles = (Vehicles) obj;
        return iId - (myVehicles.getiId());
    }

    @Override
    public String toString() {
        return "Vehicle [ID= " + iId + ", Name= " + sName + ", Fuel= "
                + sFuel + ", Location= " + sLocation + ", Postcode= "
                + iPostcode + ", Value= " + lValue + "]";
    }

    // getters and setters

    public int getiId() {
        return iId;
    }

    public void setiId(int iId) {
        this.iId = iId;
    }

    public String getsName() {
        return sName;
    }

    public void setsName(String sName) {
        this.sName = sName;
    }

    public String getsFuel() {
        return sFuel;
    }

    public void setsFuel(String sFuel) {
        this.sFuel = sFuel;
    }

    public String getsLocation() {
        return sLocation;
    }

    public void setsLocation(String sLocation) {
        this.sLocation = sLocation;
    }

    public int getiPostcode() {
        return iPostcode;
    }

    public void setiPostcode(int iPostcode) {
        this.iPostcode = iPostcode;
    }

    public long getlValue() {
        return lValue;
    }

    public void setlValue(long lValue) {
        this.lValue = lValue;
    }

}
