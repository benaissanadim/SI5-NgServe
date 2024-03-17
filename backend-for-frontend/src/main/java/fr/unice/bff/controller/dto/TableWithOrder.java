package fr.unice.bff.controller.dto;

import javax.validation.constraints.Positive;
import java.util.UUID;

public class TableWithOrder {

    @Positive
    private Long number;

    private boolean taken;

    private String tableOrderId; // could be null (is null if taken == false)
    // could also be complemented with more information on the order itself, here as a MVP, we only add the id
    // forcing for a callback to the API to get the information

    public Long getNumber() {
        return number;
    }

    public void setNumber(Long number) {
        this.number = number;
    }

    public boolean isTaken() {
        return taken;
    }

    public void setTaken(boolean taken) {
        this.taken = taken;
    }

    public String getTableOrderId() {
        return tableOrderId;
    }

    public void setTableOrderId(String tableOrderId) {
        this.tableOrderId = tableOrderId;
    }

}
