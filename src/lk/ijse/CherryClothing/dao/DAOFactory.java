package lk.ijse.CherryClothing.dao;

import lk.ijse.CherryClothing.dao.impl.*;

public class DAOFactory {
    private static DAOFactory daoFactory;

    private DAOFactory() {
    }

    public static DAOFactory getDaoFactory() {
        return (daoFactory == null) ? daoFactory = new DAOFactory() : daoFactory;
    }

    public enum DAOTypes {
        CUSTOMER,ITEM,ORDER,ORDER_DETAILS,QUERY_DAO,EMPLOYEE,SUPPLIER
    }

    public SuperDAO getDAO(DAOTypes types) {
        switch (types) {
            case CUSTOMER:
                return new CustomerDAOImpl();
            case ITEM:
                return new ItemDAOImpl();
            case ORDER:
                return new OrderDAOImpl();
            case ORDER_DETAILS:
                return new OrderDetailDAOImpl();
            case QUERY_DAO:
                return new QuerryDAOImpl();
            case EMPLOYEE:
                return new EmployeeDAOImpl();
            case SUPPLIER:
                return new SupplierDAOImpl();
            default:
                return null;
        }
    }
}
