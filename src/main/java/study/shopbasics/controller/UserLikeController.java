package study.shopbasics.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import study.shopbasics.dto.request.UserLikeRequest;
import study.shopbasics.service.UserLikeService;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/like")
public class UserLikeController {

    private final UserLikeService userLikeService;

    @PostMapping("/add")
    public void like(@Valid @RequestBody UserLikeRequest userLikeRequest) {
        this.userLikeService.saveUserLike(userLikeRequest);
    }

    @PostMapping("/remove")
    public void unlike(@Valid @RequestBody UserLikeRequest userLikeRequest) {
        this.userLikeService.deleteUserLike(userLikeRequest);
    }
}
