package com.ivallavifahrazi.lpugabsensi.models;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "staff")
public class Staff {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long idStaff;

    @Column(length = 40)
    private String nama;

    @Column(length = 40)
    private String email;

    @Column(length = 40)
    private String username;
    private String password;

    @Column(length = 20)
    private String role;

    @ManyToMany(fetch = FetchType.EAGER, cascade=CascadeType.ALL)
    @JoinTable(
            name="staff_roles",
            joinColumns={@JoinColumn(name="staff_id", referencedColumnName="idStaff")},
            inverseJoinColumns={@JoinColumn(name="role_id", referencedColumnName="id")}
            )
    private List<Role> roles = new ArrayList<>();
    public Staff() {
    }

    public Staff(String nama, String email, String username, String password, String role, List<Role> roles) {
        this.nama = nama;
        this.email = email;
        this.username = username;
        this.password = password;
        this.role = role;
        this.roles = roles;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public Long getIdStaff() {
        return idStaff;
    }

    public void setIdStaff(Long idStaff) {
        this.idStaff = idStaff;
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
