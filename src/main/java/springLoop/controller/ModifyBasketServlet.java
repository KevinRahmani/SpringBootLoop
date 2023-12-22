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
public class ModifyBasketServlet {

    @Autowired
    private ArticleService articleService;

    @Resource
    public HttpSession session;

    @GetMapping("/modifyBasket-servlet")
    @ResponseBody
    public String doGet(
            @RequestParam(name = "id") String idParam,
            @RequestParam(name = "quantity") String quantityParam
    ){
        BasketService basketService = ProcessBasketService.getBasketSession(session);
        JsonObject jsonResponse = new JsonObject();
        Gson gson = new Gson();

        int id = VerifyData.getParameterAsInt(idParam);
        int quantity = VerifyData.getParameterAsInt(quantityParam);

        ArticleEntity article = articleService.findById(id);
        ArticleEntity articleInBasket = basketService.findById(id);

        String status = "ok";
        int stock = 0;

        if (article == null || quantity > article.getStock()) {
            stock = (article != null) ? article.getStock() : 0;
            status = "not-enough";
        } else {
            if (articleInBasket != null) {
                int currentQuantity = basketService.getBasket().getArticleQuantity(articleInBasket);
                if (currentQuantity + quantity <= article.getStock()) {
                    basketService.addQuantity(articleInBasket,quantity);
                } else {
                    stock = article.getStock() - currentQuantity;
                    status = "not-enough";
                }
            } else {
                basketService.addQuantity(article, quantity);
            }
        }

        //saving the new Basket
        session.setAttribute("basket", basketService.getBasket());
        session.setAttribute("hashmapBasket", basketService.getBasket().getPanier());

        //Parameters for the json response
        jsonResponse.addProperty("status", status);
        jsonResponse.addProperty("stock", stock);

        return gson.toJson(jsonResponse);
    }

}
