package com.TiTiApps.PleaseStoreThis;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import java.util.ArrayList;
import java.util.Iterator;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelImporter {
    
    public static void importExcelFile(String filePath, String tableName) {
        
        //Create table if it doesn't exist yet
        HsqldbDatabaseWrapper importerWrapper = new HsqldbDatabaseWrapper();
        
        importerWrapper.createTable(tableName);

        importerWrapper.insertIntoTable(createRowsToInsert(filePath), tableName);

        importerWrapper.printTableContent(tableName);

    }
    
    public static ArrayList<RowToInsert> createRowsToInsert(String filePath) {
        
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
}
