package lk.ijse.CherryClothing.view.tm;

import java.math.BigDecimal;

public class EmployeeTM implements Comparable<EmployeeTM>{
    private String id;
    private String name;
    private String address;
    private BigDecimal salary;

    public EmployeeTM(String id, String name, String address, BigDecimal salary) {
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
    @Override
    public int compareTo(EmployeeTM o) {
        return id.compareTo(o.getId());
    }
}
