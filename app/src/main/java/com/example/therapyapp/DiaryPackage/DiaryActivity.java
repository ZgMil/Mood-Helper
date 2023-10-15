package com.example.therapyapp.DiaryPackage;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.therapyapp.CardPackage.CardsActivity;
import com.example.therapyapp.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

public class DiaryActivity extends AppCompatActivity {

    private DBDiaryHandler dbHandler;
    private ArrayList<DiaryModal> diaryModalArrayList;
    ActivityResultLauncher<Intent> someActivityResultLauncher;
    LinearLayout layout_list;
    ConstraintLayout placeholder;
    ImageButton addbtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diary);
        addbtn = findViewById(R.id.addbtn);
        dbHandler = new DBDiaryHandler(DiaryActivity.this);
        layout_list = findViewById(R.id.diarylogslayout);
        placeholder = findViewById(R.id.diaryplaceholder);
        loadViews();
        someActivityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == DiaryTextActivity.RESULT_OK) {
                            layout_list.removeAllViews();
                            loadViews();
                            Toast.makeText(DiaryActivity.this, R.string.saved,Toast.LENGTH_SHORT).show();
                        }
                    }
                });
        addbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(DiaryActivity.this, DiaryTextActivity.class);
                someActivityResultLauncher.launch(myIntent);
            }
        });
    }
    private void loadViews(){
        diaryModalArrayList = dbHandler.readEntries();
        if(diaryModalArrayList.isEmpty()){
            placeholder.setVisibility(View.VISIBLE);
        } else{
            placeholder.setVisibility(View.GONE);
            for(int i=diaryModalArrayList.size()-1; i>=0;i--) {
                addViews(i);
            }
        }
    }
    private void addViews(int i){
        View entry = getLayoutInflater().from(this).inflate(R.layout.diary_log,null,false);
        TextView caption = entry.findViewById(R.id.textnametxt);
        TextView datetxt = entry.findViewById(R.id.datetxtdiary);
        caption.setText(diaryModalArrayList.get(i).getText());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(getString(R.string.daymonthformat));
        String editdate = simpleDateFormat.format(diaryModalArrayList.get(i).getDate());
        datetxt.setText(editdate);
        layout_list.addView(entry);
        entry.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent myIntent = new Intent(DiaryActivity.this, DiaryTextActivity.class);
                myIntent.putExtra(getString(R.string.id),i);
                someActivityResultLauncher.launch(myIntent);
            }
        });
    }
}