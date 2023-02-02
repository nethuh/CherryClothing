package lk.ijse.CherryClothing.bo;

import lk.ijse.CherryClothing.bo.Impl.CustomerBOImpl;
import lk.ijse.CherryClothing.bo.Impl.ItemBOImpl;
import lk.ijse.CherryClothing.bo.Impl.PlaceOrderBOImpl;

public class BOFactory {
    private static BOFactory boFactory;
    private BOFactory(){
    }
    public static BOFactory getBoFactory(){
        return (boFactory==null)? boFactory=new BOFactory() : boFactory;
    }

    public enum BOTypes{
        CUSTOMER,ITEM,PO
    }

    //Object creation logic for BO objects
    public SuperBO getBO(BOTypes types) {
        switch (types) {
            case CUSTOMER:
                return new CustomerBOImpl();
            case ITEM:
                return new ItemBOImpl();
            case PO:
                return new PlaceOrderBOImpl();
            default:
                return null;
        }
    }
}
