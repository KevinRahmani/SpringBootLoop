package springLoop.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import springLoop.product.model.Popular;
import springLoop.product.service.ArticleService;

import java.util.ArrayList;
import java.util.List;

@Controller
public class PopularProductServlet {

    @Autowired
    private ArticleService articleService;


    @GetMapping("/popularProduct-servlet")
    public String doGet(Model model) {

        //Parameters to send
        List<Popular> popularList = new ArrayList<>(); //Article is the article with the best sales and String is just a plain citation

        //auto
        popularList.add(new Popular("Automobile" , "Roulez dans nos meilleures routières", articleService.findTopByCategorieOrderBySalesDesc("automobile")));

        //Bja
        popularList.add(new Popular("Bricolage, jardin et Maison" , "Bricolage et jardin ? Amusez-vous bien !", articleService.findTopByCategorieOrderBySalesDesc("bja")));

        //cm
        popularList.add(new Popular("Cuisine et Maison" , "Pour des femmes heureuses !", articleService.findTopByCategorieOrderBySalesDesc("cm")));

        //ht
        popularList.add(new Popular("High-Tech" , "Pour des Hommes heureux", articleService.findTopByCategorieOrderBySalesDesc("ht")));

        //livre
        popularList.add(new Popular("livre" , "Une envie de voyager ?", articleService.findTopByCategorieOrderBySalesDesc("livre")));

        //mdb
        popularList.add(new Popular("Musique, DVD et Blu-ray" , "Un plaisir pour les yeux et les oreilles !", articleService.findTopByCategorieOrderBySalesDesc("mdb")));

        //sports
        popularList.add(new Popular("Sports" ,  "Devenez la meilleure version de vous-même !", articleService.findTopByCategorieOrderBySalesDesc("sports")));

        //vetements
        popularList.add(new Popular("Vetements" , "Des beaux habits pour une belle journée", articleService.findTopByCategorieOrderBySalesDesc("vetements")));


        model.addAttribute("popularList", popularList);
        return "WEB-INF/popularProduct.jsp";
    }
}
