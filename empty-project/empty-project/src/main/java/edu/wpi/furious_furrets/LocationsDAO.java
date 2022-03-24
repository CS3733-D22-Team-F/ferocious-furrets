package edu.wpi.furious_furrets;

import java.sql.*;

public class LocationsDAO {

  public void testConnection() throws SQLException {

    System.out.println("Database Setup");

    try {
      Class.forName("org.apache.derby.iapi.jdbc.AutoloadedDriver");
    } catch (ClassNotFoundException e) {
      System.out.println("Driver not found");
      e.printStackTrace();
    }

    System.out.println("Driver registered");
    Connection connection = null;

    try {
      connection = DriverManager.getConnection("jdbc:derby:C:/Users/radcl/Locations");
      Statement stm = connection.createStatement();
      // stm.execute("INSERT into Students (sid, name, grade, classnum) values (1, 'Owen Radcliffe',
      // 11, 13)");
      // stm.execute("INSERT into Students values (2, 'Jack Hanlon', 10, 13)");
      // stm.execute("insert into Students values (3, 'Raffi Alexander', 11, 15)");
      // stm.execute("insert into Students values (4, 'Cole Parks', 11, 14)");
      // String q1 = "Select * from Students where grade = 11";
      // ResultSet rset1 = stm.executeQuery(q1);
      // while (rset1.next()) {
      //  System.out.println(rset1.getString("name"));
      // }
      // rset1.close();
      // stm.close();
    } catch (SQLException e) {
      System.out.println("Connection failed");
      e.printStackTrace();
      return;
    }

    System.out.println("Derby connection established");
    connection.close();
  }
}
