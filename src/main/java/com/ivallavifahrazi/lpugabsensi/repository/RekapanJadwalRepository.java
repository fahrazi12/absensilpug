package com.ivallavifahrazi.lpugabsensi.repository;

import com.ivallavifahrazi.lpugabsensi.models.RekapanJadwal;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RekapanJadwalRepository extends JpaRepository<RekapanJadwal, Long> {
    Optional<List<RekapanJadwal>> findByJadwal_JadwalId(Long jadwalId);
    Optional<List<RekapanJadwal>> findByJadwal_JadwalIdAndKursus(Long jadwalId,
                                                                 String kursus);
}
