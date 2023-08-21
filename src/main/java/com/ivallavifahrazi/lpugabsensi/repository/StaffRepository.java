package com.ivallavifahrazi.lpugabsensi.repository;

import com.ivallavifahrazi.lpugabsensi.models.Staff;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface StaffRepository extends JpaRepository<Staff, Long> {
    Optional<List<Staff>> findByRole(String role);
    Staff findByEmail(String email);
}
