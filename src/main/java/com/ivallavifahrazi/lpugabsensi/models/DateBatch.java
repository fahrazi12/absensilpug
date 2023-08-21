package com.ivallavifahrazi.lpugabsensi.models;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;

@Entity
@Table(name = "date_batch")
public class DateBatch {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long dateBatchId;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate tanggalMulai;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate tanggalAkhir;

    @Column(name = "batch_ke")
    private int batchKe;

    @Column(name = "kursus", length = 10)
    private String kursus;

    public DateBatch() {
    }

    public long getDateBatchId() {
        return dateBatchId;
    }

    public DateBatch(long dateBatchId) {
        this.dateBatchId = dateBatchId;
    }

    public void setDateBatchId(long dateBatchId) {
        this.dateBatchId = dateBatchId;
    }

    public LocalDate getTanggalMulai() {
        return tanggalMulai;
    }

    public void setTanggalMulai(LocalDate tanggalMulai) {
        this.tanggalMulai = tanggalMulai;
    }

    public LocalDate getTanggalAkhir() {
        return tanggalAkhir;
    }

    public void setTanggalAkhir(LocalDate tanggalAkhir) {
        this.tanggalAkhir = tanggalAkhir;
    }

    public int getBatchKe() {
        return batchKe;
    }

    public void setBatchKe(int batchKe) {
        this.batchKe = batchKe;
    }

    public String getKursus() {
        return kursus;
    }

    public void setKursus(String kursus) {
        this.kursus = kursus;
    }
}
