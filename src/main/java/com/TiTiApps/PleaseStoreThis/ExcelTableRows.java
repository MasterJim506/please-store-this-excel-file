package com.TiTiApps.PleaseStoreThis;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

public class ExcelTableRows {

    private String sheetName = "";
    private ArrayList<ExcelTableRow> excelTableRows = new ArrayList<ExcelTableRow>();

	public ExcelTableRows(String filePath) {
        try {

            FileInputStream excelFile = new FileInputStream(new File(filePath));
            Workbook workbook = new XSSFWorkbook(excelFile);
            Sheet datatypeSheet = workbook.getSheetAt(0);
            Iterator<Row> iterator = datatypeSheet.iterator();
            
            sheetName = workbook.getSheetName(0);
    
            while (iterator.hasNext()) {
    
                Row currentRow = iterator.next();
                ExcelTableRow excelTableRow = new ExcelTableRow();                
                Iterator<Cell> cellIterator = currentRow.iterator();
                
                if(currentRow.getRowNum() != 0) {
                    while (cellIterator.hasNext()) {
                        
                        Cell currentCell = cellIterator.next();
    
                        if (currentCell.getCellType() == CellType.STRING) {                            
                            
                            switch(currentCell.getColumnIndex()) {
                                
                                case 0:
                                excelTableRow.setEmployeeNumber(currentCell.getStringCellValue());
                                break;

                                case 1:
                                excelTableRow.setFirstName(currentCell.getStringCellValue());
                                break;

                                case 2:
                                excelTableRow.setLastName(currentCell.getStringCellValue());
                                break;
                            }
    
                        } else if (currentCell.getCellType() == CellType.NUMERIC) {
    
                            switch(currentCell.getColumnIndex()) {
                                
                                case 0:
                                excelTableRow.setEmployeeNumber(String.valueOf(currentCell.getNumericCellValue()));
                                break;

                                case 1:
                                excelTableRow.setFirstName(String.valueOf(currentCell.getNumericCellValue()));
                                break;

                                case 2:
                                excelTableRow.setLastName(String.valueOf(currentCell.getNumericCellValue()));
                                break;
    
                            }                            
                        }
                    }
                    
                    excelTableRows.add(excelTableRow);

                }
                
            }
                System.out.println();
                workbook.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public String getSheetName(){
        return sheetName;
    }
    public ArrayList<ExcelTableRow> getExcelTableRows(){
        return excelTableRows;
    }

    public void printTableCells(){
        
    }
}

    
    






