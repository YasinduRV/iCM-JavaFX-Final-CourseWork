package model.tm;

import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;

public class ItemTm extends RecursiveTreeObject<ItemTm> {
    private String id;
    private String description;
    private String type;
    private String suppId;
    private double UnitPrice;
    private int qtyOnHand;
    private String size;


    public ItemTm(String id, String description, String type, String suppId, double unitPrice, int qtyOnHand, String size) {
        this.id = id;
        this.description = description;
        this.type = type;
        this.suppId = suppId;
        UnitPrice = unitPrice;
        this.qtyOnHand = qtyOnHand;
        this.size = size;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSuppId() {
        return suppId;
    }

    public void setSuppId(String suppId) {
        this.suppId = suppId;
    }

    public double getUnitPrice() {
        return UnitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        UnitPrice = unitPrice;
    }

    public int getQtyOnHand() {
        return qtyOnHand;
    }

    public void setQtyOnHand(int qtyOnHand) {
        this.qtyOnHand = qtyOnHand;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }
}
