package lk.ijse.CherryClothing.view.tm;

import java.math.BigDecimal;

public class CClothesTM implements Comparable<CClothesTM>{
    private String id;
    private String type;
    private BigDecimal unitPrice;
    private int qtyOnHand;

    public CClothesTM(String id, String type, int qtyOnHand, BigDecimal unitPrice) {
        this.id = id;
        this.type = type;
        this.unitPrice = unitPrice;
        this.qtyOnHand = qtyOnHand;
    }

    //public CClothesTM(String id, String type, int qtyOnHand, double unitPrice) {
    //}

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

    public int getQty() {
        return qtyOnHand;
    }

    public void setQty(int qtyOnHand) {
        this.qtyOnHand = qtyOnHand;
    }

    public BigDecimal getPrice() {
        return unitPrice;
    }

    public void setPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }




    @Override
    public String toString() {
        return "CClothes{" + "id='" + id + '\'' + ", type='" + type + '\'' + ", price='" + unitPrice + '\'' + ", qty=" + qtyOnHand + '}';
    }

    @Override
    public int compareTo(CClothesTM o) {
        return id.compareTo(o.getId());
    }
}
