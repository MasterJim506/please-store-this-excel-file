package com.TiTiApps.PleaseStoreThis;

import static com.TiTiApps.PleaseStoreThis.ExcelImporter.importExcelFile;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PleaseStoreThisApplication {

    private static String filePath = "src/main/resources/excel_file/EmployeeData.xlsx";
    private static String tableName = "EmployeesInfo";

	public static void main(String[] args) {
        
        SpringApplication.run(PleaseStoreThisApplication.class, args);

        importExcelFile(filePath, tableName);
        
    }

}