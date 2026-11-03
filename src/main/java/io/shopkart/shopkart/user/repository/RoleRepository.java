package io.shopkart.shopkart.user.repository;

import io.shopkart.shopkart.user.Entity.Role;
import io.shopkart.shopkart.user.enums.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Optional<Role> findByName(RoleName roleName);

}
