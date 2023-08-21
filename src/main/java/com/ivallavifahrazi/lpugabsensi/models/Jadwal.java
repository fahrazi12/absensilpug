package com.ivallavifahrazi.lpugabsensi.models;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;

@Entity
@Table(name = "jadwal")
public class Jadwal {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long jadwalId;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate tanggal;

    @Lob
    @Column(name = "message", length = 100)
    private String message;

    @Column(name = "kursus", length = 10)
    private String kursus;

    @Column(name = "batch")
    private long batch;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinColumn(name = "date_batch_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private DateBatch dateBatch;

    public Jadwal(long jadwalId) {
        this.jadwalId = jadwalId;
    }

    public long getJadwalId() {
        return jadwalId;
    }

    public DateBatch getDateBatch(){
        return dateBatch;
    }
    public long getDateBatchId(){
        return dateBatch.getDateBatchId();
    }

    public void setBatchId(long dateBatchId){
        this.dateBatch = new DateBatch(dateBatchId);
    }

    public void setJadwalId(long jadwalId) {
        this.jadwalId = jadwalId;
    }

    public LocalDate getTanggal() {
        return tanggal;
    }

    public void setTanggal(LocalDate tanggal) {
        this.tanggal = tanggal;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getKursus() {
        return kursus;
    }

    public void setKursus(String kursus) {
        this.kursus = kursus;
    }

    public Long getBatch() {
        return batch;
    }

    public void setBatch(Long batch) {
        this.batch = batch;
    }

    public Jadwal() {
    }

    public Jadwal(long jadwalId, LocalDate tanggal, String message, String kursus, int batch) {
        this.jadwalId = jadwalId;
        this.tanggal = tanggal;
        this.message = message;
        this.kursus = kursus;
        this.batch = batch;
    }
}
