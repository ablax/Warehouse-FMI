package me.ablax.warehouse.models.req;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

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
    @Pattern(regexp = "[A-Za-z_]+")
    private String username;
    @NotNull
    @Size(min = 6, max = 20)
    @Pattern(regexp = "(?=.*[a-z])(?=.*[A-Z])(?=.*[@_~|\\-]).+")
    private String password;
    private String confirmPassword;
    @Email
    @NotNull
    private String email;
    @Nullable
    @Pattern(regexp = "^[0-9\\- ]+$")
    private String phoneNumber;

    public RegisterReq() {
    }

    public RegisterReq(final String username, final String password, final String confirmPassword, final String email, final String phoneNumber) {
        this.username = username;
        this.password = password;
        this.confirmPassword = confirmPassword;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

    @Override
    public String toString() {
        return "RegisterReq{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", confirmPassword='" + confirmPassword + '\'' +
                ", email='" + email + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                '}';
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(final String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(final String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(final String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(final String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(final String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
