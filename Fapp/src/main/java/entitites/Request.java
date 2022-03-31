package entitites;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public abstract class Request {

  private String assign; //Employee that is assigned the task (First name, Last name)
  private int empID; //ID of the employee that requested the task (5 Digit int)
  private String nID; //nodeID is the key for the location in which the request is directed to (Check Locations.csv for examples)
  private String sts; //Status of the request (In Progress or Done)
  private String reqType; //Type of request made, Patient Bed (PTBD), Recliner (RECL), X-Ray Machine (XRAY), Infusion Pump (IPMP)

  public Request(String assign, int empID, String nID, String sts, String reqType){
    this.assign = assign;
    this.empID = empID;
    this.nID = nID;
    this.sts = sts;
    this.reqType = reqType;
  }

}
