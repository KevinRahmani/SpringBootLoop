package springLoop.product.service;


import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;
import springLoop.product.model.ArticleEntity;

public class ArticleSpecification implements Specification<ArticleEntity> {

    private final ArticleEntity filters;

    public ArticleSpecification(ArticleEntity filters) {
        this.filters = filters;
    }

    @Override
    public Predicate toPredicate(Root<ArticleEntity> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        Predicate predicate = criteriaBuilder.conjunction();

        if (filters.getNom() != null) {
            predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("nom"), filters.getNom()));
        }
        if (filters.getMarque() != null) {
            predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("marque"), filters.getMarque()));
        }
        if (filters.getPrix() != 0) {
            predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("prix"), filters.getPrix()));
        }
        if (filters.getVendeur() != null) {
            predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("vendeur"), filters.getVendeur()));
        }
        if (filters.getStock() != 0) {
            predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("stock"), filters.getStock()));
        }
        if (filters.getType() != null) {
            predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("type"), filters.getType()));
        }
        if (filters.getCouleur() != null) {
            predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("couleur"), filters.getCouleur()));
        }
        if (filters.getDescription() != null) {
            predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("description"), filters.getDescription()));
        }
        if (filters.getSales() != 0) {
            predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("sales"), filters.getSales()));
        }
        if (filters.getImage() != null) {
            predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("image"), filters.getImage()));
        }
        if (filters.getCategorie() != null) {
            predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("categorie"), filters.getCategorie()));
        }

        return predicate;
    }
}
