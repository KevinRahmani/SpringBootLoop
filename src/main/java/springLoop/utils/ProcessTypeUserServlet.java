package springLoop.utils;

import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import springLoop.product.model.ArticleEntity;
import springLoop.product.service.ArticleService;
import springLoop.user.model.AdminEntity;
import springLoop.user.model.VendeurEntity;
import springLoop.user.service.VendeurService;

import java.util.List;

import static springLoop.utils.CalculationUtils.calculRevenue;

@Controller
public class ProcessTypeUserServlet {

    @Resource
    private HttpSession session;

    @Autowired
    private ArticleService articleService;

    @Autowired
    private VendeurService vendeurService;

    String typeUser;

    public String processUserType(Model model){
        typeUser = (String) session.getAttribute("type");

        switch (typeUser){
            case "admin": {
                AdminEntity admin = (AdminEntity) session.getAttribute("user");

                //List of all product to modify, list of all vendeur to send, list of all Loop'sproduct
                List<VendeurEntity> listVendeur = (List<VendeurEntity>) vendeurService.findAll();
                List<ArticleEntity> listArticle = (List<ArticleEntity>) articleService.findAll();
                List<ArticleEntity> listArticleLoop = articleService.findAllByVendeur(admin.getNom());
                double revenue = calculRevenue(listArticleLoop);

                //setAttribute
                model.addAttribute("revenue", revenue);
                model.addAttribute("listVendeur", listVendeur);
                model.addAttribute("listArticle", listArticle);
                model.addAttribute("listArticleLoop", listArticleLoop);

                return "/WEB-INF/admin.jsp";
            }

            case "vendeur": {

                VendeurEntity vendeur = (VendeurEntity) session.getAttribute("user");

                //data to transfer for product of seller
                List<ArticleEntity> listVendeur = articleService.findAllByVendeur(vendeur.getNom());
                double revenue = calculRevenue(listVendeur);

                //setAttribute
                model.addAttribute("revenue", revenue);
                model.addAttribute("listVendeur", listVendeur);

                return "/WEB-INF/vendeur.jsp";
            }
            case ("client"):
                return "/WEB-INF/client.jsp";
        }

        return "";
    }

}
