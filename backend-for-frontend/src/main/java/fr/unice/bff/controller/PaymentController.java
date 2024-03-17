package fr.unice.bff.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

public class PaymentController {
    public static final String BASE_URI = "/payment";
    private final Logger LOG = LoggerFactory.getLogger(PaymentController.class);
    @PostMapping("/separately/{seat}")
    public void paySeparately(@PathVariable("seat") String seat) {
        LOG.info("seat "+seat+" request to pay separately .");
    }

    @PostMapping("/asgroup/{seat}")
    public void payAsGroup(@PathVariable("seat") String seat) {
        LOG.info("seat " +seat +"request to pay as a group. ");
    }
}
