package com.example.therapyapp.GroundingPackage;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.therapyapp.R;

import java.util.ArrayList;

public class GroundingActivity extends AppCompatActivity {
    LinearLayout groundinglist;
    private DBGroundingHandler dbHandler;
    private ArrayList<GroundingModal> groundingModalArrayList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grounding);
        groundinglist = findViewById(R.id.groundinglistlayout);
        dbHandler = new DBGroundingHandler(GroundingActivity.this);

        loadViews();
    }
    private void loadViews(){
        groundingModalArrayList = dbHandler.readEntries();
        for(int i=groundingModalArrayList.size()-1; i>=0;i--) {
            addViews(i);
        }
    }

    private void addViews(int i) {
        View entry = getLayoutInflater().from(this).inflate(R.layout.grounding_log, null, false);
        TextView caption = entry.findViewById(R.id.groundinglogname);
        caption.setText(groundingModalArrayList.get(i).getName());//i
        groundinglist.addView(entry);
        entry.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent myIntent = new Intent(GroundingActivity.this, GroundingPlayer.class);
                myIntent.putExtra(getString(R.string.text),groundingModalArrayList.get(i).getText());
                myIntent.putExtra(getString(R.string.address),groundingModalArrayList.get(i).getAddress());
                GroundingActivity.this.startActivity(myIntent);
            }
        });
    }
}