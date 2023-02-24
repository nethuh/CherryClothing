package lk.ijse.CherryClothing.dao.impl;

import lk.ijse.CherryClothing.dao.custom.EmployeeDAO;
import lk.ijse.CherryClothing.entity.Employee;
import lk.ijse.CherryClothing.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class EmployeeDAOImpl implements EmployeeDAO {
    @Override
    public ArrayList<Employee> getAll() throws SQLException, ClassNotFoundException {
        ArrayList<Employee> allEmployee = new ArrayList<>();
        ResultSet rst = CrudUtil.execute("SELECT * FROM employee");
        while (rst.next()) {
            allEmployee.add(new Employee(rst.getString("emp_id"), rst.getString("emp_name"), rst.getString("emp_address"), rst.getBigDecimal("salary") ));
        }
        return allEmployee;
    }

    @Override
    public boolean delete(String code) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("DELETE FROM employee WHERE emp_id=?", code);
    }


    @Override
    public boolean add(Employee entity) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("INSERT INTO employee (emp_id,emp_name,emp_address,salary) VALUES (?,?,?,?)",entity.getId(),entity.getName(),entity.getAddress(),entity.getSalary());
    }

    @Override
    public boolean update(Employee entity) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("UPDATE employee SET emp_name=?,emp_address=?,salary=? WHERE emp_id=?",entity.getId(),entity.getName(),entity.getAddress(),entity.getSalary());
    }

    @Override
    public boolean exist(String code) throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.execute("SELECT emp_id FROM employee WHERE emp_id=?", code);
        return rst.next();
    }

    @Override
    public String generateNewId() throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.execute("SELECT emp_id FROM employee ORDER BY emp_id DESC LIMIT 1;");
        if (rst.next()) {
            String id = rst.getString("emp_id");
            int newItemId = Integer.parseInt(id.replace("E", "")) + 1;
            return String.format("E0%03d", newItemId);
        } else {
            return "E0001";
        }
    }

    @Override
    public Employee search(String code) throws SQLException, ClassNotFoundException {
        ResultSet rst  = CrudUtil.execute("SELECT * FROM employee WHERE emp_id=?",code+"");
        rst.next();
        return new Employee(code + "", rst.getString("emp_name"),rst.getString("emp_address"), rst.getBigDecimal("salary"));
    }
}
