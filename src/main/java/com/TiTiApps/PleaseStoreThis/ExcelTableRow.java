package com.TiTiApps.PleaseStoreThis;

public class ExcelTableRow {
    
    private String employeeNumber;
    private String firstName;
    private String lastName;

    public ExcelTableRow(){

    }

    public String toString(){
        return getEmployeeNumber() + " - " + getFirstName() + " - " + getLastName();
    }

    public String getEmployeeNumber() {
        return employeeNumber;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }
    
    public void setEmployeeNumber(String employeeNumber) {
        this.employeeNumber = employeeNumber;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

}