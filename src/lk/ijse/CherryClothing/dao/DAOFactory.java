package lk.ijse.CherryClothing.dao;

import lk.ijse.CherryClothing.dao.custom.ItemDAO;
import lk.ijse.CherryClothing.dao.impl.CustomerDAOImpl;
import lk.ijse.CherryClothing.dao.impl.ItemDAOImpl;
import lk.ijse.CherryClothing.dao.impl.OrderDAOImpl;
import lk.ijse.CherryClothing.dao.impl.OrderDetailDAOImpl;

public class DAOFactory {
    private static DAOFactory daoFactory;

    private DAOFactory() {
    }

    public static DAOFactory getDaoFactory() {
        return (daoFactory == null) ? daoFactory = new DAOFactory() : daoFactory;
    }

    public enum DAOTypes {
        CUSTOMER,ITEM,ORDER,ORDER_DETAILS,QUERY_DAO
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
            default:
                return null;
        }
    }
}
