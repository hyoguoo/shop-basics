package study.shopbasics;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

@SpringBootTest
class ShopBasicsApplicationTests {

    @Autowired
    private JdbcTemplate jdbcTemplate;

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
}
