package lk.ijse.CherryClothing.entity;

import java.math.BigDecimal;

public class OrderDetail {
    private String orderId;
    private String itemId;
    private int qty;
    private String type;
    private BigDecimal unitPrice;

    public OrderDetail(String orderId, String itemId, int qty, String type, BigDecimal unitPrice) {
        this.orderId = orderId;
        this.itemId = itemId;
        this.qty = qty;
        this.type = type;
        this.unitPrice = unitPrice;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    @Override
    public String toString() {
        return "CartDetail{" +
                "orderId='" + orderId + '\'' +
                ", itemId='" + itemId + '\'' +
                ", qty=" + qty +
                ", type='" + type + '\'' +
                ", unitPrice=" + unitPrice +
                '}';
    }
}
