package fr.unice.bff.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import fr.unice.bff.controller.dto.PreparationResult;
import fr.unice.bff.controller.dto.PreparationSent;
import fr.unice.bff.controller.dto.PreparedItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;

import static fr.unice.bff.service.MenuService.objectMapper;
import static fr.unice.bff.util.ExternalCall.call;
import static fr.unice.bff.util.ExternalCall.send;

@Service
public class CookingService {

    @Value("${kitchen.service.url}")
    private String kitchenServiceUrl;

    private final Logger LOG = LoggerFactory.getLogger(CookingService.class);

    private final OrdersCache ordersCache;

    public CookingService(OrdersCache ordersCache) {
        this.ordersCache = ordersCache;
    }

    public PreparationResult[] preparationStarted() {
        String json = call(kitchenServiceUrl+"/preparations?state=preparationStarted");
        PreparationResult[] tables = null;
        try {
            tables = objectMapper.readValue(json, PreparationResult[].class);
            LOG.info("Successfully retrieved preparationsStarted information.");
        } catch (JsonProcessingException e) {
            LOG.error("Error occurred while calling preparations API: " + e.getMessage());
        }
        return tables;
    }

    public void startCookingItem(String id) throws InterruptedException {
        try {
            send(kitchenServiceUrl+"/preparedItems/"+id+"/start");
            Thread.sleep(100);
        }catch (Exception e){

        }
    }

    public void takenToTable(String id) throws InterruptedException {
        try {
            send(kitchenServiceUrl+"/preparations/"+id+"/takenToTable");
            Thread.sleep(100);
        }catch (Exception e){
        }
    }

    public void servedToTable(String tableNumber) throws InterruptedException {
        PreparationResult[] preparationSents = this.preparationFinished();
        for(PreparationResult p : preparationSents){
            if(p.getTableId().equals(tableNumber)){
                takenToTable(p.getId());
            }
        }
        ordersCache.updateOrderStatus(Integer.parseInt(tableNumber), "SERVED_TO_TABLE","");
    }

    public void endCookingItem(String id)  {
        try {
            send(kitchenServiceUrl+"/preparedItems/"+id+"/finish");
            Thread.sleep(100);

        }catch (Exception e){

        }
    }

    public void startCooking(String tableNumber) throws InterruptedException {
        PreparationResult[] preparationSents = this.preparationStarted();
        for(PreparationResult p : preparationSents){
            if(p.getTableId().equals(tableNumber)){
                for(PreparedItem pp : p.getPreparedItems())
                    startCookingItem(pp.getId());
            }
        }
        ordersCache.updateOrderStatus(Integer.parseInt(tableNumber), "START_COOKING", LocalTime.now().toString());
    }

    public void endCooking(String tableNumber) throws InterruptedException {
        PreparationResult[] preparationSents = this.preparationStarted();
        for(PreparationResult p : preparationSents){
            if(p.getTableId().equals(tableNumber)) {
                for (PreparedItem pp : p.getPreparedItems())
                    endCookingItem(pp.getId());
            }
        }

        ordersCache.updateOrderStatus(Integer.parseInt(tableNumber), "END_COOKING", LocalTime.now().toString());
    }


    public PreparationResult[] preparationFinished() {
        String json = call(kitchenServiceUrl+"/preparations?state=readyToBeServed");
        PreparationResult[] tables = null;
        try {
            tables = objectMapper.readValue(json, PreparationResult[].class);
            LOG.info("Successfully retrieved preparationsStarted information.");
        } catch (JsonProcessingException e) {
            LOG.error("Error occurred while calling preparations API: " + e.getMessage());
        }
        return tables;
    }


    public PreparationSent[] transformPreparation(PreparationResult[] preparationResults) {
        PreparationSent[] transformedPreparations = new PreparationSent[preparationResults.length];

        for (int i = 0; i < preparationResults.length; i++) {
                    PreparationResult result = preparationResults[i];
                    PreparationSent preparationSent = new PreparationSent();
                    preparationSent.setId(result.getId());
                    preparationSent.setTableId(result.getTableId());
                    preparationSent.setCompletedAt(result.getCompletedAt());
                    preparationSent.setShouldBeReadyAt(result.getShouldBeReadyAt());
                    transformedPreparations[i] = preparationSent;
        }

        return transformedPreparations;
    }



}