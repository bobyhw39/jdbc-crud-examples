package com.toko.datatoko.services;

import com.toko.datatoko.constant.ErrorConstant;
import com.toko.datatoko.models.Produk;
import com.toko.datatoko.repository.ProdukRepository;
import com.toko.datatoko.utils.TimeUtils;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class ProdukServices {

    ProdukRepository produkRepository;

    public ProdukServices(ProdukRepository produkRepository) {
        this.produkRepository = produkRepository;
    }

    public List<Produk> getGudangProduk(){
        List<Produk> allProducts = produkRepository.getAllProducts();
        return allProducts;
    }

    public Produk getProdukById(int id){
        try{
            Produk produks = getGudangProduk()
                    .stream()
                    .filter(produk -> produk.getIdProduk() == id)
                    .collect(Collectors.toList())
                    .get(0);
            return produks;
        } catch (IndexOutOfBoundsException x){
            return new Produk();
        }
    }

    public String addProduct(String namaProduk,String categoryId,String merchantName,int unit){
        try{
            return produkRepository.insert(
                    new Produk(0,namaProduk,categoryId,merchantName, TimeUtils.currentDateTime(),0,unit)
            );
        } catch (Exception x){
            x.printStackTrace();
            return ErrorConstant.ADD_PRODUCT_FAILED;
        }
    }

    public String updateProduct(int id,String namaProduk,int unit){
        Produk produk = produkRepository.findProdukById(id);
        produk.setNamaProduk(namaProduk);
        produk.setUnit(unit);
        return produkRepository.update(produk);
    }

    public String deleteProduct(int id){
        Produk produk = produkRepository.findProdukById(id);
        produk.setStatus(1);
        return produkRepository.update(produk);
    }


}
