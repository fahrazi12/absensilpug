package com.ivallavifahrazi.lpugabsensi.models;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Entity
@Table(name = "peserta")
public class Peserta {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "peserta_id")
    private Long pesertaId;

    @Column(name = "nama", length = 50)
    private String nama;

    @Column(name = "npm", length = 10)
    private String npm;

    @Column(name = "kursus", length = 10)
    private String kursus;

    @Column(name = "phoneNum", length = 20)
    private String phoneNum;

    @Column(name = "email", length = 50)
    private String email;

    @Column(name = "batch")
    private int batch;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinColumn(name = "date_batch_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private DateBatch dateBatch;
    public Long getPesertaId() {
        return pesertaId;
    }

    public void setPesertaId(Long pesertaId) {
        this.pesertaId = pesertaId;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getNpm() {
        return npm;
    }

    public void setNpm(String npm) {
        this.npm = npm;
    }


    public String getKursus() {
        return kursus;
    }

    public void setKursus(String kursus) {
        this.kursus = kursus;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getBatch() {
        return batch;
    }

    public void setBatch(int batch) {
        this.batch = batch;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public DateBatch getDateBatch() {
        return dateBatch;
    }

    public void setDateBatch(DateBatch dateBatch) {
        this.dateBatch = dateBatch;
    }
}
