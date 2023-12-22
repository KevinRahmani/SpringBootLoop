package springLoop.controller;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import springLoop.product.model.ArticleEntity;
import springLoop.product.service.ArticleService;
import springLoop.utils.VerifyData;

import java.security.InvalidParameterException;

@Controller
public class SelectModifyServlet {

    @Autowired
    private ArticleService articleService;

    private ArticleEntity article;

    @GetMapping("/selectModify-servlet")
    @ResponseBody
    public String doGet(
            @RequestParam(name = "id")String idParam
    ){
        int id = VerifyData.getParameterAsInt(idParam);
        article = articleService.findById(id);

        if(article == null){
            throw new InvalidParameterException();
        }
        Gson gson = new Gson();
        return gson.toJson(getJsonObject());
    }
    private JsonObject getJsonObject() {
        JsonObject jsonResponse = new JsonObject();

        jsonResponse.addProperty("nom", article.getNom());
        jsonResponse.addProperty("prix", article.getPrix());
        jsonResponse.addProperty("stock", article.getStock());
        jsonResponse.addProperty("couleur", article.getCouleur());
        jsonResponse.addProperty("type", article.getType());
        jsonResponse.addProperty("categorie", article.getCategorie());
        jsonResponse.addProperty("description", article.getDescription());
        return jsonResponse;
    }

}
