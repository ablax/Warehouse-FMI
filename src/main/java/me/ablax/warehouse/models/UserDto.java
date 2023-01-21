package me.ablax.warehouse.models;

public class UserDto {

    private final Long userId;
    private final String username;

    public UserDto(final Long userId, final String username) {
        this.userId = userId;
        this.username = username;
    }

    public Long getUserId() {
        return userId;
    }

    public String getUsername() {
        return username;
    }
}
