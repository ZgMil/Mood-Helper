package com.example.therapyapp;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import com.example.therapyapp.CardPackage.CardsActivity;
import com.example.therapyapp.DiaryPackage.DiaryActivity;
import com.example.therapyapp.GroundingPackage.GroundingActivity;
import com.example.therapyapp.InfoPackage.InfoActivity;
import com.example.therapyapp.MonitoringPackage.DBHandler;
import com.example.therapyapp.MonitoringPackage.DataActivity;
import com.example.therapyapp.MonitoringPackage.EntryModal;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button cardsbtn=findViewById(R.id.statementsbtn);
        Button button = findViewById(R.id.button);
        Button statsbtn = findViewById(R.id.statsbtn);
        Button grbtn = findViewById(R.id.groundingbtn);
        Button resbtn = findViewById(R.id.resourcesbtn);
        Button submitbtn = findViewById(R.id.submitbtn);
        Button diarybtn = findViewById(R.id.diarybtn);
        SeekBar moodbar = findViewById(R.id.moodbar);
        View seekbarscontainer = findViewById(R.id.seekbarview);
        seekbarscontainer.setVisibility(seekbarscontainer.GONE);
        DBHandler dbHandler = new DBHandler(MainActivity.this);
        moodbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress,boolean fromUser) {
                // TODO Auto-generated method stub
                View materialbg = findViewById(R.id.materialbg);
                View backmood = findViewById(R.id.backmood);
                ImageView face = findViewById(R.id.faceimg);
                int color=ViewAssist.colorTranslate(progress, MainActivity.this);
                int faces[]={R.drawable.component_2_2_,R.drawable.component_3_1_,R.drawable.component_3,R.drawable.component_1,R.drawable.component_1_1_};
                face.setImageDrawable(getDrawable(faces[progress]));
                backmood.setBackgroundColor(color);
                button.setTextColor(color);

                button.setText(getString(R.string.ifeel)+ ViewAssist.stateTranslateSmall(progress, MainActivity.this));
                statsbtn.setBackgroundColor(color);
                diarybtn.setBackgroundColor(color);
                cardsbtn.setBackgroundColor(color);
                grbtn.setBackgroundColor(color);
                resbtn.setBackgroundColor(color);
                materialbg.setBackgroundColor(color);
                submitbtn.setBackgroundColor(color);
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (seekbarscontainer.getVisibility() == View.VISIBLE) {
                    seekbarscontainer.setVisibility(View.GONE);
                } else {
                    seekbarscontainer.setVisibility(View.VISIBLE);
                }
            }
        });
        submitbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SeekBar concenbar = findViewById(R.id.concenbar);
                SeekBar determbar = findViewById(R.id.determbar);
                SeekBar balancbar = findViewById(R.id.balancbar);
                SeekBar selfbar = findViewById(R.id.selfbar);
                ArrayList<EntryModal> entryModalArrayList = dbHandler.readEntries();
                Date rn = new Date();
                Date rnnow = new Date(rn.getTime()- 180*60*1000L);
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat(getString(R.string.dateformat));
                if(simpleDateFormat.format(rnnow).equals(simpleDateFormat.format(entryModalArrayList.get(entryModalArrayList.size() - 1).getDate()))){
                    dbHandler.updateEntry(moodbar.getProgress(),concenbar.getProgress(),determbar.getProgress(),balancbar.getProgress(),selfbar.getProgress(), rnnow);
                } else{
                    dbHandler.addNewEntry(moodbar.getProgress(),concenbar.getProgress(),determbar.getProgress(),balancbar.getProgress(),selfbar.getProgress());
                }
                seekbarscontainer.setVisibility(View.GONE);
                concenbar.setProgress(2);
                determbar.setProgress(2);
                balancbar.setProgress(2);
                selfbar.setProgress(2);
                Toast.makeText(MainActivity.this, R.string.done,Toast.LENGTH_SHORT).show();

                ViewAssist.moodDialog(moodbar.getProgress(),MainActivity.this);
            }
        });
        statsbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ViewAssist.intentMake(MainActivity.this,DataActivity.class);
            }
        });
        diarybtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ViewAssist.intentMake(MainActivity.this, DiaryActivity.class);
            }
        });
        cardsbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ViewAssist.intentMake(MainActivity.this, CardsActivity.class);
            }
        });
        grbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ViewAssist.intentMake(MainActivity.this, GroundingActivity.class);
            }
        });
        resbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ViewAssist.intentMake(MainActivity.this, InfoActivity.class);
            }
        });
    }

}