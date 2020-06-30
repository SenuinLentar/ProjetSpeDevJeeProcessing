/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.appjee.processing.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author myal6
 */
public class DAO {

    private Connection conn = null;
    private Statement stmt = null;

    public void connectionToDb() throws ClassNotFoundException {
        try {

            String url = "jdbc:oracle:thin:@localhost:1521:orcl";
            conn = DriverManager.getConnection(url, "dictionaryJee", "admin");

            stmt = conn.createStatement();

        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    public Boolean getWordQuery(String word) throws SQLException {
        ResultSet rs;
        String result = "";
        
        //System.out.println("select * from dictionaryfr where words = '" + word + "'");
        
        rs = stmt.executeQuery("select * from dictionaryfr where words = '" + word + "'");
        while (rs.next()) {
            result = rs.getString("words");
        }
        return result.equals(word);
    }

    public void closeConnection() throws SQLException {
        conn.close();
    }
}
