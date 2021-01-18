package com.toko.datatoko.config;

import com.toko.datatoko.constant.EnvConstant;

import java.sql.*;

public class DatabaseConfig {
    static Connection connection;
    static Statement statement;
    static ResultSet resultSet;

    public static Connection getConnection() throws ClassNotFoundException{
        try {
            Class.forName(EnvConstant.JDBC_DRIVER);
            connection = DriverManager.getConnection(EnvConstant.DB_URL,EnvConstant.DB_USER,EnvConstant.DB_PASSWORD);
            return connection;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void setAutoCommit(boolean autoCommit) throws SQLException {
        connection.setAutoCommit(autoCommit);
    }

    public void commit() throws SQLException{
        connection.commit();
    }

    public void rollback() throws SQLException {
        connection.rollback();
    }

    public void close() throws SQLException {
        connection.close();
    }

    public Statement createStatement() throws SQLException {
        return connection.createStatement();
    }

    public PreparedStatement preparedStatement(String query) throws SQLException {
        return connection.prepareStatement(query);
    }
}
