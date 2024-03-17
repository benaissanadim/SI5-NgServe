package fr.unice.bff.service;

import fr.unice.bff.controller.dto.ItemDTO;
import fr.unice.bff.models.OrderPerPerson;
import fr.unice.bff.models.OrdersPerTable;
import org.aspectj.weaver.ast.Or;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class OrdersCache {

    public final static Map<String, OrdersPerTable> map = new HashMap<>();

    public final CacheManager cacheManager;

    public OrdersCache(CacheManager cacheManager) {
        this.cacheManager = cacheManager;
    }


    public void createOrder(String orderId, int tableNumber, int nb) {
        Cache cache = cacheManager.getCache("orders");
        OrdersPerTable ordersPerTable = new OrdersPerTable();
        ordersPerTable.setTable(tableNumber);
        ordersPerTable.setNb(nb);
        ordersPerTable.setOrderId(orderId);
        ordersPerTable.setStatus("ORDER_IN_PROGRESS");
        ordersPerTable.setOrders(new ArrayList<>());
        cache.put(tableNumber, ordersPerTable);
    }


    public void addToCache(OrderPerPerson orderPerPerson, int table) {

        Cache cache = cacheManager.getCache("orders");
        Cache.ValueWrapper valueWrapper = cache.get(table);

        if (valueWrapper == null) {
            return ; // La donnée n'est pas en cache
        }

        OrdersPerTable ordersPerTable = (OrdersPerTable)  valueWrapper.get();
        List<OrderPerPerson> orderPerPersonList = ordersPerTable.getOrders();
        Optional<OrderPerPerson> optionalOrderPerPerson = orderPerPersonList.stream().
                filter(o -> o.getName().equals(orderPerPerson.getName())).findAny();
        if(optionalOrderPerPerson.isEmpty()){
            ordersPerTable.getOrders().add(orderPerPerson);
            cache.put(ordersPerTable.getTable(), ordersPerTable);
            return;
        }
        OrderPerPerson sameOrderPerPerson = optionalOrderPerPerson.get();
        List<ItemDTO> newItems = orderPerPerson.getItems();
        List<ItemDTO> previousItems = sameOrderPerPerson.getItems();
        int i= 0;
        for(ItemDTO item : newItems){
            boolean find = false;
            for(ItemDTO previousItem : previousItems){
                if(previousItem.getShortName().equals(item.getShortName())){
                    previousItem.setHowMany(previousItem.getHowMany() + item.getHowMany());
                    find = true;
                    break;
                }
            }
            if(!find){
                previousItems.add(item);
            }
        }
        sameOrderPerPerson.setItems(previousItems);
        sameOrderPerPerson.setPrice(orderPerPerson.getPrice()+ sameOrderPerPerson.getPrice());
        cache.put(ordersPerTable.getTable(), ordersPerTable);
    }

    public boolean bill(int table, String name){
        Cache cache = cacheManager.getCache("orders");
        Cache.ValueWrapper valueWrapper = cache.get(table);
        if (valueWrapper == null) {
            return false;
        }
        OrdersPerTable order = (OrdersPerTable)  valueWrapper.get();
        OrderPerPerson orderPerPerson = order.getOrders().stream().filter(o -> o.getName().equals(name))
                .findFirst().get();
        orderPerPerson.setBilled(true);
        return true;
    }

    public void updateOrderStatus(int table, String status, String time){
        Cache cache = cacheManager.getCache("orders");
        Cache.ValueWrapper valueWrapper = cache.get(table);
        if (valueWrapper == null) {
            return ;
        }
        OrdersPerTable order = (OrdersPerTable)  valueWrapper.get();
        order.setStatus(status);
        order.setEndCookingTime(time);
    }


    public OrdersPerTable getFromCache(int tableNumber) {
        Cache cache = cacheManager.getCache("orders");
        Cache.ValueWrapper valueWrapper = cache.get(tableNumber);
        if (valueWrapper != null) {

            return (OrdersPerTable) valueWrapper.get();
        } else {
            return null; // La donnée n'est pas en cache
        }
    }

    public  void clear(){
        cacheManager.getCache("orders").clear();
    }


    public  List<OrderPerPerson> get(int tableNumber){

        Cache cache = cacheManager.getCache("orders");
        Cache.ValueWrapper valueWrapper = cache.get(tableNumber);
        if (valueWrapper == null) {
            return List.of();
        }
        OrdersPerTable order = (OrdersPerTable)  valueWrapper.get();
        return order.getOrders();
    }

    public OrdersPerTable getTable(int tableNumber){
        Cache cache = cacheManager.getCache("orders");
        Cache.ValueWrapper valueWrapper = cache.get(tableNumber);
        if (valueWrapper == null) {
            return null;
        }
        OrdersPerTable order = (OrdersPerTable)  valueWrapper.get();
        return order;
    }

    public  void delete(String orderId){
        cacheManager.getCache("orders").clear();
    }
}
