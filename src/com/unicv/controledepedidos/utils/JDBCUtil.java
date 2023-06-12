/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.unicv.controledepedidos.utils;

import java.sql.*;
import java.util.Properties;
import java.sql.DriverManager;

/**
 *
 * @author erik
 */
public class JDBCUtil {
     //jdbc:sqlserver://[serverName[\instanceName][:portNumber]][;property=value[;property=value]]
    public static Connection getConnection() throws SQLException {
        String host = "localhost";
        String port = "1433";
        String dbms = "sqlserver";
        Properties connProps = new Properties();
        connProps.put("Database", "pedidos_db");
        connProps.put("user", "sa");
        connProps.put("password", "Ciego@123");
        connProps.put("encrypt", "false");
        String url = String.format("jdbc:%1$s://%2$s:%3$s",
                dbms, host, port);
        Connection conn;
        conn = (Connection) java.sql.DriverManager.getConnection(url, connProps);
        conn.setAutoCommit(false);
        return conn;
    }

    public static void commit(Connection conn) throws SQLException {

        if (conn != null) {
            conn.commit();
        }

    }

    public static void rollback(Connection conn) throws SQLException {

        if (conn != null) {
            conn.rollback();
        }
    }
}
