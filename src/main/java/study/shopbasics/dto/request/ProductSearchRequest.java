package study.shopbasics.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ProductSearchRequest {

    @NotNull(message = "searchKeyword must not be null")
    private String searchKeyword;
}
