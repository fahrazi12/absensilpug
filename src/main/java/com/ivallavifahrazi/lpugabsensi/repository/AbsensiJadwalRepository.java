package com.ivallavifahrazi.lpugabsensi.repository;

import com.ivallavifahrazi.lpugabsensi.models.AbsensiJadwal;
import com.ivallavifahrazi.lpugabsensi.models.PesertaJadwal;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AbsensiJadwalRepository extends JpaRepository<AbsensiJadwal, Long> {
    Optional<List<AbsensiJadwal>> findByJadwal_JadwalId(Long jadwalId);

    Optional<List<AbsensiJadwal>> findByJadwal_JadwalIdAndKursus(Long jadwalId,
                                                                String kursus);

    Optional<AbsensiJadwal> findByPeserta_Peserta_Npm(String npm);

    Optional<AbsensiJadwal> findByPeserta_Peserta_PesertaIdAndJadwal_JadwalId(Long id, Long jadwalId);
    Optional<List<AbsensiJadwal>> findByBatchKeAndNpm(Integer batch, String NPM);
}
