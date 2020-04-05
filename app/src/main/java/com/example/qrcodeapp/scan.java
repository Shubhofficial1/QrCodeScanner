package com.example.qrcodeapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class scan extends AppCompatActivity {
    TextView tvShow;
    TextView tvScannedText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan);

        tvShow=findViewById(R.id.tvShow);
        tvScannedText=findViewById(R.id.tvScannedText);
        IntentIntegrator integrator = new IntentIntegrator(scan.this);
        integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES);
        integrator.setPrompt("Scan QR Code");
        integrator.setCameraId(0);
        integrator.setBeepEnabled(true);
        integrator.setBarcodeImageEnabled(false);
        integrator.initiateScan();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            if (result.getContents() == null) {
                Toast.makeText(scan.this, "Scanning Stopped", Toast.LENGTH_SHORT).show();
            }
            else {
                tvScannedText.setText(result.getContents());
                Object clipboardService = getSystemService(CLIPBOARD_SERVICE);
                ClipboardManager clipboardManager = (ClipboardManager)clipboardService;
                String srcText = result.getContents().toString();
                // Create a new ClipData.
                ClipData clipData = ClipData.newPlainText("Source Text", srcText);
                // Set it as primary clip data to copy text to system clipboard.
                clipboardManager.setPrimaryClip(clipData);
                // Popup a Toast.
                Toast.makeText(this, "Copied to clipboard", Toast.LENGTH_SHORT).show();

            }


        }


    }


}

