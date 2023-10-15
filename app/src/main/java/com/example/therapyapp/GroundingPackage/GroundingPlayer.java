package com.example.therapyapp.GroundingPackage;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.therapyapp.CardPackage.CardsActivity;
import com.example.therapyapp.DiaryPackage.DiaryActivity;
import com.example.therapyapp.DiaryPackage.DiaryTextActivity;
import com.example.therapyapp.R;
import com.example.therapyapp.ViewAssist;

public class GroundingPlayer extends AppCompatActivity {

    ImageButton play;
    MediaPlayer player;
    SeekBar progressbar;
    Handler handler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grounding_player);
        play = findViewById(R.id.playbtn);
        play.setImageResource(R.drawable.play_foreground);
        progressbar = findViewById(R.id.timelinebar);
        Bundle extras = getIntent().getExtras();
        ViewAssist.Typewriter writer = new ViewAssist.Typewriter(this);
        writer = findViewById(R.id.audiotext);
        writer.setCharacterDelay(50);
        writer.animateText(extras.getString("text"));
        player = MediaPlayer.create(this,extras.getInt("address"));
        progressbar.setMax(player.getDuration());
        handler = new Handler();
        progressbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }
            @Override
            public void onProgressChanged(SeekBar seekbar, int progress,boolean fromUser) {
                if(fromUser)
                player.seekTo(progress);
            }
        });
        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(player.isPlaying()){

                    play.setImageResource(R.drawable.play_foreground);
                    player.pause();

                }else {
                    player.start();
                    play.setImageResource(R.drawable.pause_foreground);
                    updateSeekBar();
                }
            }
        });
    }
    @Override
    protected void onPause() {
        super.onPause();
        player.pause();
        play.setImageDrawable(getDrawable(R.drawable.play_foreground));
    }
    private void updateSeekBar() {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                updateSeekBar();
            }
        };
        progressbar.setProgress(player.getCurrentPosition());
        handler.postDelayed(runnable, 200);
    }
}