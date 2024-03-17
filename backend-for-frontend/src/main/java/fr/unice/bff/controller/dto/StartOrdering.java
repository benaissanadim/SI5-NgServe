package fr.unice.bff.controller.dto;

import javax.validation.constraints.Positive;
import java.util.UUID;

public class StartOrdering {

    @Positive
    private Long tableId;

    @Positive
    private int customersCount;

    public StartOrdering(Long tableOrderId, int i) {
        this.customersCount = i;
        this.tableId = tableOrderId;
    }

    public int getCustomersCount() {
        return customersCount;
    }

    public void setCustomersCount(int customersCount) {
        this.customersCount = customersCount;
    }

    public Long getTableId() {
        return tableId;
    }

    public void setTableId(Long tableId) {
        this.tableId = tableId;
    }

}
