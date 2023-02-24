package lk.ijse.CherryClothing.view.tm;

import java.math.BigDecimal;
import java.time.LocalDate;

public class OrderTM implements Comparable<OrderTM> {
    private String orderId;
    private LocalDate orderDate;
    private String customerId;

    public OrderTM(String orderId, LocalDate orderDate, String customerId) {
        this.orderId = orderId;
        this.orderDate = orderDate;
        this.customerId = customerId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public LocalDate getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDate orderDate) {
        this.orderDate = orderDate;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    @Override
    public String toString() {
        return "OrderTM{" +
                "orderId='" + orderId + '\'' +
                ", orderDate=" + orderDate +
                ", customerId='" + customerId + '\'' +
                '}';
    }
    @Override
    public int compareTo(OrderTM o) {
        return orderId.compareTo(o.getOrderId());
    }
    }

