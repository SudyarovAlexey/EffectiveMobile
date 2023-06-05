package net.EffectiveMobile.TestTask.repository;

import net.EffectiveMobile.TestTask.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Role findByRoleName(String name);

}
