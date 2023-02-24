package lk.ijse.CherryClothing.bo.Custom;

import lk.ijse.CherryClothing.bo.SuperBO;
import lk.ijse.CherryClothing.dto.EmployeeDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface EmployeeBO extends SuperBO {
    public ArrayList<EmployeeDTO> getAllEmployees() throws SQLException, ClassNotFoundException;
    public boolean addEmployee(EmployeeDTO dto) throws SQLException, ClassNotFoundException;
    public boolean updateEmployee(EmployeeDTO dto) throws SQLException, ClassNotFoundException;
    public boolean existEmployee(String id) throws SQLException, ClassNotFoundException ;
    public boolean deleteEmployee(String id) throws SQLException, ClassNotFoundException;
    public String generateNewEmployeeID () throws SQLException, ClassNotFoundException;
    public EmployeeDTO searchEmployee(String id) throws SQLException, ClassNotFoundException;
}
