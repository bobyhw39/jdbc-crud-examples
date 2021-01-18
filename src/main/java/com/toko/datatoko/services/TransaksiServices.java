package com.toko.datatoko.services;

import com.toko.datatoko.constant.ErrorConstant;
import com.toko.datatoko.models.Produk;
import com.toko.datatoko.models.Transaksi;
import com.toko.datatoko.repository.ProdukRepository;
import com.toko.datatoko.repository.TransaksiRepository;
import com.toko.datatoko.utils.TimeUtils;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public class TransaksiServices {
    TransaksiRepository transaksiRepository;
    ProdukServices produkServices;

    public TransaksiServices(TransaksiRepository transaksiRepository, ProdukServices produkServices) {
        this.transaksiRepository = transaksiRepository;
        this.produkServices = produkServices;
    }

    public String createTransaction(BigDecimal amount, int idProduk, int status){
        Optional<Produk> validateProduk = Optional.ofNullable(produkServices.getProdukById(idProduk));
        if(validateProduk.isPresent()){
            return ErrorConstant.PRODUCT_NOT_FOUND;
        }
        if(amount.compareTo(BigDecimal.ZERO) == 0){
            return ErrorConstant.INVALID_AMOUNT_FORMAT;
        }
        try {
            Transaksi transaksi = new Transaksi(
                    0,
                    amount,
                    TimeUtils.currentDateTime(),
                    TimeUtils.currentDateTime(),
                    idProduk,
                    0
            );
            transaksiRepository.insert(transaksi);
            return ErrorConstant.CREATE_TRANSACTION_SUCCESS;
        } catch (Exception x){
            x.printStackTrace();
            return ErrorConstant.CREATE_TRANSACTION_FAILED;
        }
    }

    public List<Transaksi> showAllTransaksi(){
        List<Transaksi> transaksiList = transaksiRepository.getAllTransaki();
        return transaksiList;
    }

}
