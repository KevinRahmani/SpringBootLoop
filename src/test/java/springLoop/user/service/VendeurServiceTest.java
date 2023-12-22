package springLoop.user.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;
import springLoop.user.model.VendeurEntity;

import java.sql.Timestamp;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@ExtendWith(SpringExtension.class)
class VendeurServiceTest {

    @Autowired
    private VendeurService vendeurService;

    private VendeurEntity vendeurEntity;

    @BeforeEach
    void initialize() {
        vendeurEntity = new VendeurEntity();
        vendeurEntity.setPassword("default");
        vendeurEntity.setMail("default");
        vendeurEntity.setNom("default");
        vendeurEntity.setDatesignup(new Timestamp(System.currentTimeMillis()));
        vendeurEntity.setAdresse("default");
        vendeurEntity.setNbtotalsales(0);
        vendeurEntity.setModifyRight(1);
        vendeurEntity.setAddRight(1);
        vendeurEntity.setRemoveRight(1);
    }

    @Test
    @Transactional("userTransactionManager")
    void findById() {
        //GIVEN
        initialize();
        //WHEN
        vendeurService.save(vendeurEntity);
        VendeurEntity clientFound = vendeurService.findById(vendeurEntity.getId());
        //THEN
        assertNotNull(clientFound);
        assertEquals(vendeurEntity, clientFound);
        vendeurService.delete(clientFound);
    }

    @Test
    @Transactional("userTransactionManager")
    @DisplayName("should add")
    void save() {
        // GIVEN
        initialize();
        // WHEN
        vendeurService.save(vendeurEntity);
        // THEN
        VendeurEntity savedEntity = vendeurService.findById(vendeurEntity.getId());
        assertNotNull(savedEntity);
        assertEquals(savedEntity, vendeurEntity);
        vendeurService.delete(vendeurEntity);
    }


    @Test
    @Transactional("userTransactionManager")
    @DisplayName("should delete")
    void delete() {
        // GIVEN
        initialize();
        vendeurService.save(vendeurEntity);
        // WHEN
        int entityId = vendeurEntity.getId();
        vendeurService.delete(vendeurEntity);
        // THEN
        assertNull(vendeurService.findById(entityId));
    }

    @Test
    @Transactional("userTransactionManager")
    @DisplayName("should findAll")
    void findAll() {
        //GIVEN
        //WHEN
        List<VendeurEntity> result = (List<VendeurEntity>) vendeurService.findAll();
        //THEN
        assertNotNull(result);
        assertFalse(result.isEmpty());
    }
}