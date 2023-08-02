package study.shopbasics.service;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import study.shopbasics.dto.request.UserSaveRequest;
import study.shopbasics.dto.response.UserSaveResponse;
import study.shopbasics.repository.UserRepository;

@Service
@Validated
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public UserSaveResponse saveUser(@Valid UserSaveRequest userSaveRequest) {
        if (isDuplicateEmail(userSaveRequest.getEmail())) {
            throw new IllegalArgumentException("email is already exist.");
        }
        return UserSaveResponse.of(userRepository.save(userSaveRequest.toEntity()));
    }

    private boolean isDuplicateEmail(String email) {
        return userRepository.findByEmail(email).isPresent();
    }
}
