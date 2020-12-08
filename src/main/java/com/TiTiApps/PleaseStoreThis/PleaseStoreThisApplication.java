package com.TiTiApps.PleaseStoreThis;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import static com.TiTiApps.PleaseStoreThis.ExcelImporter.createRowsToInsert;

@SpringBootApplication
public class PleaseStoreThisApplication {

    private static String excelFileName = "EmployeeData.xlsx";
    private static String filePath = "src/main/resources/excel_file/" + excelFileName;
    private static String dbName = "excel-tables-db";
    private static String tableName = "EmployeesInfo";
    private static DatabaseWrapper wrapper;
	public static void main(String[] args) {
        
        SpringApplication.run(PleaseStoreThisApplication.class, args);

        createDBTable(tableName);
        
        ArrayList<RowToInsert> rowsToInsert = createRowsToInsert(filePath);

        System.out.println("\nRows to insert : " + rowsToInsert.toString());
        System.out.println();

        insertRows(rowsToInsert, tableName);

        printTableRows(tableName);
        
    }

    public static void createDBTable(String tableName){
        
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
    
    
    public static void insertRows(ArrayList<RowToInsert> rowsToInsert, String tableName) {

        Statement stmt = null; 
        int result = 0;

        try {

            Class.forName("org.hsqldb.jdbc.JDBCDriver");
            stmt = getConnectionToDB().createStatement();
            result = stmt.executeUpdate(generateUpdateSQLStatement(rowsToInsert, tableName));
            getConnectionToDB().commit();

        } catch (Exception e) { 
            e.printStackTrace(System.out); 
        }

        System.out.println(result + " rows affected" + "\n");

    }

    public static String generateUpdateSQLStatement(ArrayList<RowToInsert> rowsToInsert, String tableName) {
        
        String updateSQLStatement = "INSERT INTO " + tableName + " VALUES ";

        for(RowToInsert row : rowsToInsert) {
            
            updateSQLStatement += "(NULL, '" + row.getEmployeeNumber() + "', '" + row.getFirstName() + "', '" + row.getLastName() + "')";

            if (row != rowsToInsert.get(rowsToInsert.size()-1)) {
                updateSQLStatement += ", ";
            }

        }

        updateSQLStatement += ";";

        System.out.println("Insert statement : " + updateSQLStatement + "\n");

        return updateSQLStatement;
    }

    public static void printTableRows(String tableName) {

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

    public static Connection getConnectionToDB() {
        
        Connection con = null;
        
        try {

            Class.forName("org.hsqldb.jdbc.JDBCDriver"); 
            con = DriverManager.getConnection( "jdbc:hsqldb:hsql://localhost/" + dbName, "SA", ""); 
    
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }

        return con;
    }

}