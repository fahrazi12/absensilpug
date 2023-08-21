package com.ivallavifahrazi.lpugabsensi.models;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Entity
@Table(name = "rekapan_jadwal")
public class RekapanJadwal {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long idRekapanJadwal;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinColumn(name = "jadwal_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Jadwal jadwal;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinColumn(name = "date_batch_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private DateBatch dateBatch;


    private String buktiAbsenSatu;
    private String buktiAbsenDua;

    private String linkGoogleDrive;

    @Column(length = 10)
    private String kursus;

    public Long getIdRekapanJadwal() {
        return idRekapanJadwal;
    }

    public void setIdRekapanJadwal(Long idRekapanJadwal) {
        this.idRekapanJadwal = idRekapanJadwal;
    }

    public Jadwal getJadwal() {
        return jadwal;
    }

    public void setJadwal(Jadwal jadwal) {
        this.jadwal = jadwal;
    }

    public DateBatch getDateBatch() {
        return dateBatch;
    }

    public void setDateBatch(DateBatch dateBatch) {
        this.dateBatch = dateBatch;
    }

    public String getBuktiAbsenSatu() {
        return buktiAbsenSatu;
    }

    public void setBuktiAbsenSatu(String buktiAbsenSatu) {
        this.buktiAbsenSatu = buktiAbsenSatu;
    }

    public String getBuktiAbsenDua() {
        return buktiAbsenDua;
    }

    public void setBuktiAbsenDua(String buktiAbsenDua) {
        this.buktiAbsenDua = buktiAbsenDua;
    }

    public String getLinkGoogleDrive() {
        return linkGoogleDrive;
    }

    public void setLinkGoogleDrive(String linkGoogleDrive) {
        this.linkGoogleDrive = linkGoogleDrive;
    }

    public String getKursus() {
        return kursus;
    }

    public void setKursus(String kursus) {
        this.kursus = kursus;
    }
}
