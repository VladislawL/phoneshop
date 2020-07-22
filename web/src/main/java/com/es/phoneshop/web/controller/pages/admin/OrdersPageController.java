package com.es.phoneshop.web.controller.pages.admin;

import com.es.core.model.order.Order;
import com.es.core.model.order.OrderStatus;
import com.es.core.order.OrderService;
import com.es.core.services.AttributeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping(value = "/admin/orders")
public class OrdersPageController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private AttributeService attributeService;

    @RequestMapping(method = RequestMethod.GET)
    public String adminPage(Model model) {
        model.addAttribute("orders", orderService.getOrders());
        return "adminPage";
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public String adminOrderPage(@PathVariable(value = "id") Long id, Model model) {
        Order order = orderService.getOrderById(id);
        model.addAttribute("order", order);
        model.addAttribute("attributes", attributeService.getAttributes());
        return "adminOrderPage";
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.POST)
    public String changeOrderStatus(@PathVariable(value = "id") Long id,
                                    @RequestParam("orderStatus") OrderStatus orderStatus) {
        Order order = orderService.getOrderById(id);
        order.setStatus(orderStatus);
        orderService.updateOrder(order);
        return "redirect:/admin/orders/" + id;
    }

}
