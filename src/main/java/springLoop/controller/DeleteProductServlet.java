package springLoop.controller;


import jakarta.servlet.ServletException;
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


@Controller
public class DeleteProductServlet {

    @Autowired
    private ArticleService articleService;

    @Autowired
    private ProcessTypeUserServlet userTypeProcessor;

    @GetMapping("/deleteProduct-servlet")
    public String doGet(Model model) {
        return userTypeProcessor.processUserType(model);
    }
    @PostMapping("/deleteProduct-servlet")
    public String doPost(@RequestParam(name = "list_produit_supp")String list_produit_supp, Model model) throws ServletException{

        //find Article to update
        int id = VerifyData.getParameterAsInt(list_produit_supp);
        ArticleEntity article = articleService.findById(id);

        if(article == null){
            throw new ServletException();
        }

        articleService.delete(article);
        return userTypeProcessor.processUserType(model);
    }
}
