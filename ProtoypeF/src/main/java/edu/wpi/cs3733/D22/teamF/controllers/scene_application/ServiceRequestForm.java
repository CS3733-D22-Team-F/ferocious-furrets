package edu.wpi.cs3733.D22.teamF.controllers.scene_application;

/** Abstract for a service request for class */
public abstract class ServiceRequestForm {

  /** Assign an employee to a service request */
  public void assignName() {}

  /** Submits request to be saved in the database */
  public void submit() {}

  /** Clears text fields in form */
  public void reset() {}
}
