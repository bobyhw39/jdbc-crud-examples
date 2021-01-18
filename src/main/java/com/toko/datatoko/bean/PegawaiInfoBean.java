package com.toko.datatoko.bean;

public class PegawaiInfoBean {
    private int idPegawai;
    private String username;
    private String role;

    public PegawaiInfoBean(int idPegawai, String username, String role) {
        this.idPegawai = idPegawai;
        this.username = username;
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

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
