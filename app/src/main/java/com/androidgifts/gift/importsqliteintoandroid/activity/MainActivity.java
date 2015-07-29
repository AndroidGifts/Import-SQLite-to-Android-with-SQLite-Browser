package com.androidgifts.gift.importsqliteintoandroid.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.androidgifts.gift.importsqliteintoandroid.R;
import com.androidgifts.gift.importsqliteintoandroid.data.Department;
import com.androidgifts.gift.importsqliteintoandroid.data.Employee;
import com.androidgifts.gift.importsqliteintoandroid.helper.EmployeeDBHelper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    private Toolbar toolBar;

    private List<Employee> employees;
    private List<Department> departments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolBar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolBar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        employees = getEmployees();
        departments = getDepartments();

        for (int i = 0; i < employees.size(); i++) {
            Toast.makeText(getApplicationContext(), "Employee: " + employees.get(i).getEmpName()
                     + ", " + employees.get(i).getEmpMail() + ", " + employees.get(i).getEmpPhone(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        return super.onOptionsItemSelected(item);
    }

    private List<Employee> getEmployees() {
        List<Employee> employees = new ArrayList<>();

        EmployeeDBHelper db = new EmployeeDBHelper(getApplicationContext());

        try {
            db.create();
        } catch (IOException ioe) {
            throw new Error("Unable to create database");
        }

        if (db.open()) {
            employees = db.getEmployees();
        }

        return employees;
    }

    private List<Department> getDepartments() {
        List<Department> departments = new ArrayList<>();

        EmployeeDBHelper db = new EmployeeDBHelper(getApplicationContext());

        try {
            db.create();
        } catch (IOException ioe) {
            throw new Error("Unable to create database");
        }

        if (db.open()) {
            departments = db.getDepartments();
        }

        return departments;
    }
}
