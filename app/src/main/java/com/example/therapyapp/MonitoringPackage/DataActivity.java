package com.example.therapyapp.MonitoringPackage;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.therapyapp.MainActivity;
import com.example.therapyapp.R;
import com.example.therapyapp.ViewAssist;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.helper.DateAsXAxisLabelFormatter;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class DataActivity extends AppCompatActivity {

    DBHandler dbHandler;
    ArrayList<EntryModal> entryModalArrayList;
    LinearLayout layout_list;
    int[] days={7,30};
    GraphView graphView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data);

        Spinner longevity = findViewById(R.id.spinnerlongevity);
        Spinner parameters = findViewById(R.id.spinnerparam);
        dbHandler = new DBHandler(DataActivity.this);
        graphView = findViewById(R.id.idGraphView);
        makeParamGraph(0,0);
        layout_list = findViewById(R.id.loglist);
        loadViews(0,0);
        parameters.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {

                makeParamGraph(position,longevity.getSelectedItemPosition());
                reloadViews(position, longevity.getSelectedItemPosition());
            }
            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
            }
        });
        longevity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {

                makeParamGraph(parameters.getSelectedItemPosition(),position);
                reloadViews(parameters.getSelectedItemPosition(), position);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
            }
        });
    }
    private void makeParamGraph(int param,int longevity){
         graphView.removeAllSeries();
        entryModalArrayList = dbHandler.readEntries();
        DataPoint[] values = new DataPoint[entryModalArrayList.size()];
        int paramres=0;
        for(int i=0; i<entryModalArrayList.size();i++) {
                switch(param){
                case 0:
                    paramres=entryModalArrayList.get(i).getMood();
                    graphView.setBackgroundColor(ViewAssist.colorTranslate(entryModalArrayList.get(entryModalArrayList.size()-1).getMood(),this));
                    break;
                case 1:
                    paramres=entryModalArrayList.get(i).getconcen();
                    graphView.setBackgroundColor(ViewAssist.colorTranslate(entryModalArrayList.get(entryModalArrayList.size()-1).getconcen(),this));
                    break;
                case 2:
                    paramres=entryModalArrayList.get(i).getDeterm();
                    graphView.setBackgroundColor(ViewAssist.colorTranslate(entryModalArrayList.get(entryModalArrayList.size()-1).getDeterm(),this));
                    break;
                case 3:
                    paramres=entryModalArrayList.get(i).getBalanc();
                    graphView.setBackgroundColor(ViewAssist.colorTranslate(entryModalArrayList.get(entryModalArrayList.size()-1).getBalanc(),this));
                    break;
                case 4:
                    paramres=entryModalArrayList.get(i).getSelf();
                    graphView.setBackgroundColor(ViewAssist.colorTranslate(entryModalArrayList.get(entryModalArrayList.size()-1).getSelf(),this));
                    break;
            }
                DataPoint v = new DataPoint(entryModalArrayList.get(i).getDate(),paramres);
                values[i] = v;
        }
        drawGraph(values,longevity);
    }

    private void reloadViews(int param,int longevity){

        layout_list.removeAllViews();
        loadViews(param,longevity);
    }
    private void loadViews(int param,int longevity){

        Date a = new Date(System.currentTimeMillis() - (86400000L *days[longevity]));
        entryModalArrayList = dbHandler.readEntries();
        for(int i=entryModalArrayList.size()-1; i>=0;i--) {
            if(entryModalArrayList.get(i).getDate().after(a)){
                addViews(i,param);
            }
        }
        ConstraintLayout placeholder = findViewById(R.id.placeholderlayoutdata);
        if(layout_list.getChildCount()==0) placeholder.setVisibility(View.VISIBLE);
        else placeholder.setVisibility(View.GONE);
    }
    private void drawGraph(DataPoint[] values, int longevity){
        LineGraphSeries<DataPoint> series = new LineGraphSeries<DataPoint>(values);
        series.setThickness(20);
        series.setColor(getColor(R.color.white));
        graphView.getGridLabelRenderer().setVerticalLabelsVisible(false);
        graphView.getGridLabelRenderer().setTextSize(24);
        graphView.getGridLabelRenderer().setLabelFormatter(new DateAsXAxisLabelFormatter(DataActivity.this,new SimpleDateFormat(getString(R.string.dayformat))));

        graphView.getGridLabelRenderer().setHumanRounding(false);
        graphView.getViewport().setXAxisBoundsManual(true);
        graphView.getViewport().setYAxisBoundsManual(true);
        Date now = new Date();
        graphView.getViewport().setMaxX(now.getTime());
        if(longevity==1) graphView.getGridLabelRenderer().setHumanRounding(true);
        Date a = new Date(System.currentTimeMillis() - (86400000L *days[longevity]));
        graphView.getViewport().setMinX(a.getTime());
        graphView.getViewport().setMaxY(6);
        graphView.getViewport().setMinY(-1);
        graphView.addSeries(series);
    }
    private void addViews(int i,int param){
            View entry = getLayoutInflater().from(this).inflate(R.layout.statistics_log,layout_list,false);
            TextView caption = entry.findViewById(R.id.moodtxt);
            TextView datetxt = entry.findViewById(R.id.datetxt);
            ImageButton deletebtn = entry.findViewById(R.id.deletebtn);
            View back = entry.findViewById(R.id.bg);
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(getString(R.string.daymonthformat));
            String editdate = simpleDateFormat.format(entryModalArrayList.get(i).getDate());
            datetxt.setText(editdate);
            switch(param){
                case 0:
                    caption.setText(ViewAssist.stateTranslate(entryModalArrayList.get(i).getMood(),this));
                    back.setBackgroundColor(ViewAssist.colorTranslate(entryModalArrayList.get(i).getMood(),this));
                    break;
                case 1:
                    caption.setText(ViewAssist.stateTranslate(entryModalArrayList.get(i).getconcen(),this));
                    back.setBackgroundColor(ViewAssist.colorTranslate(entryModalArrayList.get(i).getconcen(),this));
                    break;
                case 2:
                    caption.setText(ViewAssist.stateTranslate(entryModalArrayList.get(i).getDeterm(),this));
                    back.setBackgroundColor(ViewAssist.colorTranslate(entryModalArrayList.get(i).getDeterm(),this));
                    break;
                case 3:
                    caption.setText(ViewAssist.stateTranslate(entryModalArrayList.get(i).getBalanc(),this));
                    back.setBackgroundColor(ViewAssist.colorTranslate(entryModalArrayList.get(i).getBalanc(),this));
                    break;
                case 4:
                    caption.setText(ViewAssist.stateTranslate(entryModalArrayList.get(i).getSelf(),this));
                    back.setBackgroundColor(ViewAssist.colorTranslate(entryModalArrayList.get(i).getSelf(),this));
                    break;
            }
            deletebtn.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(DataActivity.this);
                    builder.setMessage(R.string.deletion_is_permanent);
                    builder.setTitle(R.string.are_you_sure);
                    builder.setCancelable(false);
                    builder.setPositiveButton(R.string.delete, (DialogInterface.OnClickListener) (dialog, which) -> {
                        dbHandler.deleteEntry(entryModalArrayList.get(i).getDate());
                        finish();
                        startActivity(getIntent());
                        Toast.makeText(DataActivity.this, R.string.deleted_successfully,Toast.LENGTH_SHORT).show();
                    });
                    builder.setNegativeButton(R.string.no, (DialogInterface.OnClickListener) (dialog, which) -> {
                        dialog.cancel();
                    });
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                }
            });
            layout_list.addView(entry);
    }
}