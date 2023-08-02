package study.shopbasics.service;

import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import study.shopbasics.dto.request.UserSaveRequest;
import study.shopbasics.dto.response.UserSaveResponse;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Transactional
class UserServiceTest {

    @Autowired
    private UserService userService;

    @Test
    @DisplayName("user save test success")
    public void testSaveUserSuccess() {
        // Given
        String name = "test";
        String password = "test";
        String email = "test@email.com";
        UserSaveRequest userSaveRequest = new UserSaveRequest(name, password, email);

        // When
        UserSaveResponse userSaveResponse = userService.saveUser(userSaveRequest);

        // Then
        assertEquals(userSaveResponse.getUsername(), name);
        assertEquals(userSaveResponse.getEmail(), email);
    }

    @Test
    @DisplayName("user save test invalid email")
    public void testSaveUserFail() {
        // Given
        String name = "test";
        String password = "test";
        String email = "test";
        UserSaveRequest userSaveRequest = new UserSaveRequest(name, password, email);

        // When, Then
        Assertions.assertThrows(ConstraintViolationException.class, () -> userService.saveUser(userSaveRequest));
    }
}