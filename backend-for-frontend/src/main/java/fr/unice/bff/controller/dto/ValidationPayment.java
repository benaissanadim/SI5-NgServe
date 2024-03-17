package fr.unice.bff.controller.dto;

import java.util.List;

public class ValidationPayment {
    String commandId;
    String tableId;

    List<DishTimes> dishTimes;

    public String getCommandId() {
        return commandId;
    }

    public void setCommandId(String commandId) {
        this.commandId = commandId;
    }

    public String getTableId() {
        return tableId;
    }

    public void setTableId(String tableId) {
        this.tableId = tableId;
    }

    public List<DishTimes> getDishTimes() {
        return dishTimes;
    }

    public void setDishTimes(List<DishTimes> dishTimes) {
        this.dishTimes = dishTimes;
    }
}
