package fr.unice.bff.controller.dto;

public class DishTimes {

    private String id;
    private String shortName;
    private String preparationTime;

    public String getShortName() {
        return shortName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getPreparationTime() {
        return preparationTime;
    }

    public void setPreparationTime(String preparationTime) {
        this.preparationTime = preparationTime;
    }

    @Override
    public String toString() {
        return "ResponseToClient{" + "name='" + shortName + '\'' + ", preparationTime='" + preparationTime + '\'' + '}';
    }
}
