package springLoop.controller;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import springLoop.product.model.ArticleEntity;
import springLoop.product.service.ArticleService;

import java.util.List;

@Controller
public class ModifyTypeServlet {

    @Autowired
    private ArticleService articleService;

    @GetMapping("/modifyType-servlet")
    @ResponseBody
    public String doGet(
            @RequestParam(name = "option") String option,
            @RequestParam(name = "categorie") String categorie
    ){
        List<ArticleEntity> data = null;

        switch(option){
            case "croissant":
                data = articleService.findAllByCategorieOrderByPrixAsc(categorie);
                break;
            case "decroissant" :
                data = articleService.findAllByCategorieOrderByPrixDesc(categorie);
                break;
            case "marque" :
                data = articleService.findAllByCategorieOrderByMarqueAsc(categorie);
                break;
            case "default":
                data = articleService.findAllByCategorie(categorie);
                break;
        }

        Gson gson = new Gson();
        return gson.toJson(data);
    }
}
