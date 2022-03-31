package entitites.request;

public class patientRequest extends DeliveryRequest{

    public patientRequest(String assign, int empID, String nID, String sts, String reqType, String deliveryID, String deliveryType){
        super(assign, empID, nID, sts, reqType, deliveryID, deliveryType);
    }
}
