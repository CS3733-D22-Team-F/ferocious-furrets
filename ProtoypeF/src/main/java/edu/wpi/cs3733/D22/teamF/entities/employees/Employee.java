package edu.wpi.cs3733.D22.teamF.entities.employees;

public abstract class Employee {
  private String position;
  private String employeeID;
  private String name;
  private String status;

  public Employee(String employeeID, String name, String position, String status) {
    this.employeeID = employeeID;
    this.name = name;
    this.position = position;
    this.status = status;
  }
}
