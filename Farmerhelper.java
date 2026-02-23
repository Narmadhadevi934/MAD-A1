package com.example.farmerhelp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    Spinner crop, soil, season;
    TextView tips;
    Button call, sms, email1, email2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        crop = findViewById(R.id.crop);
        soil = findViewById(R.id.soil);
        season = findViewById(R.id.season);
        tips = findViewById(R.id.tips);

        call = findViewById(R.id.call);
        sms = findViewById(R.id.sms);
        email1 = findViewById(R.id.email1);
        email2 = findViewById(R.id.email2);

        // Spinner Data
        String[] crops = {"Rice", "Wheat", "Maize"};
        String[] soils = {"Loamy", "Clay", "Sandy"};
        String[] seasons = {"Monsoon", "Winter", "Summer"};

        crop.setAdapter(new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_dropdown_item, crops));

        soil.setAdapter(new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_dropdown_item, soils));

        season.setAdapter(new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_dropdown_item, seasons));

        // Update tips when any spinner changes
        android.widget.AdapterView.OnItemSelectedListener listener =
                new android.widget.AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(android.widget.AdapterView<?> parent,
                                               android.view.View view,
                                               int position,
                                               long id) {
                        updateTips();
                    }

                    @Override
                    public void onNothingSelected(android.widget.AdapterView<?> parent) {}
                };

        crop.setOnItemSelectedListener(listener);
        soil.setOnItemSelectedListener(listener);
        season.setOnItemSelectedListener(listener);

        // Call Button
        call.setOnClickListener(v -> showCallDialog());

        // SMS Button
        sms.setOnClickListener(v -> {

            String message = "Need help for " +
                    crop.getSelectedItem().toString() + " crop.";

            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse("sms:1234567890"));
            intent.putExtra("sms_body", message);
            startActivity(intent);
        });

        // Email Buttons
        email1.setOnClickListener(v -> sendEmail());
        email2.setOnClickListener(v -> sendEmail());
    }

    private void updateTips() {

        String selectedCrop = crop.getSelectedItem().toString();
        String selectedSoil = soil.getSelectedItem().toString();
        String selectedSeason = season.getSelectedItem().toString();

        String message =
                "Crop: " + selectedCrop +
                        "\nSoil: " + selectedSoil +
                        "\nSeason: " + selectedSeason +
                        "\n\nRecommended Tips:\n";

        if (selectedCrop.equals("Rice")) {
            message += "• Maintain water level.\n• Use nitrogen fertilizer.\n";
        } else if (selectedCrop.equals("Wheat")) {
            message += "• Irrigate at proper stages.\n• Use disease-resistant seeds.\n";
        } else {
            message += "• Maintain proper spacing.\n• Monitor pests.\n";
        }

        if (selectedSoil.equals("Loamy")) {
            message += "• Ideal soil, maintain moisture.\n";
        } else if (selectedSoil.equals("Clay")) {
            message += "• Improve drainage.\n";
        } else {
            message += "• Add organic manure for fertility.\n";
        }

        if (selectedSeason.equals("Monsoon")) {
            message += "• Protect from excess rain.\n";
        } else if (selectedSeason.equals("Winter")) {
            message += "• Monitor frost damage.\n";
        } else {
            message += "• Ensure regular irrigation.\n";
        }

        tips.setText(message);
    }

    private void showCallDialog() {

        new AlertDialog.Builder(this)
                .setTitle("Confirm Call")
                .setMessage("Do you want to call the agricultural officer?")
                .setPositiveButton("Call", (dialog, which) -> {

                    Intent intent = new Intent(Intent.ACTION_DIAL);
                    intent.setData(Uri.parse("tel:1234567890"));
                    startActivity(intent);

                })
                .setNegativeButton("Cancel", null)
                .show();
    }

    private void sendEmail() {

        String emailBody =
                "Crop: " + crop.getSelectedItem().toString() +
                        "\nSoil: " + soil.getSelectedItem().toString() +
                        "\nSeason: " + season.getSelectedItem().toString() +
                        "\n\nI need farming guidance.";

        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("message/rfc822");
        intent.putExtra(Intent.EXTRA_EMAIL,
                new String[]{"agri_officer@mail.com"});
        intent.putExtra(Intent.EXTRA_SUBJECT,
                "Farming Help Request");
        intent.putExtra(Intent.EXTRA_TEXT,
                emailBody);

        startActivity(Intent.createChooser(intent, "Send Email"));
    }
}
