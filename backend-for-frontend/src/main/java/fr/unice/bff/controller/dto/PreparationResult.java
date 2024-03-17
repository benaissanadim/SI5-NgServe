package fr.unice.bff.controller.dto;

import java.util.List;

public class PreparationResult {

    private String id;

    private String tableId;

    private String shouldBeReadyAt;

    private String completedAt;

    private String takenForServiceAt; // brought to the table

    private List<PreparedItem> preparedItems;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTableId() {
        return tableId;
    }

    public void setTableId(String tableId) {
        this.tableId = tableId;
    }

    public String getShouldBeReadyAt() {
        return shouldBeReadyAt;
    }

    public void setShouldBeReadyAt(String shouldBeReadyAt) {
        this.shouldBeReadyAt = shouldBeReadyAt;
    }

    public String getCompletedAt() {
        return completedAt;
    }

    public void setCompletedAt(String completedAt) {
        this.completedAt = completedAt;
    }

    public String getTakenForServiceAt() {
        return takenForServiceAt;
    }

    public void setTakenForServiceAt(String takenForServiceAt) {
        this.takenForServiceAt = takenForServiceAt;
    }

    public List<PreparedItem> getPreparedItems() {
        return preparedItems;
    }

    public void setPreparedItems(List<PreparedItem> preparedItems) {
        this.preparedItems = preparedItems;
    }


}
