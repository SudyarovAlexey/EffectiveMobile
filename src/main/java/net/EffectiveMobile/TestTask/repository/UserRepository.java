package net.EffectiveMobile.TestTask.repository;

import net.EffectiveMobile.TestTask.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    User getUserByUsername(String name);

    User getUserById(Long userId);

}
