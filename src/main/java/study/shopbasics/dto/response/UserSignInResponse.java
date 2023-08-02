package study.shopbasics.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import study.shopbasics.entity.User;

@Getter
@NoArgsConstructor
public class UserSignInResponse {

    private Long id;
    private String username;
    private String email;

    public UserSignInResponse(Long id, String username, String email) {
        this.id = id;
        this.username = username;
        this.email = email;
    }

    public static UserSignInResponse of(final User user) {
        return new UserSignInResponse(user.getId(), user.getUsername(), user.getEmail());
    }
}
