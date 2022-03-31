package entitites;

public abstract class Request {
  private String Employee;
  private String nodeID;
  private boolean status;

  private requestType request;

  public enum requestType {
    RELIGIOUS,
    SANITIZATION,
    LANGUAGEINTERNSHIP,
    LAUNDRY
  }
}
