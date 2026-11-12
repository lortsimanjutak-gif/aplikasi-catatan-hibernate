package com.nasipecel.aplikasicatatan.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "users")
public class UserAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "nama", nullable = false, length = 150)
    private String nama;

    @Column(name = "email", nullable = false, unique = true, length = 190)
    private String email;

    @Column(name = "password", nullable = false, length = 255)
    private String password;

    public UserAccount() {}

    public UserAccount(String nama, String email, String password) {
        this.nama = nama;
        this.email = email;
        this.password = password;
    }

    public Integer getId() { return id; }
    public String getNama() { return nama; }
    public String getEmail() { return email; }
    public String getPassword() { return password; }
    public void setNama(String nama) { this.nama = nama; }
    public void setEmail(String email) { this.email = email; }
    public void setPassword(String password) { this.password = password; }
}
