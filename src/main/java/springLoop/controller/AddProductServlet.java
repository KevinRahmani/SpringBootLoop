package springLoop.controller;

import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import springLoop.product.model.ArticleEntity;
import springLoop.product.service.ArticleService;
import springLoop.user.model.AdminEntity;
import springLoop.user.model.VendeurEntity;
import springLoop.utils.ProcessTypeUserServlet;
import springLoop.utils.VerifyData;

@Controller
public class AddProductServlet {

    @Resource
    private HttpSession session;

    @Autowired
    private ArticleService articleService;

    @Autowired
    private ProcessTypeUserServlet userTypeProcessor;

    @GetMapping("/addProduct-servlet")
    public String doGet(Model model) {
        return userTypeProcessor.processUserType(model);
    }

    @PostMapping("/addProduct-servlet")
    public String doPost(@RequestParam(name = "nom")String nom,
                         @RequestParam(name = "marque")String marque,
                         @RequestParam(name = "prix")String Strprix,
                         @RequestParam(name = "stock")String Strstock,
                         @RequestParam(name = "type")String type,
                         @RequestParam(name = "couleur")String couleur,
                         @RequestParam(name = "description")String description,
                         @RequestParam(name = "categorie-add")String categorie,
                         Model model) {
        String typeUser = (String) session.getAttribute("type");
        int prix = VerifyData.getParameterAsInt(Strprix);
        int stock = VerifyData.getParameterAsInt(Strstock);

        if(VerifyData.verifyParameters(nom,marque,type,couleur,description,categorie)) {
            ArticleEntity article = new ArticleEntity();
            String pathImg = "img/";
            if(typeUser.equals("vendeur")){
                VendeurEntity vendeur = (VendeurEntity) session.getAttribute("user");
                article.setUp(nom, marque, prix, vendeur.getNom(), stock, type, couleur, description, 0, pathImg,categorie);
            } else if(typeUser.equals("admin")){
                AdminEntity admin = (AdminEntity) session.getAttribute("user");
                article.setUp(nom, marque, prix, admin.getNom(), stock, type, couleur, description, 0, pathImg,categorie);
            }
            articleService.save(article);
        } else {
            model.addAttribute("errAdd", "Veuillez remplir correctement les champs");
        }
        return userTypeProcessor.processUserType(model);

    }
}
