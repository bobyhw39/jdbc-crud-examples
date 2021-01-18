package com.toko.datatoko.repository;

import com.toko.datatoko.config.DatabaseConfig;
import com.toko.datatoko.constant.ErrorConstant;
import com.toko.datatoko.models.Produk;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProdukRepository {
    Connection conn;
    DatabaseConfig connection = new DatabaseConfig();

    public ProdukRepository(Connection connection)  throws ClassNotFoundException, SQLException {
        connection.setAutoCommit(false);
        conn = connection;
    }

    public String insert(Produk produk){
        String query = "INSERT INTO product(id_produk,nama_produk,category_id,merchant_name,created_date,status,unit) values(?,?,?,?,?,?,?)";

        try {
            PreparedStatement preparedStatement = connection.preparedStatement(query);
            preparedStatement.setString(1,null);
            preparedStatement.setString(2,produk.getNamaProduk());
            preparedStatement.setString(3,produk.getCategoryId());
            preparedStatement.setString(4,produk.getMerchantName());
            preparedStatement.setString(5,String.valueOf(produk.getCreatedDate()));
            preparedStatement.setString(6,String.valueOf(produk.getStatus()));
            preparedStatement.setString(7,String.valueOf(produk.getUnit()));
            int updated = preparedStatement.executeUpdate();
            connection.commit();
            preparedStatement.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            System.out.println(ErrorConstant.SQL_ROLLBACK);
            try{
                if(connection !=null){
                    connection.rollback();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return ErrorConstant.ADD_PRODUCT_FAILED;
        }
        return ErrorConstant.ADD_PRODUCT_SUCCESS;
    }

    public Produk findProdukById(int id){
        Produk forReturn = null;

        String query = "SELECT * from product p where p.id_produk = ?";
        try{
            PreparedStatement preparedStatement = connection.preparedStatement(query);
            preparedStatement.setString(1,String.valueOf(id));
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                forReturn = new Produk(
                        Integer.parseInt(resultSet.getString(1)),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getString(4),
                        Date.valueOf(resultSet.getString(5)),
                        Integer.parseInt(resultSet.getString(6)),
                        Integer.parseInt(resultSet.getString(7))
                );
            }

        } catch (SQLException e){
            e.printStackTrace();
        }
        return forReturn;
    }

    public List<Produk> getAllProducts(){
        List<Produk> produkList = new ArrayList<>();
        String query = "SELECT * FROM product";

        try {
            PreparedStatement preparedStatement = connection.preparedStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                produkList.add(new Produk(
                        Integer.parseInt(resultSet.getString(1)),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getString(4),
                        Date.valueOf(resultSet.getString(5)),
                        Integer.parseInt(resultSet.getString(6)),
                        Integer.parseInt(resultSet.getString(7))
                ));
            }
        } catch(Exception x){
            if(x instanceof SQLException){
                x.printStackTrace();
            } else if(x instanceof IndexOutOfBoundsException){
                return produkList;
            }
        }
        return produkList;
    }

    public String update(Produk produk){
        String query = "UPDATE product set nama_produk = ?,category_id = ?,merchant_name = ?,created_date = ?,status = ?,unit = ? where id_produk = ?";
        try{
            PreparedStatement preparedStatement = connection.preparedStatement(query);
            preparedStatement.setString(1,produk.getNamaProduk());
            preparedStatement.setString(2,produk.getCategoryId());
            preparedStatement.setString(3,produk.getMerchantName());
            preparedStatement.setString(4,String.valueOf(produk.getCreatedDate()));
            preparedStatement.setString(5,String.valueOf(produk.getStatus()));
            preparedStatement.setString(6,String.valueOf(produk.getUnit()));
            preparedStatement.setString(7,String.valueOf(produk.getIdProduk()));
            int updated = preparedStatement.executeUpdate();
            connection.commit();
            preparedStatement.close();
        } catch (SQLException e){
            e.printStackTrace();
            System.out.println(ErrorConstant.SQL_ROLLBACK);
            try{
                if(conn !=null){
                    connection.rollback();
                }
            } catch (SQLException e1){
                e1.printStackTrace();
            }
            return ErrorConstant.UPDATE_PRODUCT_FAILED;
        }
        return ErrorConstant.UPDATE_PRODUCT_SUCCESS;
    }
}
