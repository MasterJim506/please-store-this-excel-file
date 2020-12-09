package com.TiTiApps.PleaseStoreThis;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class HsqldbDatabaseWrapper implements DatabaseWrapper {

    public Connection getConnectionToDB() {
        
        Connection con = null;
        
        try {

            Class.forName("org.hsqldb.jdbc.JDBCDriver"); 
            con = DriverManager.getConnection( "jdbc:hsqldb:hsql://localhost/excel-tables-db", "SA", ""); 
    
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }

        return con;
    }

    public void createTable(String tableName) {
        
        Statement stmt = null;    
        
        try {

            stmt = getConnectionToDB().createStatement();
            
            try {
            
                stmt.executeUpdate("CREATE TABLE IF NOT EXISTS " + tableName + " (id INTEGER IDENTITY PRIMARY KEY, employeenumber VARCHAR(50) NOT NULL, firstname VARCHAR(50) NOT NULL, lastname VARCHAR(50) NOT NULL);");

            } catch (Exception e) {
                e.printStackTrace(System.out);                
            }

        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
    }

    public void insertIntoTable(ArrayList<RowToInsert> rowsToInsert, String tableName) {

        String updateSQLStatement = "INSERT INTO " + tableName + " VALUES ";

        for(RowToInsert row : rowsToInsert) {
            
            updateSQLStatement += "(NULL, '" + row.getEmployeeNumber() + "', '" + row.getFirstName() + "', '" + row.getLastName() + "')";

            if (row != rowsToInsert.get(rowsToInsert.size()-1)) {
                updateSQLStatement += ", ";
            }

        }

        updateSQLStatement += ";";

        Statement stmt = null; 
        int result = 0;

        try {

            stmt = getConnectionToDB().createStatement();
            result = stmt.executeUpdate(updateSQLStatement);
            getConnectionToDB().commit();

        } catch (Exception e) { 
            e.printStackTrace(System.out); 
        }

        System.out.println(result + " rows affected" + "\n");

    }

    public void printTableContent(String tableName) {
        Statement stmt = null;
        ResultSet result = null;

        try {

            stmt = getConnectionToDB().createStatement();
            result = stmt.executeQuery("SELECT id, employeenumber, firstname, lastname FROM " + tableName);

            System.out.println("Values from table " + tableName + " :\n");

            while(result.next()){
                System.out.println(result.getInt("id") + " | " + 
                result.getString("employeeNumber") + " | " +
                result.getString("firstName") + " | " +
                result.getString("lastName"));
            }

        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
    }
    
}
