package com.TiTiApps.PleaseStoreThis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Iterator;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

@SpringBootApplication
public class PleaseStoreThisApplication {

	public static void main(String[] args) {
		SpringApplication.run(PleaseStoreThisApplication.class, args);

        String excelFileName = "EmployeeData.xlsx";
        String filePath = "src/main/resources/excel_file/" + excelFileName;
        String sheetName = "";
        

		try {

            FileInputStream excelFile = new FileInputStream(new File(filePath));
            Workbook workbook = new XSSFWorkbook(excelFile);
            Sheet datatypeSheet = workbook.getSheetAt(0);
            Iterator<Row> iterator = datatypeSheet.iterator();
			
			sheetName = workbook.getSheetName(0);

            while (iterator.hasNext()) {

                Row currentRow = iterator.next();
				Iterator<Cell> cellIterator = currentRow.iterator();

                while (cellIterator.hasNext()) {

                    Cell currentCell = cellIterator.next();
                    if (currentCell.getCellType() == CellType.STRING) {
                        System.out.print(currentCell.getStringCellValue() + "--");
                    } else if (currentCell.getCellType() == CellType.NUMERIC) {
                        System.out.print(currentCell.getNumericCellValue() + "--");
                    }

                }
                System.out.println();
            }

            workbook.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        
        Connection con = null;
        Statement stmt = null;
        
        
        try {
            Class.forName("org.hsqldb.jdbc.JDBCDriver");
            con = DriverManager.getConnection("jdbc:hsqldb:hsql://localhost/excel-tables-db", "SA", "");
            stmt = con.createStatement();
            
            stmt.executeUpdate("CREATE TABLE " + sheetName + " (id INT NOT NULL, title VARCHAR(50) NOT NULL, author VARCHAR(20) NOT NULL, submission_date DATE, PRIMARY KEY (id));");
            
            System.out.println("Table " + sheetName + " created successfully");

        }  catch (Exception e) {
            e.printStackTrace(System.out);
        }

	}

}