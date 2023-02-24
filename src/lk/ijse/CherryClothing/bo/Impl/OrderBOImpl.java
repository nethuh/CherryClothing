package lk.ijse.CherryClothing.bo.Impl;

import lk.ijse.CherryClothing.bo.Custom.CustomerBO;
import lk.ijse.CherryClothing.bo.Custom.OrderBO;
import lk.ijse.CherryClothing.dao.DAOFactory;
import lk.ijse.CherryClothing.dao.custom.CustomerDAO;
import lk.ijse.CherryClothing.dao.custom.OrderDAO;
import lk.ijse.CherryClothing.dto.CustomerDTO;
import lk.ijse.CherryClothing.dto.OrderDTO;
import lk.ijse.CherryClothing.dto.SupplierDTO;
import lk.ijse.CherryClothing.entity.Customer;
import lk.ijse.CherryClothing.entity.Order;

import java.sql.SQLException;
import java.util.ArrayList;

public class OrderBOImpl implements OrderBO {

    OrderDAO orderDAO = (OrderDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.ORDER);

    public ArrayList<OrderDTO> getAllOrder() throws SQLException, ClassNotFoundException {
        ArrayList<OrderDTO> allCustomers = new ArrayList<>();
        ArrayList<Order> all = orderDAO.getAll();
        for (Order c : all) {
            allCustomers.add(new OrderDTO(c.getOrderId(), c.getDate(), c.getCustomerId()));
        }
        return allCustomers;
    }
}
