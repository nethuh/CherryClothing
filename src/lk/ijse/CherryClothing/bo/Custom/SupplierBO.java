package lk.ijse.CherryClothing.bo.Custom;

import lk.ijse.CherryClothing.bo.SuperBO;
import lk.ijse.CherryClothing.dto.CustomerDTO;
import lk.ijse.CherryClothing.dto.SupplierDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface SupplierBO  extends SuperBO {

    public ArrayList<SupplierDTO> getAllSupplier() throws SQLException, ClassNotFoundException;
    public boolean addSupplier(SupplierDTO dto) throws SQLException, ClassNotFoundException;
    public boolean updateSupplier(SupplierDTO dto) throws SQLException, ClassNotFoundException;
    public boolean existSupplier(String id) throws SQLException, ClassNotFoundException ;
    public boolean deleteSupplier(String id) throws SQLException, ClassNotFoundException;
    public String generateNewSupplierID () throws SQLException, ClassNotFoundException;
    public SupplierDTO searchSupplier(String id) throws SQLException, ClassNotFoundException;

}
