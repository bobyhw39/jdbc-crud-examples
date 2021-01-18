package com.toko.datatoko.repository;

import com.toko.datatoko.config.DatabaseConfig;
import com.toko.datatoko.constant.ErrorConstant;
import com.toko.datatoko.models.Produk;
import com.toko.datatoko.models.Transaksi;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TransaksiRepository {
    Connection conn;
    DatabaseConfig connection = new DatabaseConfig();

    public TransaksiRepository(Connection connection) throws ClassNotFoundException,SQLException {
        connection.setAutoCommit(false);
        conn = connection;
    }

    public String insert(Transaksi transaksi){
        String query = "INSERT INTO transaksi(id,amount,created_date,updated_date,id_produk,status) values(?,?,?,?,?,?)";

        try {
            PreparedStatement preparedStatement = connection.preparedStatement(query);
            preparedStatement.setString(1,null);
            preparedStatement.setString(2,String.valueOf(transaksi.getAmount()));
            preparedStatement.setString(3,String.valueOf(transaksi.getCreatedDate()));
            preparedStatement.setString(4,String.valueOf(transaksi.getUpdatedDate()));
            preparedStatement.setString(5,String.valueOf(transaksi.getIdProduct()));
            preparedStatement.setString(6,String.valueOf(transaksi.getStatus()));
            int updated = preparedStatement.executeUpdate();
            conn.commit();
            preparedStatement.close();
        } catch (SQLException ex){
            ex.printStackTrace();
            System.out.println(ErrorConstant.SQL_ROLLBACK);
            try{
                if(conn !=null){
                    conn.rollback();
                }
            } catch (SQLException e1){
                e1.printStackTrace();
            }
            return ErrorConstant.CREATE_TRANSACTION_FAILED;
        }
        return ErrorConstant.CREATE_TRANSACTION_SUCCESS;
    }

    public List<Transaksi> getAllTransaki(){
        List<Transaksi> transaksiList = new ArrayList<>();
        String query = "SELECT * FROM transaksi";

        try{
            PreparedStatement preparedStatement = connection.preparedStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()){
                transaksiList.add(new Transaksi(
                        Integer.parseInt(resultSet.getString(1)),
                        new BigDecimal(resultSet.getString(2)),
                        Date.valueOf(resultSet.getString(3)),
                        Date.valueOf(resultSet.getString(4)),
                        Integer.parseInt(resultSet.getString(5)),
                        Integer.parseInt(resultSet.getString(6))
                ));
            }

        } catch (SQLException ex){
            ex.printStackTrace();
        }
        return transaksiList;
    }

    public String update(Transaksi transaksi){
        return null;
    }
}
