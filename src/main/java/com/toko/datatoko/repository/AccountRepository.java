package com.toko.datatoko.repository;

import com.toko.datatoko.config.DatabaseConfig;
import com.toko.datatoko.constant.ErrorConstant;
import com.toko.datatoko.models.Account;
import com.toko.datatoko.utils.SecurityUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AccountRepository {
    Connection conn;
    DatabaseConfig connection = new DatabaseConfig();

    public AccountRepository(Connection connection) throws ClassNotFoundException,SQLException {
        connection.setAutoCommit(false);
        conn = connection;
    }

    public String insert(Account account){
        String query = "INSERT INTO account(id,username,password,alamat,no_telepon) values(?,?,?,?,?)";
        String hashPassword = SecurityUtils.passwordEncode(account.getPassword());
        System.out.println(hashPassword);
        try {
            PreparedStatement preparedStatement = connection.preparedStatement(query);
            preparedStatement.setString(1,null);
            preparedStatement.setString(2, account.getUsername());
            preparedStatement.setString(3, hashPassword);
            preparedStatement.setString(4,account.getAlamat());
            preparedStatement.setString(5,account.getNoTelepon());
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
            return ErrorConstant.CREATE_ACCOUNT_FAILED;
        }
        return ErrorConstant.CREATE_ACCOUNT_SUCCESS;
    }

//    public String update(Account account){
//        String query = "UPDATE account set password = ?, alamat = ?, no_telepon = ? where username = ?";
//        try{
//            PreparedStatement preparedStatement = connection.prepareStatement(query)
//        }
//    }


}
