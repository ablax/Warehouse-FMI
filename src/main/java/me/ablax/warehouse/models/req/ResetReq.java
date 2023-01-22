package me.ablax.warehouse.models.req;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ResetReq {
    private Long id;
    @NotNull
    @Size(min = 6, max = 20)
    @Pattern(regexp = "(?=.*[a-z])(?=.*[A-Z])(?=.*[!@_~|\\-]).+", message = "should contain at least one lowercase letter, one uppercase letter, and one symbol")
    private String password;
    private String confirmPassword;

}
