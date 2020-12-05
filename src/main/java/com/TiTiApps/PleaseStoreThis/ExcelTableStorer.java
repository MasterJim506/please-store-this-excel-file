package com.TiTiApps.PleaseStoreThis;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.ResultSet;

public class ExcelTableStorer {

    public ExcelTableStorer(){

    }

    public void storeExcelTable(ExcelTableRows excelTableRows){
        Connection con = null;
        Statement stmt = null;    
        
        try {
            Class.forName("org.hsqldb.jdbc.JDBCDriver");
            con = DriverManager.getConnection("jdbc:hsqldb:hsql://localhost/excel-tables-db", "SA", "");
            stmt = con.createStatement();
            
            try {
            
                stmt.executeUpdate("CREATE TABLE " + excelTableRows.getSheetName() + " (id INT NOT NULL, employeenumber INT NOT NULL, firstname VARCHAR(50) NOT NULL, lastname VARCHAR(50) NOT NULL, PRIMARY KEY (id));");
                System.out.println("Table " + excelTableRows.getSheetName() + " created successfully");

                ResultSet result = null;

                System.out.println("je suis dans le show");
                
                try {
                    System.out.println("je suis dans le debut du try");
                    Class.forName("org.hsqldb.jdbc.JDBCDriver");

                    result = stmt.executeQuery(
                        "SELECT id, employeenumber, firstname, lastname FROM " + excelTableRows.getSheetName());
                        System.out.println("je suis  direct apres le set du result");
                    while(result.next()){
                        System.out.println(result.getInt("id")+" | "+
                        result.getString("title")+" | "+
                        result.getString("author"));
                    }
                } catch (Exception e) {
                    e.printStackTrace(System.out);
                }

            } catch (Exception e) {
                e.printStackTrace(System.out);                
            }

        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
    }
}
