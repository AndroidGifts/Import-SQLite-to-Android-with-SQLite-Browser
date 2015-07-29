package com.androidgifts.gift.importsqliteintoandroid.data;

/**
 * Created by Android Gifts on 7/27/2015.
 */
public class Department {
    private int depID;
    private String depName;

    public int getDepID() {
        return depID;
    }

    public void setDepID(int depID) {
        this.depID = depID;
    }

    public String getDepName() {
        return depName;
    }

    public void setDepName(String depName) {
        this.depName = depName;
    }

    public Department(int depID, String depName) {
        this.depID = depID;
        this.depName = depName;
    }
}
