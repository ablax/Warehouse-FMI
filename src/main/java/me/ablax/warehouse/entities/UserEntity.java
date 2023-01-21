package me.ablax.warehouse.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import me.ablax.warehouse.models.UserDto;

@Entity
@Table
public class UserEntity extends BaseEntity {

    @Column(length = 15, nullable = false)
    private String username;

    @Column(nullable = false, length = 50)
    private String email;

    @Column
    private String phoneNumber;

    @Column
    private String password;


    public UserEntity() {
    }

    public UserEntity(final Long id, final String username, final String email, final String phoneNumber, final String password) {
        super(id);
        this.username = username;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(final String username) {
        this.username = username;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(final String password) {
        this.password = password;
    }

    public UserDto toDto() {
        return new UserDto(getId(), username);
    }
}
