package edu.wpi.cs3733.D22.teamF;

public class CreditAPI {
    String apiIncorporated;
    String apiVersion;
    String apiCreator;

    public CreditAPI(String apiIncorporated, String apiVersion, String apiCreator) {
        this.apiIncorporated = apiIncorporated;
        this.apiVersion = apiVersion;
        this.apiCreator = apiCreator;
    }

    public String getApiIncorporated() {
        return apiIncorporated;
    }

    public void setApiIncorporated(String apiIncorporated) {
        this.apiIncorporated = apiIncorporated;
    }

    public String getApiVersion() {
        return apiVersion;
    }

    public void setApiVersion(String apiVersion) {
        this.apiVersion = apiVersion;
    }

    public String getApiCreator() {
        return apiCreator;
    }

    public void setApiCreator(String apiCreator) {
        this.apiCreator = apiCreator;
    }
}
