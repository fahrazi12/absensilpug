package com.ivallavifahrazi.lpugabsensi.repository;

import com.ivallavifahrazi.lpugabsensi.models.Jadwal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface JadwalRepository extends JpaRepository<Jadwal, Long>  {
    @Query("SELECT j FROM Jadwal j WHERE dateBatch.id =?1")
    List<Jadwal> findByDateBatchId(Long batchId);

    Optional<List<Jadwal>> findByDateBatch_DateBatchId(Long dateBatchId);
    @Query("SELECT j FROM Jadwal j WHERE j.tanggal =?1")
    Jadwal findByTanggal(LocalDate tanggal);
}
