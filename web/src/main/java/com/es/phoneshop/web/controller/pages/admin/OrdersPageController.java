package com.es.phoneshop.web.controller.pages.admin;

import com.es.core.model.order.Order;
import com.es.core.model.order.OrderStatus;
import com.es.core.order.OrderService;
import com.es.core.services.AttributeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/admin/orders")
public class OrdersPageController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private AttributeService attributeService;

    @GetMapping
    public String adminPage(Model model) {
        model.addAttribute("orders", orderService.getOrders());
        return "adminPage";
    }

    @GetMapping(value = "/{id}")
    public String adminOrderPage(@PathVariable(value = "id") Long id, Model model) {
        Order order = orderService.getOrderById(id);
        model.addAttribute("order", order);
        model.addAttribute("attributes", attributeService.getAttributes());
        return "adminOrderPage";
    }

    @PutMapping(value = "/{id}")
    @ResponseBody
    public ResponseEntity changeOrderStatus(@PathVariable(value = "id") Long id,
                                            @RequestBody OrderStatus orderStatus) {
        orderService.updateOrderStatus(id, orderStatus);
        return new ResponseEntity(HttpStatus.OK);
    }

}
