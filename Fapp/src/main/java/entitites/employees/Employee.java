package entitites.employees;

public abstract class Employee {
  private String position;
  private String employeeID;

  public Employee(String position, String employeeID) {
    this.position = position;
    this.employeeID = employeeID;
  }
}
