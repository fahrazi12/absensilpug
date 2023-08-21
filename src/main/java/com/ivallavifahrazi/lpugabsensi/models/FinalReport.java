package com.ivallavifahrazi.lpugabsensi.models;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "final_report")
public class FinalReport {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long reportId;

    private Integer jumlahHadir;
    private Integer jumlahTidakHadir;

    private Boolean statusKehadiran;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinColumn(name = "peserta_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Peserta Peserta;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinColumn(name = "date_batch_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private DateBatch dateBatch;

    private Integer batchKe;

    private LocalDateTime createdAt;

    public Long getReportId() {
        return reportId;
    }

    public void setReportId(Long reportId) {
        this.reportId = reportId;
    }

    public Integer getJumlahHadir() {
        return jumlahHadir;
    }

    public void setJumlahHadir(Integer jumlahHadir) {
        this.jumlahHadir = jumlahHadir;
    }

    public Integer getJumlahTidakHadir() {
        return jumlahTidakHadir;
    }

    public void setJumlahTidakHadir(Integer jumlahTidakHadir) {
        this.jumlahTidakHadir = jumlahTidakHadir;
    }

    public Boolean getStatusKehadiran() {
        return statusKehadiran;
    }

    public void setStatusKehadiran(Boolean statusKehadiran) {
        this.statusKehadiran = statusKehadiran;
    }

    public com.ivallavifahrazi.lpugabsensi.models.Peserta getPeserta() {
        return Peserta;
    }

    public void setPeserta(com.ivallavifahrazi.lpugabsensi.models.Peserta peserta) {
        Peserta = peserta;
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
