package com.TiTiApps.PleaseStoreThis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.ResultSet;

@SpringBootApplication
public class PleaseStoreThisApplication {

    private static String excelFileName = "EmployeeData.xlsx";
    private static String filePath = "src/main/resources/excel_file/" + excelFileName;
    private static String tableName = "EmployeesInfo";

	public static void main(String[] args) {
        
        SpringApplication.run(PleaseStoreThisApplication.class, args);
        createDBTable(tableName);
        
        ArrayList<RowToInsert> rowsToInsert = createRows(filePath);

        System.out.println("\nRows to insert : " + rowsToInsert.toString());
        System.out.println();

        insertRows(rowsToInsert, tableName);
        
    }

    public static void createDBTable(String tableName){
        Connection con = null;
        Statement stmt = null;    
        
        try {
            Class.forName("org.hsqldb.jdbc.JDBCDriver");
            con = DriverManager.getConnection("jdbc:hsqldb:hsql://localhost/excel-tables-db", "SA", "");
            stmt = con.createStatement();
            
            try {
            
                stmt.executeUpdate("CREATE TABLE IF NOT EXISTS " + tableName + " (id INT IDENTITY NOT NULL, employeenumber INT NOT NULL, firstname VARCHAR(50) NOT NULL, lastname VARCHAR(50) NOT NULL, PRIMARY KEY (id));");

                ResultSet result = null;
                
                try {
                    Class.forName("org.hsqldb.jdbc.JDBCDriver");

                    result = stmt.executeQuery(
                        "SELECT id, employeenumber, firstname, lastname FROM " + tableName);
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
    
    public static ArrayList<RowToInsert> createRows(String filePath) {
        
        ArrayList<RowToInsert> rowsToInsert = new ArrayList<RowToInsert>();

        try {         
            
            FileInputStream excelFile = new FileInputStream(new File(filePath));
            Workbook workbook = new XSSFWorkbook(excelFile);
            Sheet datatypeSheet = workbook.getSheetAt(0);
            Iterator<Row> iterator = datatypeSheet.iterator();
    
            while (iterator.hasNext()) {
    
                Row currentRow = iterator.next();
                RowToInsert rowToInsert = new RowToInsert();                
                Iterator<Cell> cellIterator = currentRow.iterator();
                
                if(currentRow.getRowNum() != 0) {
                    while (cellIterator.hasNext()) {
                        
                        Cell currentCell = cellIterator.next();
                        String currentCellValue = null;
    
                        if (currentCell.getCellType() == CellType.STRING) {

                            currentCellValue = currentCell.getStringCellValue();

                        } else if (currentCell.getCellType() == CellType.NUMERIC) {

                            currentCellValue = String.valueOf(currentCell.getNumericCellValue());

                        }   
                        
                        switch(currentCell.getColumnIndex()) {
                                
                            case 0:                                
                            rowToInsert.setEmployeeNumber(currentCellValue);
                            break;

                            case 1:
                            rowToInsert.setFirstName(currentCellValue);
                            break;

                            case 2:
                            rowToInsert.setLastName(currentCellValue);
                            break;
                        }    
                    }
                    
                    rowsToInsert.add(rowToInsert);

                }
            }
                            
            workbook.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return rowsToInsert;

    }

    public static void generateInsertSQLStatement(ArrayList<RowToInsert> rowsToInsert, String tableName) {
        String insertSQLStatement = "INSERT INTO " + tableName;

        for(RowToInsert row : rowsToInsert) {
            
            insertSQLStatement += " VALUES (" + row.getEmployeeNumber() + ", " + row.getFirstName() + ", " + row.getLastName() + ", NOW())";

            if (row != rowsToInsert.get(rowsToInsert.size()-1)) {
                insertSQLStatement += ", ";
            }

        }

        insertSQLStatement += ";";

        System.out.println(insertSQLStatement);
    }

    public static void insertRows(ArrayList<RowToInsert> rowsToInsert, String tableName) {
        
        generateInsertSQLStatement(rowsToInsert, tableName);
        
    }

}