package com.toko.datatoko.models;

import java.sql.Date;

public class Produk {
    private int idProduk;
    private String namaProduk;
    private String categoryId;
    private String merchantName;
    private Date createdDate;
    private int status;
    private int unit;

    public Produk() {
    }

    public Produk(int idProduk, String namaProduk, String categoryId, String merchantName, Date createdDate, int status, int unit) {
        this.idProduk = idProduk;
        this.namaProduk = namaProduk;
        this.categoryId = categoryId;
        this.merchantName = merchantName;
        this.createdDate = createdDate;
        this.status = status;
        this.unit = unit;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }

    public int getUnit() {
        return unit;
    }

    public void setUnit(int unit) {
        this.unit = unit;
    }

    public int getIdProduk() {
        return idProduk;
    }

    public void setIdProduk(int idProduk) {
        this.idProduk = idProduk;
    }

    public String getNamaProduk() {
        return namaProduk;
    }

    public void setNamaProduk(String namaProduk) {
        this.namaProduk = namaProduk;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantId(String merchantName) {
        this.merchantName = merchantName;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Produk{");
        sb.append("idProduk=").append(idProduk);
        sb.append(", namaProduk='").append(namaProduk).append('\'');
        sb.append(", categoryId='").append(categoryId).append('\'');
        sb.append(", merchantName='").append(merchantName).append('\'');
        sb.append(", createdDate=").append(createdDate);
        sb.append(", status=").append(status);
        sb.append(", unit=").append(unit);
        sb.append('}');
        return sb.toString();
    }
}
