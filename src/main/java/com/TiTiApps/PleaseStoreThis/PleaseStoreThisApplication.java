package com.TiTiApps.PleaseStoreThis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PleaseStoreThisApplication {

    private static String excelFileName = "EmployeeData.xlsx";
    private static String filePath = "src/main/resources/excel_file/" + excelFileName;

	public static void main(String[] args) {
		SpringApplication.run(PleaseStoreThisApplication.class, args);
        
        ExcelTableRows excelTableRows = new ExcelTableRows(filePath);

        System.out.println(excelTableRows.getExcelTableRows().toString());

        //ExcelTableStorer excelTableStorer = new ExcelTableStorer();

        //excelTableStorer.storeExcelTable(excelTableRows);

	}

}