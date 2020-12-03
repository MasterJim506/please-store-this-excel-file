package com.TiTiApps.PleaseStoreThis;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class ExcelTableStorer {

    public ExcelTableStorer(){

    }

    public void storeExcelTable(ExcelFileObjects excelFileObjects){
        Connection con = null;
        Statement stmt = null;    
        
        try {
            Class.forName("org.hsqldb.jdbc.JDBCDriver");
            con = DriverManager.getConnection("jdbc:hsqldb:hsql://localhost/excel-tables-db", "SA", "");
            stmt = con.createStatement();
            
            stmt.executeUpdate("CREATE TABLE " + excelFileObjects.getSheetName() + " (id INT NOT NULL, title VARCHAR(50) NOT NULL, author VARCHAR(20) NOT NULL, submission_date DATE, PRIMARY KEY (id));");
            
            System.out.println("Table " + excelFileObjects.getSheetName() + " created successfully");

        }  catch (Exception e) {
            e.printStackTrace(System.out);
        }
    }
}
