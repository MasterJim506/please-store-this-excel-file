package com.TiTiApps.PleaseStoreThis;

import java.sql.Connection;
import java.util.ArrayList;

public interface DatabaseWrapper {

    public Connection getConnectionToDB();

    public void createTable(String tableName);

    public void insertIntoTable(ArrayList<RowToInsert> rowsToInsert, String tableName);

    //as-is, it's for test purposes
    public void printTableContent(String tableName);
    
}
