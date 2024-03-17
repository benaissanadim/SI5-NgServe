package fr.unice.bff.controller.dto;


import com.fasterxml.jackson.annotation.JsonFormat;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class Preparation {

    private UUID id;


    private String shouldBeReadyAt;

    private List<CookedItem> preparedItems;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getShouldBeReadyAt() {
        return shouldBeReadyAt;
    }

    public void setShouldBeReadyAt(String shouldBeReadyAt) {
        this.shouldBeReadyAt = shouldBeReadyAt;
    }

    public List<CookedItem> getPreparedItems() {
        return preparedItems;
    }

    public void setPreparedItems(List<CookedItem> preparedItems) {
        this.preparedItems = preparedItems;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Preparation)) return false;
        Preparation that = (Preparation) o;
        return id.equals(that.id) && shouldBeReadyAt.equals(that.shouldBeReadyAt) && preparedItems.equals(that.preparedItems);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, shouldBeReadyAt, preparedItems);
    }


}
