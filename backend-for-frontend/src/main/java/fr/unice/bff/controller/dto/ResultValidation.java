package fr.unice.bff.controller.dto;

import lombok.ToString;

@ToString
public class ResultValidation {
    String opened;
    String shouldBeReadyAt;
    String orderId;

    public String getOpened() {
        return opened;
    }

    public void setOpened(String opened) {
        this.opened = opened;
    }

    public String getShouldBeReadyAt() {
        return shouldBeReadyAt;
    }

    public void setShouldBeReadyAt(String shouldBeReadyAt) {
        this.shouldBeReadyAt = shouldBeReadyAt;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
}
