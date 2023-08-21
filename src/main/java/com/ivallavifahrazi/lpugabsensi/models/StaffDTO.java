package com.ivallavifahrazi.lpugabsensi.models;

import javax.persistence.Column;

public class StaffDTO {

    private String nama;

    private String email;

    private String username;
    private String password;

    @Column(length = 10)
    private String role;
    public StaffDTO() {
    }

    public StaffDTO(String nama, String email, String username, String password, String role) {
        this.nama = nama;
        this.email = email;
        this.username = username;
        this.password = password;
        this.role = role;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
