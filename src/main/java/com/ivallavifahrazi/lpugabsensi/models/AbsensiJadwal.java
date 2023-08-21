package com.ivallavifahrazi.lpugabsensi.models;


import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Entity
@Table(name = "absensi_jadwal")
public class AbsensiJadwal {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long idAbsenJadwal;

    @Column(length = 10)
    private String npm;
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinColumn(name = "jadwal_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Jadwal jadwal;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinColumn(name = "peserta_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private PesertaJadwal peserta;

    private Integer batchKe;
    private Boolean statusHadir;

    @Column(length = 10)
    private String kursus;

    public Long getIdAbsenJadwal() {
        return idAbsenJadwal;
    }

    public void setIdAbsenJadwal(Long idAbsenJadwal) {
        this.idAbsenJadwal = idAbsenJadwal;
    }

    public Jadwal getJadwal() {
        return jadwal;
    }

    public void setJadwal(Jadwal jadwal) {
        this.jadwal = jadwal;
    }

    public PesertaJadwal getPeserta() {
        return peserta;
    }

    public void setPeserta(PesertaJadwal peserta) {
        this.peserta = peserta;
    }

    public Boolean getStatusHadir() {
        return statusHadir;
    }

    public void setStatusHadir(Boolean statusHadir) {
        this.statusHadir = statusHadir;
    }

    public String getKursus() {
        return kursus;
    }

    public void setKursus(String kursus) {
        this.kursus = kursus;
    }

    public String getNpm() {
        return npm;
    }

    public void setNpm(String npm) {
        this.npm = npm;
    }

    public Integer getBatchKe() {
        return batchKe;
    }

    public void setBatchKe(Integer batchKe) {
        this.batchKe = batchKe;
    }
}
