package com.ivallavifahrazi.lpugabsensi.repository;

import com.ivallavifahrazi.lpugabsensi.models.StaffJadwal;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface StaffJadwalRepository extends JpaRepository<StaffJadwal, Long> {
    Optional<List<StaffJadwal>> findByJadwal_JadwalIdAndKursus(Long jadwalId, String kursus);
    Optional<List<StaffJadwal>> findByStaff_IdStaff(Long idStaff);
}
