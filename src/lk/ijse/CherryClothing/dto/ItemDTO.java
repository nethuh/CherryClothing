package lk.ijse.CherryClothing.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public class ItemDTO implements Serializable {
    private String id;
    private String type;
    private BigDecimal unitPrice;
    private int qtyOnHand;


    public ItemDTO(String id, String type, BigDecimal unitPrice, int qtyOnHand) {
        this.id = id;
        this.type = type;
        this.unitPrice = unitPrice;
        this.qtyOnHand = qtyOnHand;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public int getQtyOnHand() {
        return qtyOnHand;
    }

    public void setQtyOnHand(int qtyOnHand) {
        this.qtyOnHand = qtyOnHand;
    }

    @Override
    public String toString() {
        return "Item{" +
                "id='" + id + '\'' +
                ", type='" + type + '\'' +
                ", unitPrice=" + unitPrice +
                ", qtyOnHand=" + qtyOnHand +
                '}';
    }
}
