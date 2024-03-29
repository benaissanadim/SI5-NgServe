package fr.unice.bff.controller.dto;



import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.UUID;


public class TableOrder {

    private String id;

    private Long tableNumber;

    private int customersCount;

    private LocalDateTime opened;

    private List<OrderingLine> lines;

    private List<Preparation> preparations;

    private String billed;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getTableNumber() {
        return tableNumber;
    }

    public void setTableNumber(Long tableNumber) {
        this.tableNumber = tableNumber;
    }

    public int getCustomersCount() {
        return customersCount;
    }

    public void setCustomersCount(int customersCount) {
        this.customersCount = customersCount;
    }

    public LocalDateTime getOpened() {
        return opened;
    }

    public void setOpened(LocalDateTime opened) {
        this.opened = opened.withNano(0); // MongoDB is precise at millisecond, not nano (avoid equality problem)
    }

    public List<OrderingLine> getLines() {
        return lines;
    }

    public void setLines(List<OrderingLine> lines) {
        this.lines = lines;
    }

    public List<Preparation> getPreparations() {
        return preparations;
    }

    public void setPreparations(List<Preparation> preparations) {
        this.preparations = preparations;
    }

    public String getBilled() {
        return billed;
    }

    public void setBilled(String billed) {
        this.billed = billed;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TableOrder)) return false;
        TableOrder that = (TableOrder) o;
        return customersCount == that.customersCount && id.equals(that.id) && tableNumber.equals(that.tableNumber) && opened.equals(that.opened) && Objects.equals(lines, that.lines) && Objects.equals(preparations, that.preparations) && Objects.equals(billed, that.billed);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, tableNumber, customersCount, opened, lines, preparations, billed);
    }

}
