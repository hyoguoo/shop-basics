package study.shopbasics;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Transactional;
import study.shopbasics.entity.User;
import study.shopbasics.repository.UserRepository;

import java.time.LocalDateTime;

@SpringBootTest
@Transactional
class ShopBasicsApplicationTests {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private UserRepository userRepository;

    @Test
    @DisplayName("MySQL Connection Test")
    void testMySQLConnection() {
        try {
            Integer test = jdbcTemplate.queryForObject("SELECT 1", Integer.class);
            Assertions.assertEquals(test, 1);
        } catch (Exception e) {
            Assertions.fail(e.getMessage());
        }
    }

    @Test
    @DisplayName("Spring Data JPA Test")
    public void testSaveUser() {
        // Given
        User user = new User("test", "test", "test@test.com", LocalDateTime.now(), LocalDateTime.now());

        // When
        userRepository.save(user);
        User findUser = userRepository.findById(user.getId()).orElse(null);

        // Then
        Assertions.assertEquals(user, findUser);
        userRepository.delete(user);
        Assertions.assertEquals(userRepository.findAll().size(), 0);
    }
}
