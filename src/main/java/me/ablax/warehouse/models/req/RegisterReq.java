package me.ablax.warehouse.models.req;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;


@Data
public class RegisterReq {

    /*   ▪ потребителско име - малки и големи латински букви и "_". Дължина между 5
и 15 символа. Задължително поле
        ▪ парола - задължително поле. Дължина между 6 и 20 символа. Трябва да
съдържа:
            - поне 1 малка латинска буква
            - поне 1 голяма латинска буква
            - поне един специален символ[@, -, _, ~, |]
        ▪ E-mail адрес: валиден e-mail адрес. Задължително поле
        ▪ Телефон: Полето не е задължително. Може да съдържа само цифри, интервал
и тире('-')*/
    @Size(min = 5, max = 10)
    @NotNull
    @Pattern(regexp = "[A-Za-z_]+", message = "can contain only lower case letter, capital case letter and _")
    private String username;
    @NotNull
    @Size(min = 6, max = 20)
    @Pattern(regexp = "(?=.*[a-z])(?=.*[A-Z])(?=.*[!@_~|\\-]).+", message = "should contain at least one lowercase letter, one uppercase letter, and one symbol")
    private String password;
    private String confirmPassword;
    @Email
    @NotNull
    private String email;
    @Nullable
    @Pattern(regexp = "^[0-9\\- ]+$", message = "can contain only numbers, whitespace and -")
    private String phoneNumber;

}
