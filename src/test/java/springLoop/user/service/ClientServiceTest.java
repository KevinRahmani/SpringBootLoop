package springLoop.user.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;
import springLoop.user.model.ClientEntity;
import springLoop.user.service.ClientService;


import java.sql.Timestamp;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ExtendWith(SpringExtension.class)
class ClientServiceTest {

    @Autowired
    private ClientService clientService;

    private ClientEntity clientEntity;

    @BeforeEach
    void initialize() {
        clientEntity = new ClientEntity();
        clientEntity.setPassword("default");
        clientEntity.setMail("default");
        clientEntity.setNom("default");
        clientEntity.setDatesignup(new Timestamp(System.currentTimeMillis()));
        clientEntity.setAdresse("default");
        clientEntity.setNbproduct(0);
        clientEntity.setHistocommand("default");
    }

    @Test
    @Transactional("userTransactionManager")
    void findById() {
        //GIVEN
        initialize();
        //WHEN
        clientService.save(clientEntity);
        ClientEntity clientFound = clientService.findById(clientEntity.getId());
        //THEN
        assertNotNull(clientFound);
        assertEquals(clientEntity, clientFound);
        clientService.delete(clientFound);
    }

    @Test
    @Transactional("userTransactionManager")
    @DisplayName("should add")
    void save() {
        // GIVEN
        initialize();
        // WHEN
        clientService.save(clientEntity);
        // THEN
        ClientEntity savedEntity = clientService.findById(clientEntity.getId());
        assertNotNull(savedEntity);
        assertEquals(savedEntity, clientEntity);
        clientService.delete(clientEntity);
    }


    @Test
    @Transactional("userTransactionManager")
    @DisplayName("should delete")
    void delete() {
        // GIVEN
        initialize();
        clientService.save(clientEntity);
        // WHEN
        int entityId = clientEntity.getId();
        clientService.delete(clientEntity);
        // THEN
        assertNull(clientService.findById(entityId));
    }

    @Test
    @Transactional("userTransactionManager")
    @DisplayName("should findAll")
    void findAll() {
        //GIVEN
        //WHEN
        List<ClientEntity> result = (List<ClientEntity>) clientService.findAll();
        //THEN
        assertNotNull(result);
        assertFalse(result.isEmpty());
    }
}