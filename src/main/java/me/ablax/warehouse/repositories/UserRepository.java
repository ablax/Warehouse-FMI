package me.ablax.warehouse.repositories;

import me.ablax.warehouse.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findByEmailOrUsername(final String email, final String username);
}
