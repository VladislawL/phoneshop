package com.es.core.cart;

import com.es.core.model.phone.Phone;
import com.es.core.services.PhoneService;
import com.es.core.services.PriceCalculator;
import com.es.core.services.StockService;
import com.es.core.validators.QuantityValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

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

    @Autowired
    private PhoneService phoneService;

    private static final String NOT_ENOUGH_STOCK_CODE = "quantity.greaterThanStock";

    private static final String CART_ITEM_QUANTITY_FIELD = "cartItems[%s].quantity";
    private static final String CART_ITEM_PHONE_ID_FIELD = "cartItems[%s].phoneId";

    @Override
    public Cart getCart() {
        return cart;
    }

    @Override
    public void addPhone(Long phoneId, Long quantity) throws QuantityValidationException {
        Optional<CartItem> cartItem = findCartItem(phoneId);

        addOrUpdateCartItem(cartItem, phoneId, quantity);

        priceCalculatorService.calculateCart(cart);
    }

    @Override
    public void addCartItems(List<CartItem> cartItems, Errors errors) {
        int i = 0;
        for (CartItem cartItem : cartItems) {
            if (errors.getFieldError(String.format(CART_ITEM_PHONE_ID_FIELD, i)) == null &&
                    errors.getFieldError(String.format(CART_ITEM_QUANTITY_FIELD, i)) == null) {
                Optional<CartItem> optionalCartItem = findCartItem(cartItem.getPhoneId());
                addOrUpdateCartItem(optionalCartItem, cartItem.getPhoneId(), cartItem.getQuantity());
                cartItem.setPhoneId(0);
                cartItem.setQuantity(0);
            }
            i++;
        }
    }

    @Override
    public void updatePhone(Map<Long, Long> items) throws QuantityValidationException {
        for (Long phoneId : items.keySet()) {
            Optional<CartItem> cartItem = findCartItem(phoneId);
            if (items.get(phoneId) > 0) {
                addOrUpdateCartItem(cartItem, phoneId, items.get(phoneId));
            } else {
                if (cartItem.isPresent()) {
                    removeCartItem(phoneId);
                }
            }
        }
        priceCalculatorService.calculateCart(cart);
    }

    private void addOrUpdateCartItem(Optional<CartItem> cartItem, Long newId, Long quantity) {
        if (cartItem.isPresent()) {
            updateExistingCartItem(cartItem.get(), quantity);
        } else {
            addNewCartItem(newId, quantity);
        }
    }

    @Override
    public List<Phone> getPhones() {
        List<Long> ids = cart.getCartItems().stream()
                .map(CartItem::getPhoneId)
                .collect(Collectors.toList());

        return phoneService.getPhonesById(ids);
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
        removeCartItem(phoneId);
        priceCalculatorService.calculateCart(cart);
    }

    private void removeCartItem(Long phoneId) {
        cart.getCartItems().removeIf(cartItem -> cartItem.getPhoneId().equals(phoneId));
    }

    private void checkQuantity(long phoneId, long quantity) {
        if (!quantityValidator.isValid(phoneId, quantity)) {
            throw new QuantityValidationException(NOT_ENOUGH_STOCK_CODE, stockService.getStock(phoneId));
        }
    }

    private Optional<CartItem> findCartItem(Long phoneId) {
        return cart.getCartItems().stream()
                .filter(item -> item.getPhoneId().equals(phoneId))
                .findFirst();
    }

}
