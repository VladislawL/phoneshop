package com.es.phoneshop.web.controller.pages;

import com.es.core.cart.Cart;
import com.es.core.cart.CartItem;
import com.es.core.cart.CartService;
import com.es.core.cart.MiniCart;
import com.es.core.services.AttributeService;
import com.es.core.services.CartPageDataService;
import com.es.core.utils.PriceFormatter;
import com.es.core.services.CartPageData;
import com.es.core.validators.CartPageDataValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/cart")
public class CartPageController {

    @Resource
    private CartService cartService;

    @Autowired
    private AttributeService attributeService;

    @Autowired
    private PriceFormatter priceFormatter;

    @Autowired
    private CartPageDataService cartPageDataService;

    @Autowired
    private CartPageDataValidator cartPageDataValidator;

    private Cart cart;

    @PostConstruct
    public void postConstruct() {
        cart = cartService.getCart();
    }

    @RequestMapping(method = RequestMethod.GET)
    public String getCart(Model model) {
        CartPageData cartPageData = new CartPageData();
        cartPageDataService.fillCartPageData(cartPageData);

        model.addAttribute("cart", cart);
        model.addAttribute("attributes", attributeService.getAttributes());
        model.addAttribute("phones", cartService.getPhones());
        model.addAttribute("currencySymbol", priceFormatter.getDefaultCurrency());
        model.addAttribute("cartPageData", cartPageData);

        return "cartPage";
    }

    @RequestMapping(method = RequestMethod.PUT)
    public String updateCart(@ModelAttribute CartPageData cartPageData, Errors errors, Model model) {
        cartPageDataValidator.validate(cartPageData, errors);
        if (!errors.hasErrors()) {
            cartService.updatePhone(cartPageData.getCartItems());

            return "redirect:cart";
        } else {
            model.addAttribute("cart", cart);
            model.addAttribute("attributes", attributeService.getAttributes());
            model.addAttribute("phones", cartService.getPhones());
            model.addAttribute("currencySymbol", priceFormatter.getDefaultCurrency());
            model.addAttribute("cartPageData", cartPageData);
            return "cartPage";
        }
    }

    @DeleteMapping
    @ResponseBody
    public Map<String, Object> removeCartItem(@RequestBody Long phoneId) {
        cartService.remove(phoneId);
        MiniCart miniCart = MiniCart.fromCart(cart);

        Map<String, Object> response = new HashMap<>();
        response.put("miniCart", miniCart);
        response.put("currency", priceFormatter.getDefaultCurrency());

        return response;
    }

}
