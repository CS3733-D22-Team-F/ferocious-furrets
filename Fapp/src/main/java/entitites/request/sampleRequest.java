package entitites.request;

public abstract class sampleRequest extends MedicalRequest{

    protected String sampleType;

    public sampleRequest(String assign, int empID, String nID, String sts, String reqType, String equipID, String equipType, String sampleType){
        super(assign, empID, nID, sts, reqType, equipID, equipType);
        this.sampleType = sampleType;
    }
}
