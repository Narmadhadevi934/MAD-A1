package com.example.dailyexpensive;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.*;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    EditText amount, desc;
    Spinner category;
    RadioGroup paymentGroup;
    Button add, call, sms, email;
    ListView listView;

    ArrayList<String> expenseList;
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Link XML Views
        amount = findViewById(R.id.amount);
        desc = findViewById(R.id.desc);
        category = findViewById(R.id.category);
        paymentGroup = findViewById(R.id.paymentGroup);
        add = findViewById(R.id.add);
        call = findViewById(R.id.call);
        sms = findViewById(R.id.sms);
        email = findViewById(R.id.email);
        listView = findViewById(R.id.listView);

        // Spinner Data
        String[] categories = {"Office", "Food", "Travel", "Shopping"};
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_dropdown_item,
                categories
        );
        category.setAdapter(spinnerAdapter);

        // ListView Setup
        expenseList = new ArrayList<>();
        adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                expenseList
        );
        listView.setAdapter(adapter);

        // Add Button
        add.setOnClickListener(v -> addExpense());

        // Call Button
        call.setOnClickListener(v -> showCallDialog());

        // SMS Button
        sms.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse("sms:1234567890"));
            intent.putExtra("sms_body", "Here is my expense report.");
            startActivity(intent);
        });

        // Email Button
        email.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("message/rfc822");
            intent.putExtra(Intent.EXTRA_EMAIL,
                    new String[]{"accountant@mail.com"});
            intent.putExtra(Intent.EXTRA_SUBJECT,
                    "Daily Expense Report");
            intent.putExtra(Intent.EXTRA_TEXT,
                    expenseList.toString());

            startActivity(Intent.createChooser(intent, "Send Email"));
        });
    }

    private void addExpense() {

        String amt = amount.getText().toString().trim();
        String description = desc.getText().toString().trim();

        if (amt.isEmpty() || description.isEmpty()) {
            Toast.makeText(this,
                    "Please enter all details",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        String selectedCategory =
                category.getSelectedItem().toString();

        int selectedId =
                paymentGroup.getCheckedRadioButtonId();

        RadioButton selectedPayment =
                findViewById(selectedId);

        String paymentType =
                selectedPayment.getText().toString();

        String item = description + " - ₹" + amt +
                " | " + selectedCategory +
                " | " + paymentType;

        expenseList.add(item);
        adapter.notifyDataSetChanged();

        amount.setText("");
        desc.setText("");
    }

    private void showCallDialog() {

        new AlertDialog.Builder(this)
                .setTitle("Confirm Call")
                .setMessage("Do you want to call the accountant?")
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
