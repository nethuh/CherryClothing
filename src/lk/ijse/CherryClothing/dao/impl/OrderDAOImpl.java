package lk.ijse.CherryClothing.dao.impl;

import lk.ijse.CherryClothing.dao.custom.OrderDAO;
import lk.ijse.CherryClothing.entity.Order;
import lk.ijse.CherryClothing.util.CrudUtil;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class OrderDAOImpl implements OrderDAO {

    @Override
    public ArrayList<Order> getAll() throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public boolean add(Order entity) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("INSERT INTO `Orders` (order_id, date, cus_id) VALUES (?,?,?)",entity.getOrderId(), Date.valueOf(entity.getDate()),entity.getCustomerId());
    }

    @Override
    public boolean update(Order entity) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean exist(String id) throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.execute("SELECT order_id FROM `Orders` WHERE order_id=?",id);
        return rst.next();
    }

    @Override
    public String generateNewId() throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.execute("SELECT order_id FROM `Orders` ORDER BY order_id DESC LIMIT 1;");
        return rst.next() ? String.format("O0%03d", (Integer.parseInt(rst.getString("order_id").replace("O", "")) + 1)) : "O0001";
    }

    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public Order search(String id) throws SQLException, ClassNotFoundException {
        return null;
    }
}
