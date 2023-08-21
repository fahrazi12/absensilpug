package com.ivallavifahrazi.lpugabsensi.models;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Entity
@Table(name = "staff_jadwal")
public class StaffJadwal {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "staff_jadwal_id")
    private Long staffJadwalId;

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
    @JoinColumn(name = "staff_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Staff staff;

    public Long getStaffJadwalId() {
        return staffJadwalId;
    }

    public void setStaffJadwalId(Long staffJadwalId) {
        this.staffJadwalId = staffJadwalId;
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

    public Staff getStaff() {
        return staff;
    }

    public void setStaff(Staff staff) {
        this.staff = staff;
    }
}
