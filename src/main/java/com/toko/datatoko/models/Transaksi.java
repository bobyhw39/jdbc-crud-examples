package com.toko.datatoko.models;

import java.math.BigDecimal;
import java.sql.Date;

public class Transaksi {
    private int id;
    private BigDecimal amount;
    private Date createdDate;
    private Date updatedDate;
    private int idProduct;
    private int status;

    public Transaksi(int id, BigDecimal amount, Date createdDate, Date updatedDate, int idProduct, int status) {
        this.id = id;
        this.amount = amount;
        this.createdDate = createdDate;
        this.updatedDate = updatedDate;
        this.idProduct = idProduct;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(Date updatedDate) {
        this.updatedDate = updatedDate;
    }

    public int getIdProduct() {
        return idProduct;
    }

    public void setIdProduct(int idProduct) {
        this.idProduct = idProduct;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Transaksi{");
        sb.append("id=").append(id);
        sb.append(", amount=").append(amount);
        sb.append(", createdDate=").append(createdDate);
        sb.append(", updatedDate=").append(updatedDate);
        sb.append(", idProduct=").append(idProduct);
        sb.append(", status=").append(status);
        sb.append('}');
        return sb.toString();
    }
}
