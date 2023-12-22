package springLoop.controller;

import jakarta.annotation.Resource;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import springLoop.basket.service.BasketService;
import springLoop.product.model.ArticleEntity;
import springLoop.product.service.ArticleService;
import springLoop.user.model.ClientEntity;
import springLoop.utils.ProcessBasketService;
import springLoop.utils.VerifyData;

import java.util.HashMap;
import java.util.List;

@Controller
public class RedirectionServlet{

    @Autowired
    private  ArticleService articleService;
    @Resource
    private HttpSession session;
    private HashMap<String, String> fullNameCategoryMap;

    @RequestMapping("/redirection-servlet")
    public String handlePageRequest(@RequestParam(name = "requestedPage", defaultValue = "index") String requestedPage,
                                    @RequestParam(name = "product", required = false) String product,
                                    @RequestParam(name = "categorie", required = false) String categorie,
                                    Model model) {

        if(requestedPage.equals("index")) {
            return getIndex(model);
        }
        if(requestedPage.equals("about_us")){
            return "/WEB-INF/about_us.jsp";
        }
        if(requestedPage.equals("cgv")){
            return "/WEB-INF/cgv.jsp";
        }
        if(requestedPage.equals("collaborator")){
            return "/WEB-INF/collaborator.jsp";
        }
        if(requestedPage.equals("product")){
            return getProduct(model, product);
        }
        if(requestedPage.equals("categorie")){
            return getCategorie(model, categorie);
        }
        if(requestedPage.equals("basket")){
            return getBasket();
        }
        if(requestedPage.equals("invoice")){
            return getInvoice(model);
        }
        return getIndex(model);
    }



    @GetMapping("/")
    public String getIndex(Model model){
        //Smartphone Carousel List
        List<ArticleEntity> smartphoneList = articleService.findAllByCategorieAndMarqueAndType("ht", "Apple", "Téléphones");

        //Watch Carousel List
        List<ArticleEntity> watchList = articleService.findAllByType("Montres connectées");

        //Set Attribute
        model.addAttribute("smartphoneList", smartphoneList);
        model.addAttribute("watchList", watchList);

        return "/WEB-INF/index.jsp";

    }


    @GetMapping("/categorie")
    public String getCategorie(Model model, String categorie){

        List<ArticleEntity> listCategorie= articleService.findAllByCategorie(categorie);

        String fullNameCategory = associatedCategory(categorie);

        //Set Attribute
        model.addAttribute("listCategorie", listCategorie);
        model.addAttribute("categorie", categorie);
        model.addAttribute("fullNameCategory", fullNameCategory);

        System.out.println("categorie demandée :" + categorie);

        return "/WEB-INF/categorie.jsp";
    }

    @GetMapping("/product")
    public String getProduct(Model model, String product){
        int productIdAsInt = VerifyData.getParameterAsInt(product);
        ArticleEntity article = articleService.findById(productIdAsInt);
        model.addAttribute("article", article);
        return "/WEB-INF/product.jsp";
    }

    @GetMapping("/basket")
    public String getBasket() {
        return "/WEB-INF/basket.jsp";
    }

    @GetMapping("/invoice")
    public String getInvoice(Model model) {
        String userType = (String) session.getAttribute("type");

        if(userType == null || userType.equals("admin") || userType.equals("vendeur")){
            model.addAttribute("errAccess", "Veuillez vous connecter avec un compte client pour passer commande !");
            getBasket();

        } else if(userType.equals("client")){
            ClientEntity client = (ClientEntity) session.getAttribute("user");
            BasketService basketService = ProcessBasketService.getBasketSession(session);

            double totalPriceHT = basketService.getTotalPriceHT();
            double totalPriceTTC = basketService.getTotalPrice();

            //necessary point to get reduction
            if(client.getFidelity() > 500){
                model.addAttribute("fidelity", true);
            }

            model.addAttribute("totalPriceHT", totalPriceHT);
            model.addAttribute("totalPriceTTC", totalPriceTTC);
        }
        return "/WEB-INF/invoice.jsp";
    }

    private void fillCategorie(){
        fullNameCategoryMap = new HashMap<>();
        fullNameCategoryMap.put("automobile", "automobile");
        fullNameCategoryMap.put("ht", "High-Tech");
        fullNameCategoryMap.put("bja", "Bricolage, jardin et animalerie");
        fullNameCategoryMap.put("cm", "Cuisine et Maison");
        fullNameCategoryMap.put("mdb", "Musique, DVD et Blu-ray");
        fullNameCategoryMap.put("sports", "Sports & Loisirs");
        fullNameCategoryMap.put("livre", "Livres");
        fullNameCategoryMap.put("vetements", "Vêtements");
    }

    private String associatedCategory(String requestedCategory) {
        fillCategorie();
        return fullNameCategoryMap.get(requestedCategory) != null ? fullNameCategoryMap.get(requestedCategory) : null;
    }

}