package com.es.phoneshop.web.controller.pages;

import com.es.core.order.OrderService;
import com.es.core.services.AttributeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.UUID;

@Controller
@RequestMapping(value = "/orderOverview")
public class OrderOverviewPageController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private AttributeService attributeService;

    @RequestMapping(value = "/{orderUuid}", method = RequestMethod.GET)
    public String getOrderOverview(
            @PathVariable("orderUuid") UUID uuid,
            Model model) {
        model.addAttribute("order", orderService.getOrderByUUID(uuid));
        model.addAttribute("attributes", attributeService.getAttributes());

        return "orderOverviewPage";
    }

}
