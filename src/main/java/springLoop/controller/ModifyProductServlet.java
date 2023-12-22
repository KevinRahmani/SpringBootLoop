package springLoop.controller;

import jakarta.annotation.Resource;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import springLoop.product.model.ArticleEntity;
import springLoop.product.service.ArticleService;
import springLoop.utils.ProcessTypeUserServlet;
import springLoop.utils.VerifyData;

import java.io.IOException;


@Controller
public class ModifyProductServlet{


    @Autowired
    private ArticleService articleService;

    @Autowired
    private ProcessTypeUserServlet userTypeProcessor;

    @GetMapping("/modifyProduct-servlet")
    public String doGet(Model model) {
        return userTypeProcessor.processUserType(model);
    }

        @PostMapping("/modifyProduct-servlet")
        public String doPost(@RequestParam(name = "list_produit_modif")String list_produit_modif,
                             @RequestParam(name = "nom")String nom,
                             @RequestParam(name = "prix")String Strprix,
                             @RequestParam(name = "stock")String Strstock,
                             @RequestParam(name = "type")String type,
                             @RequestParam(name = "couleur")String couleur,
                             @RequestParam(name = "description")String description,
                             @RequestParam(name = "categorie-modif")String categorie,
                             Model model) throws ServletException {
            try{
                //find Article to update
                int id = VerifyData.getParameterAsIntV2(list_produit_modif);
                ArticleEntity article = articleService.findById(id);

                //if all parameters are good
                int prix = VerifyData.getParameterAsInt(Strprix);
                int stock = VerifyData.getParameterAsInt(Strstock);

                if (VerifyData.verifyParameters(nom, list_produit_modif, type, couleur, description, categorie) && article != null) {

                    article.setNom(nom);
                    article.setPrix(prix);
                    article.setStock(stock);
                    article.setType(type);
                    article.setCouleur(couleur);
                    article.setDescription(description);
                    article.setCategorie(categorie);

                    articleService.save(article);
                } else {
                    model.addAttribute("errModify", "Veuillez correctement valider les champs");
                }
            } catch(NumberFormatException e){
                model.addAttribute("errModify", "Veuillez correctement rentrer des nombres");
            }
            return userTypeProcessor.processUserType(model);
        }


}

