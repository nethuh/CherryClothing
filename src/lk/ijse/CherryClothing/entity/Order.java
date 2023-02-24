package lk.ijse.CherryClothing.entity;

import java.sql.Date;
import java.time.LocalDate;

public class Order {
    private String orderId;
    private LocalDate date;
    private String customerId;

    public Order() {
    }

    public Order(String orderId, LocalDate date, String customerId ) {
        this.orderId = orderId;
        this.date = date;
        this.customerId = customerId;

    }

    public Order(String cus_id, Date date, String address) {
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {

        this.customerId = customerId;
    }



    @Override
    public String toString() {
        return "Order{" +
                "orderId='" + orderId + '\'' +
                ", date='" + date + '\'' +
                ", customerId='" + customerId + '\'' +
                '}';
    }
}
