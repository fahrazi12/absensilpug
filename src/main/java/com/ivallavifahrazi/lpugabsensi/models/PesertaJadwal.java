package com.ivallavifahrazi.lpugabsensi.models;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "peserta_jadwal")
public class PesertaJadwal {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "peserta_jadwal_id")
    private Long pesertaId;

    @Column(name = "kursus", length = 10)
    private String kursus;


    @Column(name = "batch")
    private int batch;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinColumn(name = "jadwal_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Jadwal jadwal;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinColumn(name = "date_batch_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private DateBatch dateBatch;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinColumn(name = "peserta_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Peserta peserta;



    //AUDITING



    //GETTER SETTER jadwal

    public Jadwal getJadwal(){return  jadwal;}
    public void setJadwal(Jadwal jadwal){this.jadwal = jadwal;}
    public long getJadwalId(){return jadwal.getJadwalId();}
    public void setJadwalId(long jadwalId){this.jadwal = new Jadwal(jadwalId);}

    //GETTER SETTER dateBatch

    public DateBatch getDateBatch(){
        return dateBatch;
    }
    public void setDateBatch(DateBatch dateBatch) {
        this.dateBatch = dateBatch;
    }
    public long getDateBatchId(){
        return dateBatch.getDateBatchId();
    }
    public void setBatchId(long dateBatchId){
        this.dateBatch = new DateBatch(dateBatchId);
    }

    //GETTER SETTER PESERTA


    public Peserta getPeserta() {
        return peserta;
    }

    public void setPeserta(Peserta peserta) {
        this.peserta = peserta;
    }

    public Long getPesertaId() {
        return pesertaId;
    }

    public void setPesertaId(Long pesertaId) {
        this.pesertaId = pesertaId;
    }



    public String getKursus() {
        return kursus;
    }

    public void setKursus(String kursus) {
        this.kursus = kursus;
    }

    public int getBatch() {
        return batch;
    }

    public void setBatch(int batch) {
        this.batch = batch;
    }
}
