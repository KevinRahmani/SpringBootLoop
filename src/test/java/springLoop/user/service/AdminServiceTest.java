package springLoop.user.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;
import springLoop.user.model.AdminEntity;

import java.sql.Timestamp;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ExtendWith(SpringExtension.class)
class AdminServiceTest {
    
    @Autowired
    private AdminService adminService;

    private AdminEntity adminEntity;

    @BeforeEach
    void initialize() {
        adminEntity = new AdminEntity();
        adminEntity.setPassword("default");
        adminEntity.setMail("default");
        adminEntity.setNom("default");
        adminEntity.setAdresse("default");
        adminEntity.setNbtotalsales(0);
    }

    @Test
    @Transactional("userTransactionManager")
    void findById() {
        //GIVEN
        initialize();
        //WHEN
        adminService.save(adminEntity);
        AdminEntity adminFound = adminService.findById(adminEntity.getId());
        //THEN
        assertNotNull(adminFound);
        assertEquals(adminEntity, adminFound);
        adminService.delete(adminFound);
    }

    @Test
    @Transactional("userTransactionManager")
    @DisplayName("should add")
    void save() {
        // GIVEN
        initialize();
        // WHEN
        adminService.save(adminEntity);
        // THEN
        AdminEntity savedEntity = adminService.findById(adminEntity.getId());
        assertNotNull(savedEntity);
        assertEquals(savedEntity, adminEntity);
        adminService.delete(adminEntity);
    }


    @Test
    @Transactional("userTransactionManager")
    @DisplayName("should delete")
    void delete() {
        // GIVEN
        initialize();
        adminService.save(adminEntity);
        // WHEN
        int entityId = adminEntity.getId();
        adminService.delete(adminEntity);
        // THEN
        assertNull(adminService.findById(entityId));
    }

    @Test
    @Transactional("userTransactionManager")
    @DisplayName("should findAll")
    void findAll() {
        //GIVEN
        //WHEN
        List<AdminEntity> result = (List<AdminEntity>) adminService.findAll();
        //THEN
        assertNotNull(result);
        assertFalse(result.isEmpty());
    }

}