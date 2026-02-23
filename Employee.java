package com.example.employee;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.*;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    EditText name, empId;
    Spinner dept;
    Button save, accountant, email;
    GridView grid;

    ArrayList<String> employees;
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Connect XML
        name = findViewById(R.id.name);
        empId = findViewById(R.id.empId);
        dept = findViewById(R.id.dept);
        save = findViewById(R.id.save);
        accountant = findViewById(R.id.accountant);
        email = findViewById(R.id.email);
        grid = findViewById(R.id.grid);

        // Spinner Data
        String[] departments = {"Sales", "IT", "HR", "Marketing"};
        ArrayAdapter<String> spAdapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_dropdown_item,
                departments
        );
        dept.setAdapter(spAdapter);

        // Grid Data
        employees = new ArrayList<>();

        adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                employees
        );

        grid.setAdapter(adapter);

        // Save Button
        save.setOnClickListener(v -> saveEmployee());

        // Accountant Call Button
        accountant.setOnClickListener(v ->
                showCallDialog("Accountant"));

        // Email Button
        email.setOnClickListener(v -> sendEmail());

        // Grid Item Click
        grid.setOnItemClickListener((parent, view, position, id) ->
                showCallDialog("Employee"));
    }

    private void saveEmployee() {

        String n = name.getText().toString().trim();
        String id = empId.getText().toString().trim();
        String d = dept.getSelectedItem().toString();

        if (n.isEmpty() || id.isEmpty()) {
            Toast.makeText(this,
                    "Enter all details",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        String employeeData =
                "Name: " + n +
                        "\nID: " + id +
                        "\nDept: " + d;

        employees.add(employeeData);
        adapter.notifyDataSetChanged();

        name.setText("");
        empId.setText("");
    }

    private void sendEmail() {

        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("message/rfc822");
        intent.putExtra(Intent.EXTRA_EMAIL,
                new String[]{"hr@company.com"});
        intent.putExtra(Intent.EXTRA_SUBJECT,
                "Employee Details");
        intent.putExtra(Intent.EXTRA_TEXT,
                employees.toString());

        startActivity(Intent.createChooser(intent,
                "Send Email"));
    }

    private void showCallDialog(String person) {

        new AlertDialog.Builder(this)
                .setTitle("Confirm Call")
                .setMessage("Do you want to call " + person + "?")
                .setPositiveButton("Call",
                        (dialog, which) -> {

                            Intent intent =
                                    new Intent(Intent.ACTION_DIAL);
                            intent.setData(
                                    Uri.parse("tel:1234567890"));
                            startActivity(intent);
                        })
                .setNegativeButton("Cancel", null)
                .show();
    }
}
