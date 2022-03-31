package entitites.request;

import entitites.Request;

public class Delivery extends Request {
    private int deliveryID;
    private deliveryType delivery;

    public enum deliveryType
    {MEDECINEDELIVERY, MEALDELIVERY, GIFTDELIVERY, FLORALDELIVERY, PATIENT}

    public Delivery(int id, deliveryType d){
        this.deliveryID = id;
        this.delivery = d;
    }

}
