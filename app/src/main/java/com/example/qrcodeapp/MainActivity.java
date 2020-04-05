package com.example.qrcodeapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Vibrator;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

public class MainActivity extends AppCompatActivity {
    Button btnGenerate,btnScan;
    ImageView ivPic;
    EditText etInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnGenerate=findViewById(R.id.btnGenerate);
        btnScan=findViewById(R.id.btnScan);
        ivPic=findViewById(R.id.ivPic);
        etInput=findViewById(R.id.etInput);

        btnGenerate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get instance of Vibrator from current Context
                Vibrator vibrator = (Vibrator) getSystemService(getBaseContext().VIBRATOR_SERVICE);
                if (etInput.getText().toString().trim().isEmpty()){
                    if (vibrator.hasVibrator()) {
                        Log.v("Can Vibrate", "YES");
                    } else {
                        Log.v("Can Vibrate", "NO");
                    }
                    vibrator.vibrate(50);
                    Toast.makeText(MainActivity.this, "Please Enter All Fields", Toast.LENGTH_SHORT).show();
                }
                else {
                    QRCodeWriter qrCodeWriter = new QRCodeWriter();
                    try {
                        BitMatrix bitMatrix = qrCodeWriter.encode(etInput.getText().toString().trim(), BarcodeFormat.QR_CODE, 300, 300);
                        Bitmap bitmap = Bitmap.createBitmap(300, 300, Bitmap.Config.RGB_565);
                        for (int x = 0; x < 300; x++) {
                            for (int y = 0; y < 300; y++) {
                                bitmap.setPixel(x, y, bitMatrix.get(x, y) ? Color.BLACK : Color.WHITE);
                            }
                        }
                        ivPic.setImageBitmap(bitmap);
                        vibrator.vibrate(50);
                        Toast.makeText(MainActivity.this, "Qr Code Generated", Toast.LENGTH_SHORT).show();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        btnScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,com.example.qrcodeapp.scan.class);
                startActivity(intent);
            }
        });

    }
}
