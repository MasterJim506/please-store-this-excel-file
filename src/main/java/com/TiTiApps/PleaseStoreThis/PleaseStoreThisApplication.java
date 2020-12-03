package com.TiTiApps.PleaseStoreThis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PleaseStoreThisApplication {

    private static String excelFileName = "EmployeeData.xlsx";
    private static String filePath = "src/main/resources/excel_file/" + excelFileName;

	public static void main(String[] args) {
		SpringApplication.run(PleaseStoreThisApplication.class, args);
        
        ExcelFileObjects excelFileObjects = new ExcelFileObjects(filePath);

        ExcelTableStorer excelTableStorer = new ExcelTableStorer();

        excelTableStorer.storeExcelTable(excelFileObjects);

	}

}