package springLoop.product.repository;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import springLoop.product.model.ArticleEntity;

import java.util.List;
import java.util.Optional;

@Repository
public interface ArticleRepository extends JpaRepository<ArticleEntity, Integer> {

    List<ArticleEntity> findAll(Specification<ArticleEntity> specification);
    ArticleEntity findTopByCategorieOrderBySalesDesc(String category);

    Optional<ArticleEntity> findById(int id);
    List<ArticleEntity> findAllByCategorie(String categorie);
    List<ArticleEntity> findAllByNom(String nom);
    List<ArticleEntity> findAllByMarque(String marque);
    List<ArticleEntity> findAllByVendeur(String vendeur);
    List<ArticleEntity> findAllByType(String type);
    List<ArticleEntity> findAllByCouleur(String couleur);
    List<ArticleEntity> findAllByCategorieOrderByPrixAsc(String categorie);
    List<ArticleEntity> findAllByCategorieAndMarqueAndType(String categorie, String marque, String type);

    List<ArticleEntity> findAllByCategorieOrderByPrixDesc(String categorie);
    List<ArticleEntity> findAllByCategorieOrderByMarqueAsc(String categorie);
}
