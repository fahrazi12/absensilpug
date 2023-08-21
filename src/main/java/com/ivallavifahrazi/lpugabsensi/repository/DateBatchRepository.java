package com.ivallavifahrazi.lpugabsensi.repository;

import com.ivallavifahrazi.lpugabsensi.models.DateBatch;
import com.ivallavifahrazi.lpugabsensi.models.Jadwal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DateBatchRepository extends JpaRepository<DateBatch, Long> {

    Optional<List<DateBatch>> findByBatchKe(Integer batchKe);
}
