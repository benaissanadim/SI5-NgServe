package fr.unice.bff.models;

import fr.unice.bff.controller.dto.ItemDTO;
import java.util.List;


public class OrderSeats {
    int seat;
    List<ItemDTO> item;
    boolean paySeparately;
    public boolean isPaySeparately() {
        return paySeparately;
    }
    public void setPaySeparately(boolean paySeparately) {
        this.paySeparately = paySeparately;
    }
    
}
