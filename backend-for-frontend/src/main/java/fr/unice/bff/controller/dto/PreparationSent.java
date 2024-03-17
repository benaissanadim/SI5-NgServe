package fr.unice.bff.controller.dto;

import java.time.LocalDateTime;

public class PreparationSent {
    private String id;

    private String tableId;

    private String shouldBeReadyAt;

    private String completedAt;

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
}
