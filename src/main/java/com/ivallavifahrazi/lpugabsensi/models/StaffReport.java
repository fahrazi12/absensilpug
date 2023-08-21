package com.ivallavifahrazi.lpugabsensi.models;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "staff_report")
public class StaffReport {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public long staffReportId;

    private Integer jumlahJaga;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinColumn(name = "staff_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Staff staff;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinColumn(name = "date_batch_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private DateBatch dateBatch;

    private Integer batchKe;

    private LocalDateTime createdAt;

    public long getStaffReportId() {
        return staffReportId;
    }

    public void setStaffReportId(long staffReportId) {
        this.staffReportId = staffReportId;
    }

    public Integer getJumlahJaga() {
        return jumlahJaga;
    }

    public void setJumlahJaga(Integer jumlahJaga) {
        this.jumlahJaga = jumlahJaga;
    }

    public Staff getStaff() {
        return staff;
    }

    public void setStaff(Staff staff) {
        this.staff = staff;
    }

    public DateBatch getDateBatch() {
        return dateBatch;
    }

    public void setDateBatch(DateBatch dateBatch) {
        this.dateBatch = dateBatch;
    }

    public Integer getBatchKe() {
        return batchKe;
    }

    public void setBatchKe(Integer batchKe) {
        this.batchKe = batchKe;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
