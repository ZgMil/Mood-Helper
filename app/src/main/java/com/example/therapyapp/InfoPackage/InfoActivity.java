package com.example.therapyapp.InfoPackage;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.therapyapp.R;

import java.util.ArrayList;

public class InfoActivity extends AppCompatActivity {

    LinearLayout infolist;
    private DBInfoHandler dbHandler;
    private ArrayList<InfoModal> infoModalArrayList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        infolist = findViewById(R.id.infolistlayout);
        dbHandler = new DBInfoHandler(InfoActivity.this);
        loadViews();
    }
    private void loadViews(){
        infoModalArrayList = dbHandler.readEntries();
        for(int i=infoModalArrayList.size()-1; i>=0;i--) {
            addViews(i);
        }
    }
    private void addViews(int i) {
        View entry = getLayoutInflater().from(this).inflate(R.layout.info_log, null, false);
        TextView caption = entry.findViewById(R.id.infologname);
        caption.setText(infoModalArrayList.get(i).getName());//i
        infolist.addView(entry);
        entry.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent myIntent = new Intent(InfoActivity.this, ReadingActivity.class);
                myIntent.putExtra(getString(R.string.text), infoModalArrayList.get(i).getText());
                InfoActivity.this.startActivity(myIntent);
            }
        });
    }
}