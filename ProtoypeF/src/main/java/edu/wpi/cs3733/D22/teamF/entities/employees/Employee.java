package edu.wpi.cs3733.D22.teamF.entities.employees;

public class Employee {
  public String employeeID;
  public String firstName;
  public String lastName;
  public String salary;

  public Employee(String employeeID, String firstName, String lastName, String salary) {
    this.employeeID = employeeID;
    this.firstName = firstName;
    this.lastName = lastName;
    this.salary = salary;
  }
}
