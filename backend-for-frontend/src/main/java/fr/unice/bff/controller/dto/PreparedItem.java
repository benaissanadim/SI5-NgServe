package fr.unice.bff.controller.dto;


import java.time.LocalDateTime;
import java.util.UUID;

public class PreparedItem {

    private String id;

    private String shortName;

    private String recipe;

    private String shouldStartAt;

    private String startedAt;

    private String finishedAt;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getRecipe() {
        return recipe;
    }

    public void setRecipe(String recipe) {
        this.recipe = recipe;
    }

    public String getShouldStartAt() {
        return shouldStartAt;
    }

    public void setShouldStartAt(String shouldStartAt) {
        this.shouldStartAt = shouldStartAt;
    }

    public String getStartedAt() {
        return startedAt;
    }

    public void setStartedAt(String startedAt) {
        this.startedAt = startedAt; // MongoDB is precise at millisecond, not nano (avoid equality problem)
    }

    public String getFinishedAt() {
        return finishedAt;
    }

    public void setFinishedAt(String finishedAt) {
        this.finishedAt = finishedAt;
    }

}
