package lk.ijse.CherryClothing.bo.Custom;

import lk.ijse.CherryClothing.bo.SuperBO;
import lk.ijse.CherryClothing.dao.custom.CustomerDAO;
import lk.ijse.CherryClothing.dto.CustomerDTO;
import lk.ijse.CherryClothing.entity.Customer;

import java.sql.SQLException;
import java.util.ArrayList;

public interface CustomerBO extends SuperBO {

    public ArrayList<CustomerDTO> getAllCustomer() throws SQLException, ClassNotFoundException;
    public boolean addCustomer(CustomerDTO dto) throws SQLException, ClassNotFoundException;
    public boolean updateCustomer(CustomerDTO dto) throws SQLException, ClassNotFoundException;
    public boolean existCustomer(String id) throws SQLException, ClassNotFoundException ;
    public boolean deleteCustomer(String id) throws SQLException, ClassNotFoundException;
    public String generateNewCustomerID () throws SQLException, ClassNotFoundException;
    public CustomerDTO searchCustomer(String id) throws SQLException, ClassNotFoundException;
}

