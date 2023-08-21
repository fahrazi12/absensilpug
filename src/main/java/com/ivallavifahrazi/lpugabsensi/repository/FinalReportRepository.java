package com.ivallavifahrazi.lpugabsensi.repository;

import com.ivallavifahrazi.lpugabsensi.models.FinalReport;
import com.ivallavifahrazi.lpugabsensi.models.StaffReport;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FinalReportRepository extends JpaRepository<FinalReport, Long> {
    Optional<List<FinalReport>> findByBatchKe(Integer batchKe);

    Optional<List<FinalReport>> findByDateBatch_DateBatchId(Long batchId);
}
