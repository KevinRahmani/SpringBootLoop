package springLoop.product.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;
import springLoop.product.model.ArticleEntity;
import springLoop.product.service.ArticleService;
import springLoop.product.service.ArticleSpecification;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@ExtendWith(SpringExtension.class)
class AvecArticleServiceTest {

    @Autowired
    private ArticleService articleService;

    private ArticleEntity stockEntity;

    void initialize(){
        stockEntity = new ArticleEntity();
        stockEntity.setCouleur("default");
        stockEntity.setDescription("default");
        stockEntity.setNom("default");
        stockEntity.setMarque("default");
        stockEntity.setImage("default");
        stockEntity.setPrix(0);
        stockEntity.setSales(0);
        stockEntity.setStock(0);
        stockEntity.setType("default");
        stockEntity.setVendeur("default");
        stockEntity.setCategorie("default");
    }
    @Test
    @DisplayName("Should findById")
    @Transactional
    void findById() {
        //GIVEN
        initialize();
        //WHEN
        articleService.save(stockEntity);
        ArticleEntity articleFound = articleService.findById(stockEntity.getId());
        //THEN
        assertNotNull(articleFound);
        assertEquals(stockEntity, articleFound);
        articleService.delete(stockEntity);
    }

    @Test
    @DisplayName("should add")
    @Transactional
    void save() {
        // GIVEN
        initialize();
        // WHEN
        articleService.save(stockEntity);
        // THEN
        ArticleEntity savedEntity = articleService.findById(stockEntity.getId());
        assertNotNull(savedEntity);
        assertEquals(savedEntity, stockEntity);
        articleService.delete(stockEntity);
    }


    @Test
    @Transactional
    @DisplayName("Should delete")
    void delete() {
        // GIVEN
        initialize();
        articleService.save(stockEntity);
        // WHEN
        articleService.delete(stockEntity);
        // THEN
        assertNull(articleService.findById(stockEntity.getId()));
    }

    @Test
    @DisplayName("should findAll")
    @Transactional
    void findAll() {
        //GIVEN
        //WHEN
        List<ArticleEntity> result = (List<ArticleEntity>) articleService.findAll();
        //THEN
        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertTrue(result.size() > 400);
    }

    @Test
    @DisplayName("should findAll")
    @Transactional
    void findAllByFilters(){
        //GIVEN
        initialize();
        articleService.save(stockEntity);
        //WHEN
        List<ArticleEntity> result = (List<ArticleEntity>) articleService.findAllByFilters(stockEntity);
        //THEN
        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(1,result.size());
        articleService.delete(stockEntity);
    }

    @Test
    @DisplayName("Should findByCategorieAndMaxSales")
    @Transactional
    void findByCategorieAndMaxSales() {
        //GIVEN
        initialize();
        stockEntity.setSales(10000); //suppose it's the highest sale
        articleService.save(stockEntity);
        //WHEN
        ArticleEntity result = articleService.findTopByCategorieOrderBySalesDesc(stockEntity.getCategorie());
        //THEN
        assertNotNull(result);
        assertEquals(result,stockEntity);
        articleService.delete(stockEntity);
    }



}
