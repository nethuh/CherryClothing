package lk.ijse.CherryClothing.dao.impl;

import lk.ijse.CherryClothing.dao.custom.CustomerDAO;
import lk.ijse.CherryClothing.dao.custom.SupplierDAO;
import lk.ijse.CherryClothing.entity.Customer;
import lk.ijse.CherryClothing.entity.Supplier;
import lk.ijse.CherryClothing.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class SupplierDAOImpl implements SupplierDAO {
    @Override
    public ArrayList<Supplier> getAll() throws SQLException, ClassNotFoundException {
        ArrayList<Supplier> allSuppliers = new ArrayList<>();

        ResultSet rst = CrudUtil.execute("SELECT * FROM Supplier");

        while (rst.next()) {
            Supplier supplier = new Supplier(rst.getString("sup_id"), rst.getString("sup_name"), rst.getString("contact"));
            allSuppliers.add(supplier);
        }
        return allSuppliers;
    }
    @Override
    public boolean add(Supplier entity) throws SQLException, ClassNotFoundException {

        return CrudUtil.execute("INSERT INTO Supplier (sup_id,sup_name, contact) VALUES (?,?,?)",entity.getSupId(),entity.getName(),entity.getContact());

    }
    @Override
    public boolean update(Supplier entity) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("UPDATE Supplier SET sup_name=?, contact=? WHERE sup_id=?", entity.getName(),entity.getContact(), entity.getSupId());
    }

    @Override
    public boolean exist(String id) throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.execute("SELECT sup_id FROM Supplier WHERE sup_id=?",id);
        return rst.next();
    }
    @Override
    public String generateNewId() throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.execute("SELECT sup_id FROM Supplier ORDER BY sup_id DESC LIMIT 1;");
        if (rst.next()) {
            String id = rst.getString("sup_id");
            int newCustomerId = Integer.parseInt(id.replace("S", "")) + 1;
            return String.format("S0%03d", newCustomerId);
        } else {
            return "S0001";
        }
    }
    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("DELETE FROM Supplier WHERE sup_id=?",id);

    }
    @Override
    public Supplier search(String id) throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.execute("SELECT * FROM Supplier WHERE sup_id=?",id+ "");
        rst.next();
        return new Supplier(id + "", rst.getString("sup_name"), rst.getString("contact"));

    }
}
