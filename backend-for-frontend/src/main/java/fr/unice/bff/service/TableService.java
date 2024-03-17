package fr.unice.bff.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import fr.unice.bff.controller.dto.CookedItem;
import fr.unice.bff.controller.dto.DishTimes;
import fr.unice.bff.controller.dto.ItemDTO;
import fr.unice.bff.controller.dto.Preparation;
import fr.unice.bff.controller.dto.ResultValidation;
import fr.unice.bff.controller.dto.StartOrderingDTO;
import fr.unice.bff.controller.dto.TableCreate;
import fr.unice.bff.controller.dto.TableId;
import fr.unice.bff.controller.dto.TableOrder;
import fr.unice.bff.controller.dto.TableWithOrder;
import fr.unice.bff.controller.dto.ValidationPayment;
import fr.unice.bff.models.OrderPerPerson;
import fr.unice.bff.models.OrdersPerTable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import static fr.unice.bff.service.MenuService.objectMapper;
import static fr.unice.bff.util.ExternalCall.call;
import static fr.unice.bff.util.ExternalCall.send;

@Service
public class TableService {

    @Value("${dining.service.url}")
    private String diningServiceUrl;

    private final Long VIRTUAL_TABLE_ID = 12345679L;
    private final Logger LOG = LoggerFactory.getLogger(TableService.class);

    @Autowired
    private OrdersCache ordersCache;
    public TableWithOrder[] retrieveTables() {
        String json = call(diningServiceUrl+"/tables");
        TableWithOrder[] tables = null;
        try {
            tables = objectMapper.readValue(json, TableWithOrder[].class);
            LOG.debug("Successfully retrieved tables information from DINING-SERVICE");
        } catch (JsonProcessingException e) {
            LOG.error("Error occurred while calling tables API: " + e.getMessage());
        }
        return tables;
    }

    public TableOrder tableOrder(String orderId) {
        String json = call(diningServiceUrl+"/tableOrders/"+ orderId);
        TableOrder table = null;
        try {
            table = objectMapper.readValue(json, TableOrder.class);
            LOG.debug("Successfully get order table");
        } catch (JsonProcessingException e) {
            LOG.error("Error occurred while calling tableOrders API: " + e.getMessage());
        }
        return table;
    }

    @Cacheable("tablesss")
    public TableWithOrder createTable(TableCreate tableCreate) {

        ResponseEntity<String> result= send(diningServiceUrl+"/tables", tableCreate);

        TableWithOrder table = null;
        try {
            table = objectMapper.readValue(result.getBody(), TableWithOrder.class);
            LOG.debug("Successfully creating viirtual table from DINING-SERVICE");
        } catch (JsonProcessingException e) {
            TableWithOrder t = new TableWithOrder();
            t.setTableOrderId(null);
            t.setNumber(VIRTUAL_TABLE_ID);
            t.setTaken(false);
        }
        return table;
    }

    public TableOrder start(StartOrderingDTO startOrderingDTO){
        ordersCache.clear();
        TableOrder tableOrder = reserveTable(startOrderingDTO);
        ordersCache.createOrder(tableOrder.getId(),startOrderingDTO.getTableId().intValue(),
                startOrderingDTO.getCustomersCount());
        return tableOrder;
    }


    public TableOrder reserveTable(StartOrderingDTO startOrderingDTO) {
        ResponseEntity<String> result= send(diningServiceUrl+"/tableOrders", startOrderingDTO);

        TableOrder table = null;
        try {
            table = objectMapper.readValue(result.getBody(), TableOrder.class);
            LOG.debug("Successfully reserving table "+startOrderingDTO.getTableId() + " from DINING-SERVICE");
        } catch (JsonProcessingException e) {
            LOG.error("Error occurred while reserving tables API: " + e.getMessage());
        }
        return table;
    }

    public TableOrder acceptOrder(ItemDTO itemDTO, String orderId) {
        ResponseEntity<String> result= send(diningServiceUrl+"/tableOrders/"+orderId, itemDTO);

        TableOrder table = null;
        try {
            table = objectMapper.readValue(result.getBody(), TableOrder.class);
            LOG.debug("Successfully accepting order information from DINING-SERVICE");
        } catch (JsonProcessingException e) {
            LOG.error("Error occurred while accepting API: " + e.getMessage());
        }
        return table;
    }


    public TableOrder billTable(String orderId) {
        ResponseEntity<String> result= send(diningServiceUrl+"/tableOrders/"+orderId+"/bill", null);

        TableOrder table = null;
        try {
            table = objectMapper.readValue(result.getBody(), TableOrder.class);
            LOG.debug("Successfully reopenening table from DINING-SERVICE");
        } catch (JsonProcessingException e) {
            LOG.error("Error occurred while accepting API: " + e.getMessage());
        }
        return table;
    }

    public Preparation[] prepare(String orderId){
        ResponseEntity<String> result= send(diningServiceUrl+"/tableOrders/"+orderId+"/prepare", null);

        Preparation[] preparations = null;
        try {
            preparations = objectMapper.readValue(result.getBody(), Preparation[].class);
            LOG.debug("Successfully preparing command.");
        } catch (JsonProcessingException e) {
            LOG.error("Error occurred while accepting API for prepare: " + e.getMessage());
        }
        return preparations;
    }

    private ValidationPayment transformToResponse(Preparation[] p , TableOrder t){
        ValidationPayment validationPayment = new ValidationPayment();
        validationPayment.setCommandId(t.getId());
        validationPayment.setTableId(t.getTableNumber().toString());
        List<DishTimes> dishTimes = new ArrayList<>();
        for(int i = 0 ; i < p.length ; i++){
            DishTimes r = new DishTimes();
            r.setPreparationTime(p[i].getShouldBeReadyAt());
            StringBuilder all = new StringBuilder();
            for(CookedItem item : p[i].getPreparedItems()){
                if(!all.toString().contains(item.getShortName()))  {
                    all.append(" ").append(item.getShortName());
                }
            }
            r.setShortName(all.toString().toString());
            r.setId(t.getId());
            dishTimes.add(r);
        }
        validationPayment.setDishTimes(dishTimes);
        return validationPayment;
    }

    public Optional<TableWithOrder> findTableAvailable(){
        return Arrays.stream(retrieveTables())
                .filter(table -> !table.isTaken() && table.getNumber() < 10)
                .findFirst();
    }

    public TableWithOrder findVirtualTable(){
        return Arrays.stream(retrieveTables())
                .filter(table -> Objects.equals(table.getNumber(), VIRTUAL_TABLE_ID))
                .findFirst().get();
    }

    public TableId takeForEatIn(){
        LOG.info("RESERVING TABLE FOR EAT IN - START");
        StartOrderingDTO startOrderingDTO = new StartOrderingDTO();
        Optional<TableWithOrder> optionalTableWithOrder = findTableAvailable();
        if(optionalTableWithOrder.isEmpty()) return null;
        TableWithOrder tableWithOrder = optionalTableWithOrder.get();
        startOrderingDTO.setTableId(tableWithOrder.getNumber());
        startOrderingDTO.setCustomersCount(1);
        TableOrder t = reserveTable(startOrderingDTO);
        TableId tableId = new TableId();
        tableId.setId(t.getId());
        LOG.info("RESERVING TABLE FOR EAT IN - END");
        return tableId;
    }


    @Cacheable("tableVirtual")
    public Boolean createVirtualTableEatOut(){
        try{
            TableCreate t = new TableCreate();
            t.setTableId(VIRTUAL_TABLE_ID);
            createTable(t);
        }catch (Exception e){

        }
        return true;
    }

    public ValidationPayment addAllItemsToOrderForEatIn(String orderId, List<ItemDTO> items){
        LOG.info("VALIDATING ORDER FOR EATING IN - START");

        TableOrder tableOrder = null;
        LOG.info("accepting payment order is processing");
        for(ItemDTO item : items){
            tableOrder = acceptOrder(item, orderId);
        }
        Preparation[] p = prepare(orderId);

        ValidationPayment validationPayment = transformToResponse(p ,tableOrder);

        LOG.info("VALIDATING ORDER FOR EATING IN - END");

        return validationPayment;
    }

    public synchronized ValidationPayment addAllItemsToOrderForEatOut(List<ItemDTO> items, boolean eatOut){
        LOG.info("VALIDATING ORDER FOR EATING OUT - START");
        boolean b = createVirtualTableEatOut();
        TableWithOrder tableWithOrder = findVirtualTable();
        TableOrder tableOrder = null;
        StartOrderingDTO startOrderingDTO = new StartOrderingDTO();
        startOrderingDTO.setTableId(tableWithOrder.getNumber());
        startOrderingDTO.setCustomersCount(1);
        TableOrder t = reserveTable(startOrderingDTO);

        for(ItemDTO item : items){
            tableOrder = acceptOrder(item, t.getId());
        }
        if(eatOut){
            Preparation[] p = prepare(t.getId());
            ValidationPayment r = transformToResponse(p,t);
            r.setTableId(null);
            LOG.info("VALIDATING ORDER FOR EATING OUT - END");
            billTable(t.getId());

            return r;

        }

        billTable(t.getId());
        return null;
    }

    public void addOrder(OrderPerPerson orderPerPerson, int table){
        ordersCache.addToCache(orderPerPerson, table);
    }

    public List<OrderPerPerson> getAll(int tableNum){
        return ordersCache.get(tableNum);
    }

    public boolean bill(int table, String name){
        OrdersPerTable order = ordersCache.getTable(table);
        ordersCache.bill(table, name);
        OrderPerPerson orderPerPerson =
                order.getOrders().stream().filter(o-> o.getName().equals(name)).findFirst().get();
        addAllItemsToOrderForEatOut(orderPerPerson.getItems(), false);
        if(order.getOrders().stream().allMatch(OrderPerPerson::isBilled)){
            String orderId = order.getOrderId();
            System.out.println("heeeeeeeere");
            billTable(orderId);
            ordersCache.delete(orderId);
        }
        return true;
    }

    public boolean bill(int table){
        OrdersPerTable order = ordersCache.getTable(table);
        String orderId = order.getOrderId();
        TableOrder t = billTable(orderId);
        ordersCache.delete(orderId);
        return true;
    }

    public List<ItemDTO> transformToSimplifiedFormat(OrdersPerTable ordersPerTable) {
        Map<String, ItemDTO> simplifiedItemsMap = new HashMap<>();

        for (OrderPerPerson order : ordersPerTable.getOrders()) {
            for (ItemDTO item : order.getItems()) {
                String shortName = item.getShortName();
                int howMany = item.getHowMany();

                if (simplifiedItemsMap.containsKey(shortName)) {
                    ItemDTO existingItem = simplifiedItemsMap.get(shortName);
                    existingItem.setHowMany(existingItem.getHowMany() + howMany);
                } else {
                    ItemDTO simplifiedItem = new ItemDTO();
                    simplifiedItem.setId(item.getId());
                    simplifiedItem.setShortName(shortName);
                    simplifiedItem.setHowMany(howMany);
                    simplifiedItemsMap.put(shortName, simplifiedItem);
                }
            }
        }
        return new ArrayList<>(simplifiedItemsMap.values());
    }



    public ResultValidation validate(int table) {
        OrdersPerTable order = ordersCache.getTable(table);
        String orderId = order.getOrderId();
        TableOrder tableOrder = null;
        List<ItemDTO> itemDTOS = transformToSimplifiedFormat(order);
        for(ItemDTO item : itemDTOS){
            tableOrder = acceptOrder(item, orderId);
        }
        Preparation[] p = prepare(orderId);

        ResultValidation resultValidation = new ResultValidation();
        resultValidation.setOrderId(orderId);
        resultValidation.setShouldBeReadyAt(p[0].getShouldBeReadyAt());
        resultValidation.setShouldBeReadyAt(p[0].getShouldBeReadyAt());
        resultValidation.setOpened(tableOrder.getOpened().toString());
        LOG.info("result validation " + resultValidation);
        return resultValidation;
    }
}