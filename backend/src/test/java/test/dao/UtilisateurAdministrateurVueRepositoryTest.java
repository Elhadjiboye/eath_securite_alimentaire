package test.dao;

import com.eath.entite.UtilisateurAdministrateurVue;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import com.eath.dao.UtilisateurAdministrateurVueRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class UtilisateurAdministrateurVueRepositoryTest {

    @Autowired
    private UtilisateurAdministrateurVueRepository repository;

    @Test
    public void testFindByEmail() {
        String email = "john.doe@example.com"; // Assurez-vous que cet email existe dans votre vue
        Optional<UtilisateurAdministrateurVue> user = repository.findByEmail(email);
        assertTrue(user.isPresent(), "L'utilisateur devrait être trouvé");
        assertEquals(email, user.get().getEmail(), "Les emails devraient correspondre");
    }
}
