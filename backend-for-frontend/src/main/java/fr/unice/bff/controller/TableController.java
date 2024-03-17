package fr.unice.bff.controller;
import java.util.Collections;

import fr.unice.bff.controller.dto.*;
import fr.unice.bff.models.OrderPerPerson;
import fr.unice.bff.models.OrdersPerTable;
import fr.unice.bff.service.OrdersCache;
import fr.unice.bff.service.TableService;
import org.slf4j.Logger;
import reactor.core.publisher.Flux;
import org.springframework.web.bind.annotation.*;
import java.time.Duration;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.MediaType;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.web.bind.annotation.GetMapping;
import java.util.List;

    @RestController
    @RequestMapping(path = TableController.BASE_URI)
    public class TableController {
        public static final String BASE_URI = "/tables";
        private final Logger LOG = LoggerFactory.getLogger(TableController.class);

        @Autowired
        private TableService tableService;

        @Autowired
        private OrdersCache ordersCache;

        @PostMapping("/{orderId}/leave")
        public ResponseEntity<TableOrder> leaveTable(@PathVariable("orderId") String orderId) {
            LOG.info("Received request to leave table with order ID: " + orderId);
            try {
                TableOrder reopenedOrder = tableService.billTable(orderId);
                LOG.info("Table with order ID " + orderId + " has been successfully left.");
                return ResponseEntity.ok(reopenedOrder);
            } catch (Exception e) {
                LOG.error("Error while trying to leave table with order ID " + orderId + ": " + e.getMessage());
                return ResponseEntity.ok().body(null);
            }
        }
        @PostMapping("/reserve")
        public ResponseEntity<TableId> reserve() {
            try {
                LOG.info("Received request to reserve a table.");
                return ResponseEntity.ok(tableService.takeForEatIn());
            } catch (Exception e) {
                LOG.error("Error while reserving a table: " + e.getMessage());
                return ResponseEntity.ok().body(null);
            }
        }

        @PostMapping("/validateEatIn/{orderId}")
        public ResponseEntity<ValidationPayment> addAllItemsToOrderForEatIn(
                @PathVariable("orderId") String orderId,
                @RequestBody List<ItemDTO> items
        ) {
            try {
                LOG.info("Received request to validate items for eat-in order with order ID: " + orderId);
                ValidationPayment tableOrder = tableService.addAllItemsToOrderForEatIn(orderId, items);
                return ResponseEntity.ok(tableOrder);
            } catch (Exception e) {
                LOG.error("Error while validating items for eat-in order: " + e.getMessage());
                return ResponseEntity.badRequest().body(null);
            }
        }

        @PostMapping("start")
        public ResponseEntity<TableOrder> start(@RequestBody StartOrderingDTO startOrderingDTO){
            return ResponseEntity.ok().body(tableService.start(startOrderingDTO));
        }

        @PostMapping("validate/{table}")
        public ResponseEntity<ResultValidation> validate(@PathVariable("table") int table){
            return ResponseEntity.ok().body(tableService.validate(table));
        }

        @PostMapping("/addToTable/{table}")
        public ResponseEntity<String> add(@RequestBody OrderPerPerson orderPerPerson, @PathVariable int table){
            tableService.addOrder(orderPerPerson, table);
            return ResponseEntity.ok().body("ADDED SUCCESSFULLY");
        }

        //@GetMapping("/orders/{table}")
        //public ResponseEntity<List<OrderPerPerson>> get(@PathVariable int table){
           // return ResponseEntity.ok().body(tableService.getAll(table));
        //}

        @GetMapping(value = "/orders/{table}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
        public Flux<ServerSentEvent<OrdersPerTable>> getChangesSSE(@PathVariable int table) {
            return Flux.interval(Duration.ofSeconds(1))
                    .map(sequence -> {
                        OrdersPerTable data = ordersCache.getTable(table);
                        if (data == null) {
                            data = null;
                        }
                        return ServerSentEvent.<OrdersPerTable>builder()
                                .id(String.valueOf(sequence))
                                .data(data)
                                .build();
                    });
        }




        @GetMapping("/tableOrders/{table}")
        public ResponseEntity<OrdersPerTable> getTableOrder(@PathVariable int table){
            OrdersPerTable ordersPerTable = ordersCache.getTable(table);
            return ResponseEntity.ok().body(ordersPerTable);
        }


        @PostMapping("/bill/{table}/{name}")
        public ResponseEntity<Boolean> bill(@PathVariable int table, @PathVariable String name){
            return ResponseEntity.ok().body(tableService.bill(table, name));
        }

        @PostMapping("/bill-all/{table}")
        public ResponseEntity<Boolean> bill(@PathVariable int table){
            return ResponseEntity.ok().body(tableService.bill(table));
        }

        @PostMapping("/validateEatOut")
        public ResponseEntity<ValidationPayment> addAllItemsToOrderForEatOut(
                @RequestBody List<ItemDTO> items) {
            try {
                LOG.info("Received request to validate " + items.size() + " items for eat-out order.");
                ValidationPayment preparation = tableService.addAllItemsToOrderForEatOut(items, true);
                LOG.info("Validation successful for " + items.size() + " items for eat-out order.");
                return ResponseEntity.ok(preparation);
            } catch (Exception e) {
                LOG.error("Error while validating items for eat-out order: " + e.getMessage());
                return ResponseEntity.ok().body(null);
            }
        }

    }
