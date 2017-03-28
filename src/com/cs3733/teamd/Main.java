package com.cs3733.teamd;

import java.sql.*;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        System.out.println("-------Embedded Java DB Connection Testing --------");
        try {
            Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
        } catch (ClassNotFoundException e) {
            System.out.println("Java DB Driver not found. Add the classpath to your module.");
            System.out.println("For IntelliJ do the following:");
            System.out.println("File | Project Structure, Modules, Dependency tab");
            System.out.println("Add by clicking on the green plus icon on the right of the window");
            System.out.println("Select JARs or directories. Go to the folder where the Java JDK is installed");
            System.out.println("Select the folder java/jdk1.8.xxx/db/lib where xxx is the version.");
            System.out.println("Click OK, compile the code and run it.");
            e.printStackTrace();
            return;
        }

        System.out.println("Java DB driver registered!");
        Connection connection = null;

        try {
            // substitute your database name for myDB
            connection = DriverManager.getConnection("jdbc:derby:myDB;create=true");
        } catch (SQLException e) {
            System.out.println("Connection failed. Check output console.");
            e.printStackTrace();
            return;
        }


        String dropServicesSql = "DROP TABLE SERVICES";
        String createServicesSql = "CREATE TABLE SERVICES"
                + "(s_id INTEGER GENERATED ALWAYS AS IDENTITY" +
                "        (START WITH 1, INCREMENT BY 1),"
                + "name VARCHAR(50),"
                + "floor INTEGER,"
                + "room VARCHAR(20))";

        String insertServiceSql = "INSERT INTO SERVICES " +
                " (name, floor, room) VALUES(" +
                "'Test Service', 4, '420C')";

        String queryServiceSql = "SELECT * FROM SERVICES";

        try {
            List<HospitalService> services = HospitalServiceDatabaseProvider.loadHospitalServicesFromDb(connection);

            for(HospitalService s: services) {
                System.out.println("Name: "+s.getName()
                                    +"\tFloor: "+s.getFloor()
                                    +"\tRoom: "+s.getRoom());
            }

            HospitalService s = new HospitalService("Test Doctor", 4, "421C");
            //HospitalServiceDatabaseProvider.addHospitalServiceToDb(s,connection);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("Java DB connection established!");
	// write your code here
    }
}
