package lk.ijse.CherryClothing.bo;

import lk.ijse.CherryClothing.bo.Impl.*;

public class BOFactory {
    private static BOFactory boFactory;
    private BOFactory(){
    }
    public static BOFactory getBoFactory(){
        return (boFactory==null)? boFactory=new BOFactory() : boFactory;
    }

    public enum BOTypes{
        CUSTOMER,ITEM,PO,EMPLOYEE,SUPPLIER,ORDER
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
            case EMPLOYEE:
                return new EmployeeBOImpl();
            case SUPPLIER:
                return new SupplierBOImpl();
            case ORDER:
                return new OrderBOImpl();
            default:
                return null;
        }
    }
}
