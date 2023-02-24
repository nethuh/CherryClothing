package lk.ijse.CherryClothing.bo.Impl;

import lk.ijse.CherryClothing.bo.Custom.EmployeeBO;
import lk.ijse.CherryClothing.dao.DAOFactory;
import lk.ijse.CherryClothing.dao.custom.EmployeeDAO;
import lk.ijse.CherryClothing.dto.EmployeeDTO;
import lk.ijse.CherryClothing.entity.Employee;

import java.sql.SQLException;
import java.util.ArrayList;

public class EmployeeBOImpl implements EmployeeBO {

    EmployeeDAO employeeDAO = (EmployeeDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.EMPLOYEE);

    @Override
    public ArrayList<EmployeeDTO> getAllEmployees() throws SQLException, ClassNotFoundException {
        ArrayList<EmployeeDTO> allEmployee = new ArrayList<>();
        ArrayList<Employee> all = employeeDAO.getAll();
        for (Employee e : all) {
            allEmployee.add(new EmployeeDTO(e.getId(), e.getName(),e.getAddress(), e.getSalary()));
        }
        return allEmployee;
    }
    @Override
    public boolean deleteEmployee(String code) throws SQLException, ClassNotFoundException {
        return employeeDAO.delete(code);
    }
    @Override
    public boolean addEmployee(EmployeeDTO dto) throws SQLException, ClassNotFoundException {
        return employeeDAO.add (new Employee(dto.getId(),dto.getName(), dto.getAddress(), dto.getSalary()));

    }
    @Override
    public boolean updateEmployee(EmployeeDTO dto) throws SQLException, ClassNotFoundException {
        return employeeDAO.update(new Employee(dto.getId(),dto.getName(), dto.getAddress(), dto.getSalary()));

    }
    @Override
    public boolean existEmployee(String code) throws SQLException, ClassNotFoundException {
        return employeeDAO.exist(code);
    }
    @Override
    public String generateNewEmployeeID() throws SQLException, ClassNotFoundException {
        return employeeDAO.generateNewId();
    }
    @Override
    public EmployeeDTO searchEmployee(String code) throws SQLException, ClassNotFoundException {
        Employee e = employeeDAO.search(code);
        return new EmployeeDTO(e.getId(),e.getName(),e.getAddress(), e.getSalary());
    }
}
