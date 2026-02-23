package com.example.patientinformationapp;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.*;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    EditText etName, etAge, etPhone;
    RadioGroup rgGender;
    Spinner spIllness;
    Button btnDate, btnSubmit, btnCall, btnSMS, btnEmail;
    String selectedDate = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Patient App - Reg No: 23ITR087");
        setContentView(R.layout.activity_main);

        etName = findViewById(R.id.etName);
        etAge = findViewById(R.id.etAge);
        etPhone = findViewById(R.id.etPhone);
        rgGender = findViewById(R.id.rgGender);
        spIllness = findViewById(R.id.spIllness);
        btnDate = findViewById(R.id.btnDate);
        btnSubmit = findViewById(R.id.btnSubmit);
        btnCall = findViewById(R.id.btnCall);
        btnSMS = findViewById(R.id.btnSMS);
        btnEmail = findViewById(R.id.btnEmail);

        String[] illness = {"Fever", "Cold", "Headache"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_dropdown_item, illness);
        spIllness.setAdapter(adapter);

        btnDate.setOnClickListener(v -> {
            Calendar c = Calendar.getInstance();
            DatePickerDialog dp = new DatePickerDialog(this,
                    (view, year, month, day) ->
                            selectedDate = day + "/" + (month + 1) + "/" + year,
                    c.get(Calendar.YEAR),
                    c.get(Calendar.MONTH),
                    c.get(Calendar.DAY_OF_MONTH));
            dp.show();
        });

        btnSubmit.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, DisplayActivity.class);
            intent.putExtra("name", etName.getText().toString());
            intent.putExtra("age", etAge.getText().toString());
            intent.putExtra("phone", etPhone.getText().toString());
            intent.putExtra("date", selectedDate);
            startActivity(intent);
        });

        btnCall.setOnClickListener(v -> {
            new AlertDialog.Builder(this)
                    .setTitle("Confirmation")
                    .setMessage("Call Doctor?")
                    .setPositiveButton("Yes", (d, w) -> {
                        Intent callIntent = new Intent(Intent.ACTION_DIAL);
                        callIntent.setData(Uri.parse("tel:9876543210"));
                        startActivity(callIntent);
                    })
                    .setNegativeButton("No", null)
                    .show();
        });

        btnSMS.setOnClickListener(v -> {
            new AlertDialog.Builder(this)
                    .setTitle("Confirmation")
                    .setMessage("Send SMS?")
                    .setPositiveButton("Yes", (d, w) -> {
                        Intent smsIntent = new Intent(Intent.ACTION_VIEW);
                        smsIntent.setData(Uri.parse("sms:9876543210"));
                        startActivity(smsIntent);
                    })
                    .setNegativeButton("No", null)
                    .show();
        });

        btnEmail.setOnClickListener(v -> {
            new AlertDialog.Builder(this)
                    .setTitle("Confirmation")
                    .setMessage("Send Email?")
                    .setPositiveButton("Yes", (d, w) -> {
                        Intent emailIntent = new Intent(Intent.ACTION_SEND);
                        emailIntent.setType("message/rfc822");
                        emailIntent.putExtra(Intent.EXTRA_EMAIL,
                                new String[]{"doctor@gmail.com"});
                        startActivity(emailIntent);
                    })
                    .setNegativeButton("No", null)
                    .show();
        });
    }
}
