package com.androidgifts.gift.importsqliteintoandroid.helper;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.androidgifts.gift.importsqliteintoandroid.data.Department;
import com.androidgifts.gift.importsqliteintoandroid.data.Employee;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Android Gifts on 7/27/2015.
 */
public class EmployeeDBHelper extends SQLiteOpenHelper {
    private static String DB_PATH = "";
    private static String DB_NAME = "employeedb";
    private static String TABLE_DEPARTMENT = "Department";
    private static String TABLE_EMPLOYEE = "Employee";
    private static String TABLE_PHONE = "EmployeePhone";

    private final Context context;
    private SQLiteDatabase db;

    public EmployeeDBHelper(Context context) {
        super(context, DB_NAME, null, 1);

        if (android.os.Build.VERSION.SDK_INT >= 17) {
            DB_PATH = context.getApplicationInfo().dataDir + "/databases/";
        } else {
            DB_PATH = "/data/data/" + context.getPackageName() + "/databases/";
        }
        this.context = context;
    }

    // Creates a empty database on the system and rewrites it with your own database.
    public void create() throws IOException {
        boolean dbExist = checkDataBase();

        if (dbExist) {
            //do nothing - database already exist
        } else {
            // By calling this method and empty database will be created into the default system path
            // of your application so we are gonna be able to overwrite that database with our database.
            this.getReadableDatabase();
            try {
                copyDataBase();
            } catch (IOException e) {
                throw new Error("Error copying database");
            }
        }
    }

    // Check if the database exist to avoid re-copy the data
    private boolean checkDataBase() {
        SQLiteDatabase checkDB = null;
        try {
            String path = DB_PATH + DB_NAME;
            checkDB = SQLiteDatabase.openDatabase(path, null, SQLiteDatabase.OPEN_READONLY);
        } catch (SQLiteException e) {
            // database don't exist yet.
            e.printStackTrace();
        }
        if (checkDB != null) {
            checkDB.close();
        }
        return checkDB != null ? true : false;
    }

    // copy your assets db
    private void copyDataBase() throws IOException {
        //Open your local db as the input stream
        InputStream myInput = context.getAssets().open(DB_NAME);

        String outFileName = DB_PATH + DB_NAME;

        //Open the empty db as the output stream
        OutputStream myOutput = new FileOutputStream(outFileName);

        //transfer bytes from the inputfile to the outputfile
        byte[] buffer = new byte[1024];
        int length;
        while ((length = myInput.read(buffer)) > 0) {
            myOutput.write(buffer, 0, length);
        }

        myOutput.flush();
        myOutput.close();
        myInput.close();
    }

    //Open the database
    public boolean open() {
        try {
            String myPath = DB_PATH + DB_NAME;
            db = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READWRITE);
            return true;
        } catch (SQLException sqle) {
            db = null;
            return false;
        }
    }

    @Override
    public synchronized void close() {
        if (db != null)
            db.close();
        super.close();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }


    /*
    * Get all Department
    * */
    public List<Department> getDepartments() {
        List<Department> departments = new ArrayList<>();

        try {
            //get all rows from Department table
            String query = "SELECT * FROM " + TABLE_DEPARTMENT;
            SQLiteDatabase db = SQLiteDatabase.openDatabase(DB_PATH + DB_NAME, null, SQLiteDatabase.OPEN_READWRITE);
            Cursor cursor = db.rawQuery(query, null);

            while (cursor.moveToNext()) {
                int depID = Integer.parseInt(cursor.getString(0));
                String depName = cursor.getString(1);

                Department department = new Department(depID, depName);

                departments.add(department);
            }
        } catch(Exception e) {
            Log.d("DB", e.getMessage());
        }

        return departments;
    }

    /*
    * Get all phone numbers associated to specific employee
    * We pass the empID and retrieve List of strings back
    * */
    public List<String> getPhones(int empID) {
        List<String> phones = new ArrayList<>();

        try {
            SQLiteDatabase db = SQLiteDatabase.openDatabase(DB_PATH + DB_NAME, null, SQLiteDatabase.OPEN_READWRITE);
            //select rows which its third column "Employee_idEmployee" has same value as empID
            Cursor cursor = db.query(TABLE_PHONE, null, "Employee_idEmployee" + " = '" + empID + "'", null, null, null, null);

            while (cursor.moveToNext()) {
                String phone = cursor.getString(1);

                phones.add(phone);
            }
        } catch(Exception e) {
            Log.d("DB", e.getMessage());
        }

        return phones;
    }

    /*
    * Get all Employees
    * */
    public List<Employee> getEmployees() {
        List<Employee> employees = new ArrayList<>();

        try {
            //get all rows from Employee table
            String query = "SELECT * FROM " + TABLE_EMPLOYEE;
            SQLiteDatabase db = SQLiteDatabase.openDatabase(DB_PATH + DB_NAME, null, SQLiteDatabase.OPEN_READWRITE);
            Cursor cursor = db.rawQuery(query, null);

            while (cursor.moveToNext()) {
                int empID = Integer.parseInt(cursor.getString(0));
                String empName = cursor.getString(1);
                int empAge = Integer.parseInt(cursor.getString(2));
                String empMail = cursor.getString(3);

                //Get Phone Numbers associated to this employee
                List<String> empPhones = getPhones(empID);

                Employee employee = new Employee(empID, empName, empAge, empMail, empPhones);

                employees.add(employee);
            }
        } catch(Exception e) {
            Log.d("DB", e.getMessage());
        }

        return employees;
    }

}
