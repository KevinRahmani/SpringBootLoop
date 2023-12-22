package springLoop.product.service;

import springLoop.product.model.ArticleEntity;
import springLoop.product.repository.ArticleRepository;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Data
@Service
public class ArticleService {

    private final ArticleRepository articleRepository;
    @Autowired
    public ArticleService(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    public ArticleEntity findById(int id) {
        return articleRepository.findById(id).orElse(null);
    }

    public void save(ArticleEntity a){
        articleRepository.save(a);
    }

    public void delete(ArticleEntity a){
        articleRepository.delete(a);
    }

    public Iterable<ArticleEntity> findAll() {
        return articleRepository.findAll();
    }

    public Iterable<ArticleEntity> findAllByFilters(ArticleEntity filters) {
        ArticleSpecification spec = new ArticleSpecification(filters);
        return articleRepository.findAll(spec);
    }

    public ArticleEntity findTopByCategorieOrderBySalesDesc(String category) {
        return articleRepository.findTopByCategorieOrderBySalesDesc(category);
    }

    public List<ArticleEntity> findAllByCategorie(String categorie){
        return articleRepository.findAllByCategorie(categorie);
    }

    public List<ArticleEntity> findAllByNom(String nom){
        return articleRepository.findAllByNom(nom);
    }

    public List<ArticleEntity> findAllByMarque(String marque){
        return articleRepository.findAllByMarque(marque);
    }

    public List<ArticleEntity> findAllByVendeur(String vendeur){
        return articleRepository.findAllByVendeur(vendeur);
    }

    public List<ArticleEntity> findAllByType(String type) {
        return articleRepository.findAllByType(type);
    }

    public List<ArticleEntity> findAllByCouleur(String couleur) {
        return articleRepository.findAllByCouleur(couleur);
    }

    public List<ArticleEntity> findAllByCategorieAndMarqueAndType(String categorie, String marque, String type) {
        return articleRepository.findAllByCategorieAndMarqueAndType(categorie, marque, type);
    }

    public List<ArticleEntity> findAllByCategorieOrderByPrixAsc(String categorie) {
        return articleRepository.findAllByCategorieOrderByPrixAsc(categorie);
    }

    public List<ArticleEntity> findAllByCategorieOrderByPrixDesc(String categorie) {
        return articleRepository.findAllByCategorieOrderByPrixDesc(categorie);
    }

    public List<ArticleEntity> findAllByCategorieOrderByMarqueAsc(String categorie) {
        return articleRepository.findAllByCategorieOrderByMarqueAsc(categorie);
    }

}
