package lk.ijse.CherryClothing.entity;

import java.math.BigDecimal;
import java.time.LocalDate;

public class CustomEntity {
    //Customer
    private String id;
    private String name;
    private String address;
    private String contact;

    //Item
    private String type;
    private double unitPrice;
    private int qtyOnHand;

    //OrderDetail
    private String itemCode;
    private int qty;
    private String oid;

    //Order
    private String orderId;
    private LocalDate orderDate;
    private String customerId;



    public CustomEntity(String oid, LocalDate date, String customerID, String itemCode, int qty, double unitPrice) {
        this.unitPrice = unitPrice;
        this.oid = oid;
        this.orderDate = date;
        this.customerId = customerID;
        this.itemCode = itemCode;
        this.qty = qty;
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

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public int getQtyOnHand() {
        return qtyOnHand;
    }

    public void setQtyOnHand(int qtyOnHand) {
        this.qtyOnHand = qtyOnHand;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public String getOid() {
        return oid;
    }

    public void setOid(String oid) {
        this.oid = oid;
    }

    public LocalDate getDate() {
        return orderDate;
    }

    public void setDate(LocalDate date) {
        this.orderDate = date;
    }

    public String getCustomerID() {
        return customerId;
    }

    public void setCustomerID(String customerID) {
        this.customerId = customerID;
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }
}


