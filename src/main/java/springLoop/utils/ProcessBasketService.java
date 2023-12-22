package springLoop.utils;


import jakarta.servlet.http.HttpSession;
import springLoop.basket.model.Basket;
import springLoop.basket.service.BasketService;

import java.util.Objects;

public class ProcessBasketService {

    public static BasketService getBasketSession(HttpSession session){
        Basket basket = (Basket) session.getAttribute("basket");
        BasketService basketService = null;

        //client has already a basket
        //client doesn't we create new Basket for him
        basketService = new BasketService(Objects.requireNonNullElseGet(basket, Basket::new));

        return basketService;
    }

}
