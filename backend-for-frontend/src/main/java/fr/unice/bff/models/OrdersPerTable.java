package fr.unice.bff.models;

import fr.unice.bff.controller.dto.ItemDTO;

import java.util.List;

public class OrdersPerTable {

    String orderId;
    int table;
    List<OrderPerPerson> orders;
    int nb;
    boolean validated;

    String endCookingTime;

    String status;

    public String getStatus() {
        return status;
    }

    public String getEndCookingTime() {
        return endCookingTime;
    }

    public void setEndCookingTime(String endCookingTime) {
        this.endCookingTime = endCookingTime;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public boolean isValidated() {
        return validated;
    }

    public void setValidated(boolean validated) {
        this.validated = validated;
    }

    public int getTable() {
        return table;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public void setTable(int table) {
        this.table = table;
    }

    public List<OrderPerPerson> getOrders() {
        return orders;
    }

    public void setOrders(List<OrderPerPerson> orders) {
        this.orders = orders;
    }

    public int getNb() {
        return nb;
    }

    public void setNb(int nb) {
        this.nb = nb;
    }
}