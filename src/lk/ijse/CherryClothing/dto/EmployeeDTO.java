package lk.ijse.CherryClothing.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public class EmployeeDTO implements Serializable {
    private String id;
    private String name;
    private String address;
    private BigDecimal salary;

    public EmployeeDTO(String id, String name, String address, BigDecimal salary) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.salary = salary;

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public BigDecimal getSalary() {
        return salary;
    }

    public void setSalary(BigDecimal salary) {
        this.salary = salary;
    }


    @Override
    public String toString() {
        return "Customer{" + "id='" + id + '\'' + ", name='" + name + '\'' + ", address='" + address + '\'' + ", salary=" + salary + '}';
    }
}
