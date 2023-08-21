package com.ivallavifahrazi.lpugabsensi.models;

import java.util.List;

public class ListAbsensiJadwal {
    private List<AbsensiJadwal> absensiJadwalList;

    public ListAbsensiJadwal(List<AbsensiJadwal> absensiJadwalList) {
        this.absensiJadwalList = absensiJadwalList;
    }

    public List<AbsensiJadwal> getAbsensiJadwalList() {
        return absensiJadwalList;
    }

    public void setAbsensiJadwalList(List<AbsensiJadwal> absensiJadwalList) {
        this.absensiJadwalList = absensiJadwalList;
    }
}
