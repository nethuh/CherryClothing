package lk.ijse.CherryClothing.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class OrderDTO {

        private String orderId;
        private LocalDate date;
        private String customerId;
        private String cusName;
        private BigDecimal orderTotal;

       List<OrderDetailDTO> orderDetail;


    public OrderDTO(String orderId, LocalDate date, String customerId,List<OrderDetailDTO> orderDetail) {
            this.orderId = orderId;
            this.date = date;
            this.customerId = customerId;
            this.orderDetail =orderDetail;

        }
    public OrderDTO(String orderId, LocalDate orderDate, String customerId) {
        this.orderId = orderId;
        this.date = orderDate;
        this.customerId = customerId;
    }

    public OrderDTO(String orderId, LocalDate orderDate, String customerId, String customerName, BigDecimal orderTotal) {
        this.orderId = orderId;
        this.date = orderDate;
        this.customerId = customerId;
        this.cusName = customerName;
        this.orderTotal = orderTotal;
    }
    public List<OrderDetailDTO> getOrderDetails() {
        return orderDetail;
    }

    public void setOrderDetails(List<OrderDetailDTO> orderDetails) {
        this.orderDetail = orderDetails;
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
    public String getCustomerName() {
        return cusName;
    }

    public void setCustomerName(String customerName) {
        this.cusName = customerName;
    }

    public BigDecimal getOrderTotal() {
        return orderTotal;
    }

    public void setOrderTotal(BigDecimal orderTotal) {
        this.orderTotal = orderTotal;
    }



        @Override
        public String toString() {
            return "Order{" +
                    "orderId='" + orderId + '\'' +
                    ", date='" + date + '\'' +
                    ", customerId='" + customerId + '\'' +
                    ", cusName='" + cusName + '\'' +
                    ", orderTotal=" + orderTotal +
                    '}';
        }
}
