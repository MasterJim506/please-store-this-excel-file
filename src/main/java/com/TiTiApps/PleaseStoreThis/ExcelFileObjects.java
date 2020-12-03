package com.TiTiApps.PleaseStoreThis;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Iterator;

public class ExcelFileObjects {

    private String sheetName = "";

	public ExcelFileObjects(String filePath) {
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
    }
    
    public String getSheetName(){
        return sheetName;
    }
}

    
    






