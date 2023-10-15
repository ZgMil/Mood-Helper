package com.example.therapyapp.DiaryPackage;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.therapyapp.MonitoringPackage.DataActivity;
import com.example.therapyapp.R;

import java.util.ArrayList;

public class DiaryTextActivity extends AppCompatActivity {
    private DBDiaryHandler dbHandler;
    private ArrayList<DiaryModal> diaryModalArrayList;
    EditText diary;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diary_text);
        Bundle extras = getIntent().getExtras();
        Button deletebtn=findViewById(R.id.deletebtndiary);
        Button submitbtn=findViewById(R.id.submitbtndiary);
        if(extras!=null) deletebtn.setVisibility(View.VISIBLE); else deletebtn.setVisibility(View.GONE);
        diary=findViewById(R.id.diaryentry);
        dbHandler = new DBDiaryHandler(DiaryTextActivity.this);
        diaryModalArrayList = dbHandler.readEntries();
        if(extras!=null) {
            int idofentry  = extras.getInt("id");
                diary.setText(diaryModalArrayList.get(idofentry).getText());
        }
        submitbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(extras!=null){
                    int idofentry  = extras.getInt("id");
                    dbHandler.updatediary(diary.getText().toString(),diaryModalArrayList.get(idofentry).getId());
                }else{
                    if(!diary.getText().toString().equals("")){
                        dbHandler.addNewEntry(diary.getText().toString());
                    }
                }
                setResult(RESULT_OK);
                finish();
            }
        });
        deletebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(DiaryTextActivity.this);
                builder.setMessage("Удаленные данные будут потеряны");
                builder.setTitle("Вы уверены?");
                builder.setCancelable(false);
                builder.setPositiveButton("Удалить", (DialogInterface.OnClickListener) (dialog, which) -> {
                    int idofentry  = extras.getInt("id");
                    dbHandler.deleteDiary(diaryModalArrayList.get(idofentry).getId());
                    setResult(RESULT_OK);
                    finish();
                });
                builder.setNegativeButton("Нет", (DialogInterface.OnClickListener) (dialog, which) -> {
                    dialog.cancel();
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });
    }
}