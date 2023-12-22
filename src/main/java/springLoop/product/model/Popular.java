package springLoop.product.model;

import jakarta.persistence.Entity;
import lombok.Data;

@Data
public class Popular {

    public String categorie;
    public String citation;
    public ArticleEntity article;

    public Popular(String categorie, String citation, ArticleEntity article) {
        this.categorie = categorie;
        this.citation = citation;
        this.article = article;
    }

}
