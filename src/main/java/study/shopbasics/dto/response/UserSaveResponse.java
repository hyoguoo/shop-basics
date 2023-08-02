package study.shopbasics.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import study.shopbasics.entity.User;

@Getter
@NoArgsConstructor
public class UserSaveResponse {

    private Long id;
    private String username;
    private String email;

    public UserSaveResponse(Long id, String username, String email) {
        this.id = id;
        this.username = username;
        this.email = email;
    }

    public static UserSaveResponse of(final User user) {
        return new UserSaveResponse(user.getId(), user.getUsername(), user.getEmail());
    }
}
