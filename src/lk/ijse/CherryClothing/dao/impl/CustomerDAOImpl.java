package lk.ijse.CherryClothing.dao.impl;

import lk.ijse.CherryClothing.dao.custom.CustomerDAO;
import lk.ijse.CherryClothing.db.DBConnection;
import lk.ijse.CherryClothing.dto.CustomerDTO;
import lk.ijse.CherryClothing.entity.Customer;
import lk.ijse.CherryClothing.util.CrudUtil;

import java.sql.*;
import java.util.ArrayList;

public class CustomerDAOImpl implements CustomerDAO {
    @Override
    public ArrayList<Customer> getAll() throws SQLException, ClassNotFoundException {
        ArrayList<Customer> allCustomers = new ArrayList<>();

        ResultSet rst = CrudUtil.execute("SELECT * FROM Customer");

        while (rst.next()) {
            Customer customer = new Customer(rst.getString("cus_id"), rst.getString("cus_name"), rst.getString("address"),rst.getString("contact"));
            allCustomers.add(customer);
        }
        return allCustomers;
    }
    @Override
    public boolean add(Customer entity) throws SQLException, ClassNotFoundException {

        return CrudUtil.execute("INSERT INTO Customer (cus_id,cus_name, address,contact) VALUES (?,?,?,?)",entity.getId(),entity.getName(),entity.getAddress(),entity.getContact());

    }
    @Override
    public boolean update(Customer entity) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("UPDATE Customer SET cus_name=?, address=?, contact=? WHERE cus_id=?", entity.getName(), entity.getAddress(),entity.getContact(), entity.getId());
    }

    @Override
    public boolean exist(String id) throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.execute("SELECT cus_id FROM Customer WHERE cus_id=?",id);
        return rst.next();
    }
    @Override
    public String generateNewId() throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.execute("SELECT cus_id FROM Customer ORDER BY cus_id DESC LIMIT 1;");
        if (rst.next()) {
            String id = rst.getString("cus_id");
            int newCustomerId = Integer.parseInt(id.replace("C", "")) + 1;
            return String.format("C0%03d", newCustomerId);
        } else {
            return "C0001";
        }
    }
    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("DELETE FROM Customer WHERE cus_id=?",id);

    }
    @Override
    public Customer search(String id) throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.execute("SELECT * FROM Customer WHERE cus_id=?",id+ "");
        rst.next();
        return new Customer(id + "", rst.getString("cus_name"), rst.getString("address"), rst.getString("contact"));

    }
}
