package study.shopbasics.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserSignInRequest {

    @NotNull(message = "email must not be null")
    @Email
    private String email;

    @NotNull(message = "password must not be null")
    private String password;

    public UserSignInRequest(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
