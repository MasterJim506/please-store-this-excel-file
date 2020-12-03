@echo off
title super script title!
cd %~dp0\hsqldb-2.5.1\hsqldb
start /b java -classpath lib/hsqldb.jar org.hsqldb.server.Server --database.0 file:data/excel-tables-db/excel-tables-db --dbname.0 excel-tables-db
cd %~dp0\target
java -jar PleaseStoreThis-0.0.1-SNAPSHOT.jar
pause