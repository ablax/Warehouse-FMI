package me.ablax.warehouse.repositories;

import me.ablax.warehouse.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    UserEntity findByEmailOrUsername(final String email, final String username);
}
