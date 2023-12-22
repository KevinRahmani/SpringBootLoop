package springLoop.controller;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import springLoop.product.model.ArticleEntity;
import springLoop.product.service.ArticleService;
import springLoop.utils.CalculationUtils;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Controller
public class SearchBarControllerServlet {


    @Autowired
    private ArticleService articleService;

    @GetMapping("/searchBarController-servlet")
    @ResponseBody
    public String handlePageRequest(@RequestParam(name = "task", defaultValue = "index") String task,
                                    Model model) {

        try {

            //Parameters
            String valueResearch = task;
            String result = "";
            String status = "fail";

            if(valueResearch != null && !valueResearch.isEmpty()){
                //downcast the research to lower case for better efficiency
                valueResearch = valueResearch.toLowerCase();
                //search in the hashmap of categories
                result = searchCategories(valueResearch);

                //search for product if result is empty ie no categories
                if(result.isEmpty()){
                    result = searchProducts(valueResearch);
                }

                //if result not empty status = ok => found something
                if(!result.isEmpty()){
                    status = "ok";
                }
            } else {
                result = "Veuillez rentrer une recherche";
            }

            //Parameters to send for ajax response*
            Gson gson = new Gson();
            JsonObject jsonResponse = new JsonObject();

            jsonResponse.addProperty("status", status);
            jsonResponse.addProperty("result", result);

            return gson.toJson(jsonResponse);


        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Erreur lors du traitement de la requête", e);
        }


    }

    private String searchCategories(String valueResearch){
        String result = "";
        //Hashmap with the name of categories and it's value
        HashMap<String, String> categories = getCategoriesHashMap();

        //search for categories
        for (Map.Entry<String,String> entry : categories.entrySet()) {
            double similarity = CalculationUtils.calculateSimilarity(entry.getKey(), valueResearch);
            if (similarity > 75.0) {
                result = "redirection-servlet?requestedPage=categorie&categorie="+entry.getValue();
            }
        }

        return result;
    }

    private String searchProducts(String valueResearch){
        String result = "";
        List<ArticleEntity> articleList = (List<ArticleEntity>) articleService.findAll();
        for (ArticleEntity article : articleList){
            double similarity = CalculationUtils.calculateSimilarity(article.getNom().toLowerCase(), valueResearch);
            if (similarity > 75.0) {
                result = "redirection-servlet?requestedPage=product&product="+article.getId();
            }
        }
        return result;
    }

    private static HashMap<String, String> getCategoriesHashMap() {
        HashMap<String,String> categories= new HashMap<>();
        categories.put("automobile", "automobile");
        categories.put("bricolage, jardin et animalerie", "bja");
        categories.put("cuisine et maison", "cm");
        categories.put("high-tech", "ht");
        categories.put("livre", "livre");
        categories.put("musique, dvd et blu-ray", "mdb");
        categories.put("sports", "sports");
        categories.put("vetements", "vetements");
        return categories;
    }

}

