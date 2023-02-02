package lk.ijse.CherryClothing.dao.impl;

import lk.ijse.CherryClothing.dao.custom.OrderDetailDAO;
import lk.ijse.CherryClothing.entity.OrderDetail;
import lk.ijse.CherryClothing.util.CrudUtil;

import java.sql.SQLException;
import java.util.ArrayList;

public class OrderDetailDAOImpl implements OrderDetailDAO {
    @Override
    public ArrayList<OrderDetail> getAll() throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public boolean add(OrderDetail entity) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("INSERT INTO OrderDetail (order_id, item_id, qty, unitPrice) VALUES (?,?,?,?)", entity.getOrderId(), entity.getItemId(), entity.getQty(), entity.getUnitPrice());
    }

    @Override
    public boolean update(OrderDetail entity) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean exist(String id) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public String generateNewId() throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public OrderDetail search(String id) throws SQLException, ClassNotFoundException {
        return null;
    }
}
