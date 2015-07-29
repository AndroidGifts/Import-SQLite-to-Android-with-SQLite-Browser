package com.androidgifts.gift.importsqliteintoandroid.data;

import java.util.List;

/**
 * Created by Android Gifts on 7/27/2015.
 */
public class Employee {
    private int empID;
    private String empName;
    private int empAge;
    private String empMail;

    private List<String> empPhone;

    public Employee(int empID, String empName, int empAge, String empMail, List<String> empPhone) {
        this.empID = empID;
        this.empName = empName;
        this.empAge = empAge;
        this.empMail = empMail;
        this.empPhone = empPhone;
    }

    public List<String> getEmpPhone() {
        return empPhone;
    }

    public void setEmpPhone(List<String> empPhone) {
        this.empPhone = empPhone;
    }

    public String getEmpMail() {
        return empMail;
    }

    public void setEmpMail(String empMail) {
        this.empMail = empMail;
    }

    public int getEmpID() {
        return empID;
    }

    public void setEmpID(int empID) {
        this.empID = empID;
    }

    public String getEmpName() {
        return empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
    }

    public int getEmpAge() {
        return empAge;
    }

    public void setEmpAge(int empAge) {
        this.empAge = empAge;
    }
}
