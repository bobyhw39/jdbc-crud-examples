package com.toko.datatoko.models;

public class Pegawai {
    private int idPegawai;
    private String username;
    private String password;
    private String role;

    public Pegawai(int idPegawai, String username, String password, String role) {
        this.idPegawai = idPegawai;
        this.username = username;
        this.password = password;
        this.role = role;
    }

    public int getIdPegawai() {
        return idPegawai;
    }

    public void setIdPegawai(int idPegawai) {
        this.idPegawai = idPegawai;
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
