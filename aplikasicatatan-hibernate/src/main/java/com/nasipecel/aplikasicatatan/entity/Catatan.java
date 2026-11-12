package com.nasipecel.aplikasicatatan.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "catatan")
public class Catatan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private UserAccount user;

    @Column(name = "judul", nullable = false, length = 255)
    private String judul;

    @Lob
    @Column(name = "isi")
    private String isi;

    @Column(name = "favorit", nullable = false)
    private boolean favorit;

    public Catatan() {}

    public Catatan(UserAccount user, String judul, String isi, boolean favorit) {
        this.user = user;
        this.judul = judul;
        this.isi = isi;
        this.favorit = favorit;
    }

    public Integer getId() { return id; }
    public UserAccount getUser() { return user; }
    public String getJudul() { return judul; }
    public String getIsi() { return isi; }
    public boolean isFavorit() { return favorit; }
    public void setUser(UserAccount user) { this.user = user; }
    public void setJudul(String judul) { this.judul = judul; }
    public void setIsi(String isi) { this.isi = isi; }
    public void setFavorit(boolean favorit) { this.favorit = favorit; }
}
