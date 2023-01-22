package me.ablax.warehouse.models.req;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ForgotReq {
    @NotNull
    @Email
    private String email;

}
