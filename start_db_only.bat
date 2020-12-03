@echo off
title super script title!
cd %~dp0\hsqldb-2.5.1\hsqldb
java -classpath lib/hsqldb.jar org.hsqldb.server.Server --database.0 file:data/excel-tables-db/excel-tables-db --dbname.0 excel-tables-db
pause