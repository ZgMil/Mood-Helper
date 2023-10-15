package com.example.therapyapp;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;

import android.os.Handler;
import android.util.AttributeSet;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;

import com.example.therapyapp.CardPackage.CardsActivity;
import com.example.therapyapp.DiaryPackage.DiaryActivity;
import com.example.therapyapp.GroundingPackage.GroundingActivity;
import com.example.therapyapp.MonitoringPackage.DataActivity;

public class ViewAssist {
    public static int colorTranslate(int i, Activity activity){
        int[] states= {R.color.lightRed, R.color.purplelight, R.color.bluelight, R.color.greenlight, R.color.yellow};
        return ContextCompat.getColor(activity,states[i]);
    }
     public static String stateTranslate(int i,Activity activity){
        String[] states= activity.getResources().getStringArray(R.array.moodNames);
        return states[i];
    }
    public static String stateTranslateSmall(int i,Activity activity){
        String[] states= activity.getResources().getStringArray(R.array.moodNamesSmall);
        return states[i];
    }
    static void intentMake(Activity activitycurrent,Class activitynew){
        Intent myIntent = new Intent(activitycurrent, activitynew);
        activitycurrent.startActivity(myIntent);
    }
    static void moodDialog(int mood, Activity activity){
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setMessage(activity.getResources().getStringArray(R.array.monitoringResponces)[mood]);
        builder.setTitle(R.string.saved);
        builder.setCancelable(false);
        builder.setPositiveButton(R.string.yes, (DialogInterface.OnClickListener) (dialog, which) -> {
            switch(mood){
                case 0:
                    ViewAssist.intentMake(activity, GroundingActivity.class);
                    break;
                case 1:
                    ViewAssist.intentMake(activity, DiaryActivity.class);
                    break;
                case 2:
                    ViewAssist.intentMake(activity, CardsActivity.class);
                    break;
                case 3:
                    ViewAssist.intentMake(activity, DataActivity.class);
                    break;
                case 4:
                    ViewAssist.intentMake(activity, DiaryActivity.class);
                    break;
            }
        });
        builder.setNegativeButton(R.string.no, (DialogInterface.OnClickListener) (dialog, which) -> {
            dialog.cancel();
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
    @SuppressLint("AppCompatCustomView")
    public static class Typewriter extends TextView {
        private CharSequence mText;
        private int mIndex;
        private long mDelay = 5000;
        public Typewriter(Context context) {
            super(context);
        }
        public Typewriter(Context context, AttributeSet attrs) {
            super(context, attrs);
        }
        private Handler mHandler = new Handler();
        private Runnable characterAdder = new Runnable() {
            @Override
            public void run() {
                setText(mText.subSequence(0, mIndex++));
                if(mIndex <= mText.length()) {
                    mHandler.postDelayed(characterAdder, mDelay);
                }
            }
        };
        public void animateText(CharSequence text) {
            mText = text;
            mIndex = 0;
            setText("");
            mHandler.removeCallbacks(characterAdder);
            mHandler.postDelayed(characterAdder, mDelay);
        }
        public void setCharacterDelay(long millis) {
            mDelay = millis;
        }

    }

}
