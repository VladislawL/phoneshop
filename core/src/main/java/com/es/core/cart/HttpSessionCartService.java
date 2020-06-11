package com.es.core.cart;

import com.es.core.services.PriceCalculator;
import com.es.core.services.StockService;
import com.es.core.validators.QuantityValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class HttpSessionCartService implements CartService {

    @Autowired
    private Cart cart;

    @Autowired
    private PriceCalculator priceCalculatorService;

    @Autowired
    private QuantityValidator quantityValidator;

    @Autowired
    private StockService stockService;

    @Override
    public Cart getCart() {
        return cart;
    }

    @Override
    public void addPhone(Long phoneId, Long quantity) throws QuantityValidationException {
        Optional<CartItem> cartItem = findCartItem(phoneId);
        if (cartItem.isPresent()) {
            updateExistingCartItem(cartItem.get(), quantity);
        } else {
            addNewCartItem(phoneId, quantity);
        }
        cart.setSubTotalPrice(priceCalculatorService.calculateSubtotalPrice(cart));
    }

    @Override
    public void updatePhone(Map<Long, Long> items) throws QuantityValidationException {
        for (Long phoneId : items.keySet()) {
            Optional<CartItem> cartItem = findCartItem(phoneId);
            if (cartItem.isPresent()) {
                updateExistingCartItem(cartItem.get(), items.get(phoneId));
            } else {
                addNewCartItem(phoneId, items.get(phoneId));
            }
        }
        cart.setSubTotalPrice(priceCalculatorService.calculateSubtotalPrice(cart));
    }

    private void updateExistingCartItem(CartItem oldCartItem, Long quantity) {
        checkQuantity(oldCartItem.getPhoneId(), quantity);

        oldCartItem.setQuantity(quantity);
    }

    private void addNewCartItem(Long phoneId, Long quantity) {
        checkQuantity(phoneId, quantity);

        CartItem cartItem = new CartItem(phoneId, quantity);

        List<CartItem> cartItems = cart.getCartItems();
        cartItems.add(cartItem);
    }

    @Override
    public void remove(Long phoneId) {
        cart.getCartItems().removeIf(cartItem -> cartItem.getPhoneId().equals(phoneId));
        cart.setSubTotalPrice(priceCalculatorService.calculateSubtotalPrice(cart));
    }

    private void checkQuantity(long phoneId, long quantity) {
        if (!quantityValidator.isValid(phoneId, quantity)) {
            long stock = stockService.getStock(phoneId);
            throw new QuantityValidationException("Not enough stock, available " + stock);
        }
    }

    private Optional<CartItem> findCartItem(Long phoneId) {
        return cart.getCartItems().stream()
                .filter(item -> item.getPhoneId().equals(phoneId))
                .findFirst();
    }

}
