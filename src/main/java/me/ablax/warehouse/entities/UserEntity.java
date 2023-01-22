package me.ablax.warehouse.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import me.ablax.warehouse.models.UserDto;

@Entity
@Table
@Data
public class UserEntity extends BaseEntity {

    @Column(length = 15, nullable = false)
    private String username;

    @Column(nullable = false, length = 50)
    private String email;

    @Column
    private String phoneNumber;

    @Column
    private String password;

    public UserDto toDto(){
        return new UserDto(getId(), username);
    }

}
