package study.shopbasics.service;

import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import study.shopbasics.dto.request.UserSaveRequest;
import study.shopbasics.dto.request.UserSignInRequest;
import study.shopbasics.dto.response.UserSaveResponse;
import study.shopbasics.dto.response.UserSignInResponse;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Transactional
class UserServiceTest {

    @Autowired
    private UserService userService;

    @Test
    @DisplayName("user save test success")
    void testSaveUserSuccess() {
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
    void testSaveUserFail() {
        // Given
        String name = "test";
        String password = "test";
        String email = "test";
        UserSaveRequest userSaveRequest = new UserSaveRequest(name, password, email);

        // When, Then
        Assertions.assertThrows(ConstraintViolationException.class, () -> userService.saveUser(userSaveRequest));
    }

    @Test
    @DisplayName("user save test duplicate email")
    void testSaveUserFailDuplicateEmail() {
        // Given
        userService.saveUser(new UserSaveRequest("test", "test", "test@email.com"));

        // When, Then
        Assertions.assertThrows(IllegalArgumentException.class, () ->
                userService.saveUser(new UserSaveRequest("test", "test", "test@email.com")));
    }

    @Test
    @DisplayName("user signin test success")
    void testSigninSuccess() {
        // Given
        String email = "test@email.com";
        String password = "test";
        userService.saveUser(new UserSaveRequest("test", password, email));

        // When
        UserSignInResponse userSaveResponse = userService.signin(new UserSignInRequest(email, password));

        // Then
        assertEquals(userSaveResponse.getEmail(), email);
    }

    @Test
    @DisplayName("user signin test not exist email")
    void testSigninFailNotExistEmail() {
        // Given
        String email = "test@email.com";
        String password = "test";
        userService.saveUser(new UserSaveRequest("test", password, email));

        // When, Then
        Assertions.assertThrows(IllegalArgumentException.class, () ->
                userService.signin(new UserSignInRequest("notExistEmail", password)));
    }

    @Test
    @DisplayName("user signin test invalid password")
    void testSigninFailInvalidPassword() {
        // Given
        String email = "test@email.com";
        String password = "test";
        userService.saveUser(new UserSaveRequest("test", password, email));

        // When, Then
        Assertions.assertThrows(IllegalArgumentException.class, () ->
                userService.signin(new UserSignInRequest(email, "invalidPassword")));
    }
}