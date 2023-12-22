package springLoop.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import springLoop.product.model.ArticleEntity;
import springLoop.product.service.ArticleService;
import springLoop.utils.VerifyData;

@Controller
public class VerifyStockServlet {

    @Autowired
    private ArticleService articleService;

    @GetMapping("/verifyStock-servlet")
    @ResponseBody
    public String doGet(
            @RequestParam(name = "id")String idParam
    ){
        int idProduct = VerifyData.getParameterAsInt(idParam);
        ArticleEntity product = articleService.findById(idProduct);
        int stock = product.getStock();

        return String.valueOf(stock);
    }
}
