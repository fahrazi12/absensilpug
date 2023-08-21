package com.ivallavifahrazi.lpugabsensi.repository;

import com.ivallavifahrazi.lpugabsensi.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RolesRepository extends JpaRepository<Role, Long> {
    Role findByName(String name);

}
