package model;

import java.util.ArrayList;

public class Order {
    private String id;
    private String date;
    private String customerId;
    private String emplpoyeeId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public ArrayList<OrderDetails> getOrderDetailList() {
        return orderDetailList;
    }

    public void setOrderDetailList(ArrayList<OrderDetails> orderDetailList) {
        this.orderDetailList = orderDetailList;
    }

    private ArrayList< OrderDetails>orderDetailList;

    public Order(String id, String date, String customerId, ArrayList<OrderDetails> orderDetailList) {
        this.id = id;
        this.date = date;
        this.customerId = customerId;
        this.orderDetailList = orderDetailList;
    }
}
