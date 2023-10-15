package com.example.therapyapp.InfoPackage;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.example.therapyapp.R;

public class ReadingActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reading);
        TextView article = findViewById(R.id.infotext);
        Bundle extras = getIntent().getExtras();
        article.setText(extras.getString(getString(R.string.text)));
    }
}