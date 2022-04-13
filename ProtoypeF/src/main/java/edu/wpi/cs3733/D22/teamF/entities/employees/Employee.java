package edu.wpi.cs3733.D22.teamF.entities.employees;

public class Employee {
  private String employeeID;
  private String firstName;
  private String lastName;
  private String salary;

  public Employee(String employeeID, String firstName, String lastName, String salary) {
    this.employeeID = employeeID;
    this.firstName = firstName;
    this.lastName = lastName;
    this.salary = salary;
  }

  public String getEmployeeID() {
    return employeeID;
  }

  public void setEmployeeID(String employeeID) {
    this.employeeID = employeeID;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public String getSalary() {
    return salary;
  }

  public void setSalary(String salary) {
    this.salary = salary;
  }
}
