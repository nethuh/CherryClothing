package lk.ijse.CherryClothing.view.tm;

import java.math.BigDecimal;

public class OrderDetailTM {
    private String itemId;
    private String type;
    private int qty;
    private BigDecimal unitPrice;
    private BigDecimal total;

    public OrderDetailTM( String itemId, String type, int qty,BigDecimal unitPrice,BigDecimal total) {
        this.itemId = itemId;
        this.qty = qty;
        this.type = type;
        this.unitPrice = unitPrice;
        this.total = total;
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

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    @Override
    public String toString() {
        return "CartDetail{" +
                ", itemId='" + itemId + '\'' +
                ", qty=" + qty +
                ", type='" + type + '\'' +
                ", unitPrice=" + unitPrice +
                "total='" + total + '\'' +
                '}';
    }

}
