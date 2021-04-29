package com.upce.ar;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;
import java.io.InputStream;

public class AboutApp extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.about_app);
        Button btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(), HomeScreen.class)));
        ImageView qrImage = findViewById(R.id.imageViewQrCode);
        InputStream is = null;
        try {
            is = getApplicationContext().getAssets().open("qrcodeuniverzal.png");
        } catch (IOException e) {
            e.printStackTrace();
        }
        Bitmap bitmap = BitmapFactory.decodeStream(is);
        qrImage.setImageBitmap(bitmap);
    }
}

