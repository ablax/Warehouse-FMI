package me.ablax.warehouse.models;

import java.io.Serializable;

public record UserDto(Long userId, String username) implements Serializable {

    private static final long serialVersionUID = 382941L;

    @Override
    public String toString() {
        return "UserDto{" +
                "userId=" + userId +
                ", username='" + username + '\'' +
                '}';
    }
}
