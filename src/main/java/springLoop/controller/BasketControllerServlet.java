package springLoop.controller;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import springLoop.basket.service.BasketService;
import springLoop.product.model.ArticleEntity;
import springLoop.product.service.ArticleService;
import springLoop.utils.ProcessBasketService;
import springLoop.utils.VerifyData;

@Controller
public class BasketControllerServlet {

    @Autowired
    private ArticleService articleService;
    @Resource
    public HttpSession session;


    @GetMapping("/basketController-servlet")
    @ResponseBody
    public String doGet(
            @RequestParam(name = "action") String action,
            @RequestParam(name = "id") String idParam
    ){
        BasketService basketService = ProcessBasketService.getBasketSession(session);

        String stat="";
        int stockProduct = 0;

        switch (action){
            case "undo": {
                //delete entire basket
                basketService.clearBasketService();
                stat = "ok";
                break;
            }
            case "deleteRow": {
                //delete a row from basket
                int id = VerifyData.getParameterAsInt(idParam);
                basketService.delete(articleService.findById(id));
                stat = "ok";
                break;
            }

            case "modify": {
                //add or delete 1 from basket article
                String[] parts = idParam.split("-");
                String toDo = parts[0];
                int idTrim = VerifyData.getParameterAsInt(parts[1]);

                ArticleEntity article = articleService.findById(idTrim);

                ArticleEntity articleInBasket = basketService.findById(idTrim);
                int currentQuantity = basketService.getBasket().getArticleQuantity(articleInBasket);

                //if plus add 1 to basket
                if (toDo.equals("plus")) {
                    //verify if article is in basket
                    if (articleInBasket != null) {
                        //verify if enough in stock
                        if (currentQuantity + 1 <= article.getStock()) {
                            basketService.add(articleInBasket);
                            stat = "plus-ok";
                            stockProduct = basketService.getBasket().getArticleQuantity(articleInBasket);
                            //else stop the button
                        } else {
                            stat = "plus-fail";
                        }
                    }
                }

                if (toDo.equals("minus")) {
                    //verify if article is in basket
                    if (articleInBasket != null) {
                        //check if quantity > 0
                        if (currentQuantity > 0) {
                            stat = "minus-ok";
                            //delete 1 from basket, if quantity = 0, delete row automatically
                            basketService.deleteQuantity(articleInBasket, 1);
                            //check if article is still in basket
                            if (basketService.findById(idTrim) == null) {
                                stat = "minus-unset";
                            } else {
                                stockProduct = basketService.getBasket().getArticleQuantity(articleInBasket);
                            }
                        }
                    }
                }
                break;
            }
        }

        //Saving the new basket
        if(action.equals("undo") || VerifyData.isHashMapEmpty(basketService.getBasket().getPanier())){
            session.removeAttribute("basket");
            session.removeAttribute("hashmapBasket");
        } else {
           session.setAttribute("basket", basketService.getBasket());
           session.setAttribute("hashmapBasket", basketService.getBasket().getPanier());
        }

        //Parameters for the json response
        Gson gson = new Gson();
        JsonObject jsonResponse = new JsonObject();

        jsonResponse.addProperty("stat", stat);
        jsonResponse.addProperty("stock", stockProduct);

        return gson.toJson(jsonResponse);
    }



}
