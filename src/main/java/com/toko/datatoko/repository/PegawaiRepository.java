package com.toko.datatoko.repository;

import com.toko.datatoko.config.DatabaseConfig;
import com.toko.datatoko.constant.ErrorConstant;
import com.toko.datatoko.models.Account;
import com.toko.datatoko.models.Pegawai;
import com.toko.datatoko.models.Produk;
import com.toko.datatoko.utils.SecurityUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PegawaiRepository {

    Connection conn;
    DatabaseConfig connection = new DatabaseConfig();

    public PegawaiRepository(Connection connection) throws ClassNotFoundException,SQLException {
        connection.setAutoCommit(false);
        this.connection.setAutoCommit(false);
        conn = connection;
    }

    public String insert(Pegawai pegawai){
        String query = "INSERT INTO pegawai(id_pegawai,username,password,role) values(?,?,?,?)";
        try {
            PreparedStatement preparedStatement = connection.preparedStatement(query);
            preparedStatement.setString(1,null);
            preparedStatement.setString(2, pegawai.getUsername());
            preparedStatement.setString(3, pegawai.getPassword());
            preparedStatement.setString(4,pegawai.getRole());
            int updated = preparedStatement.executeUpdate();
            System.out.println("Row Impacted : " + updated);
            connection.commit();
            preparedStatement.close();
        } catch (SQLException ex){
            ex.printStackTrace();
            System.out.println(ErrorConstant.SQL_ROLLBACK);
            try{
                if(conn !=null){
                    connection.rollback();
                }
            } catch (SQLException e1){
                e1.printStackTrace();
            }
            return ErrorConstant.CREATE_ACCOUNT_FAILED;
        }
        return ErrorConstant.CREATE_ACCOUNT_SUCCESS;
    }

    public List<Pegawai> findPegawaiByUsername(String username){
        List<Pegawai> pegawaiList = new ArrayList<>();

        String query = "SELECT * from pegawai acc where acc.username = ?";
        try {
            PreparedStatement preparedStatement = connection.preparedStatement(query);
            preparedStatement.setString(1,username);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                pegawaiList.add(
                        new Pegawai(
                                Integer.parseInt(resultSet.getString(1)),
                                resultSet.getString(2),
                                resultSet.getString(3),
                                resultSet.getString(4)
                        )
                );
            }
            preparedStatement.close();
        } catch (SQLException e){
            e.printStackTrace();
        }
        return pegawaiList;
    }

    public String update(Pegawai pegawai){
        String query = "UPDATE pegawai set password = ?,role = ? where username = ?";
        try{
            PreparedStatement preparedStatement = connection.preparedStatement(query);
            preparedStatement.setString(1,String.valueOf(pegawai.getPassword()));
            preparedStatement.setString(2,String.valueOf(pegawai.getRole()));
            preparedStatement.setString(3,String.valueOf(pegawai.getUsername()));
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
            } catch (SQLException e1){
                e1.printStackTrace();
            }
            return ErrorConstant.CREATE_ACCOUNT_FAILED;
        }
        return ErrorConstant.UPDATE_ACCOUNT_SUCCESS;
    }

    public String delete(String username){
        String query = "DELETE from Pegawai WHERE username=?";
        try {
           PreparedStatement preparedStatement = connection.preparedStatement(query);
           preparedStatement.setString(1,String.valueOf(username));
           int updated = preparedStatement.executeUpdate();
           connection.commit();
           preparedStatement.close();
        } catch(SQLException x){
            x.printStackTrace();
            System.out.println(ErrorConstant.SQL_ROLLBACK);
            try {
                if(connection != null){
                    connection.rollback();
                }
            } catch (SQLException e){
                e.printStackTrace();
            }
            return ErrorConstant.SQL_ROLLBACK;
        }
        return ErrorConstant.DELETE_ACCOUNT_SUCCESS;
    }
}
