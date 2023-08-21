package com.ivallavifahrazi.lpugabsensi.repository;

import com.ivallavifahrazi.lpugabsensi.models.PesertaJadwal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PesertaJadwalRepository extends JpaRepository<PesertaJadwal, Long> {
   @Query("SELECT pj FROM PesertaJadwal pj where pj.dateBatch.dateBatchId=?1")
   Optional<List<PesertaJadwal>> findByDateBatchId(Long dateBatchId);
   @Query("SELECT pj from PesertaJadwal pj where pj.jadwal.jadwalId=?1")
   Optional<List<PesertaJadwal>> findByJadwalId(Long jadwalId);

   Optional<List<PesertaJadwal>> findByPeserta_Npm(String npm);

   Optional<List<PesertaJadwal>> findByBatchAndPeserta_Npm(Integer batch, String NPM);

   Optional<List<PesertaJadwal>> findByJadwal_JadwalIdAndKursus(Long jadwalId,
                                                                String kursus);
}
