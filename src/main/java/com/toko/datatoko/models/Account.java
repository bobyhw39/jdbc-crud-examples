package com.toko.datatoko.models;

public class Account {
    private int id;
    private String username;
    private String password;
    private String alamat;
    private String noTelepon;

    public Account(int id, String username, String password, String alamat, String noTelepon) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.alamat = alamat;
        this.noTelepon = noTelepon;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getNoTelepon() {
        return noTelepon;
    }

    public void setNoTelepon(String noTelepon) {
        this.noTelepon = noTelepon;
    }
}
