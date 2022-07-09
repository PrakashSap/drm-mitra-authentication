package com.drm.mitra.authentication.repository;

import com.drm.mitra.authentication.entity.Roles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.UUID;

public interface RolesRepository extends JpaRepository<Roles, UUID> {
    @Query(value = "SELECT * FROM roles a WHERE a.role_name=?1",nativeQuery = true)
    Roles findByRoleName(String roleName);
}
