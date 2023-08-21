package com.ivallavifahrazi.lpugabsensi.repository;


import com.ivallavifahrazi.lpugabsensi.models.FinalReport;
import com.ivallavifahrazi.lpugabsensi.models.StaffReport;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface StaffReportRepository extends JpaRepository<StaffReport, Long> {
    Optional<List<StaffReport>> findByBatchKe(Integer batchKe);

    Optional<List<StaffReport>> findByDateBatch_DateBatchId(Long batchId);
}
