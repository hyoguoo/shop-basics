package study.shopbasics.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import study.shopbasics.dto.request.UserSaveRequest;
import study.shopbasics.dto.request.UserSignInRequest;
import study.shopbasics.dto.response.UserSaveResponse;
import study.shopbasics.dto.response.UserSignInResponse;
import study.shopbasics.service.UserService;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;

    @PostMapping("/sign-up")
    public UserSaveResponse signup(@Valid @RequestBody UserSaveRequest userSaveRequest) {
        return this.userService.saveUser(userSaveRequest);
    }

    @PostMapping("/sign-in")
    public UserSignInResponse signin(@Valid @RequestBody UserSignInRequest userSignInRequest) {
        return this.userService.signin(userSignInRequest);
    }
}
