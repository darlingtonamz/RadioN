package com.amanzed.radio;

import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.amanzed.radio.db.DBHelper;
import com.amanzed.radio.list.Radio;
import com.amanzed.radio.recycle.RecyclerAdapter;
import com.amanzed.radio.util.LoadJson;
import com.github.silvestrpredko.dotprogressbar.DotProgressBar;
import com.hugomatilla.audioplayerview.AudioPlayerView;
import com.pkmmte.view.CircularImageView;

import org.firezenk.audiowaves.Visualizer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class MainActivity extends AppCompatActivity {
    private Handler timeHandler;
    AudioPlayerView audioPlayerView;
    DotProgressBar loadingAnimation;
    Visualizer visualizer;
    DBHelper mydb;
    ArrayList<Radio> radioList, custom;
    private RecyclerAdapter adapter;
    RecyclerView radioLV;
    Radio selected_radio;
    FloatingActionButton floatPlay;
    MediaPlayer mediaPlayer;
    String url = "http://icestream.coolwazobiainfo.com:8000/coolfm-lagos";
    LinearLayout showing;
    public CircularImageView playing_thumb;
    public TextView playing_name;
    private Runnable loadTimeRunnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mydb = new DBHelper(this);
        loadDB();
        custom = radioList;

        Collections.sort(radioList, Radio.RadioNameComparator);
        int radios_num = mydb.numberOfRadioRows();
        adapter = new RecyclerAdapter(this, radioList);
        radioLV = (RecyclerView)findViewById(R.id.radio_recView);
        showing = (LinearLayout)findViewById(R.id.showing_dashboard);
        playing_thumb = (CircularImageView)findViewById(R.id.playing_thumb);
        playing_name = (TextView)findViewById(R.id.playing_name);

        mediaPlayer = new MediaPlayer();



        visualizer = (Visualizer) findViewById(R.id.visualizer);

        loadingAnimation = (DotProgressBar)findViewById(R.id.dot_progress_bar);
        audioPlayerView = (AudioPlayerView) findViewById(R.id.player);
        //Typeface iconFont = Typeface.createFromAsset(getAssets(), "fontello.ttf");
        floatPlay = (FloatingActionButton)findViewById(R.id.floatPlayBut);
        //audioPlayerView.setTypeface(iconFont);
        //audioPlayerView.withUrl(url);

        floatPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                togglePlayer();
                //audioPlayerView.toggleAudio();
            }
        });
        /*audioPlayerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Clicked", Toast.LENGTH_SHORT).show();
                try {
                    audioPlayerView.toggleAudio();
                } catch (IOException e) {
                    //not found
                    Log.e("radioN", "IO error: " + e.toString());
                }
            }
        });*/

        audioPlayerView.setOnAudioPlayerViewListener(new AudioPlayerView.OnAudioPlayerViewListener() {
            @Override
            public void onAudioPreparing() {
                loadingAnimation.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAudioReady() {
                loadingAnimation.setVisibility(View.INVISIBLE);
                visualizer.startListening();
            }

            @Override
            public void onAudioFinished() {
                visualizer.stopListening();
            }
        });

        radioLV.setAdapter(adapter);
        adapter.SetOnItemClickListener(new RecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                selected_radio = custom.get(position);
                //RadioPlayer.play(selected_radio);
                Toast.makeText(MainActivity.this, radioList.get(position).getName(), Toast.LENGTH_SHORT).show();
                startTimer();
                try {
                    resetAll();
                    mediaPlayer.setDataSource(selected_radio.getUrl());
                    mediaPlayer.prepareAsync();
                    loadingAnimation.setVisibility(View.VISIBLE);
                    //hide play button
                    floatPlay.setVisibility(View.GONE);
                    /*Thread.sleep(10000);
                    if (!mediaPlayer.isPlaying()) {
                        throw new IOException();
                    }*/
                } catch (IllegalArgumentException e) {
                } catch (IllegalStateException e) {
                } catch (IOException e) {
                    Log.e("radioN", "IO error: " + e.toString());
                    Toast.makeText(MainActivity.this, "Unable to load "+selected_radio.getName(), Toast.LENGTH_LONG).show();
                    resetAll();
                }//catch (InterruptedException e){}
            }
        });
        radioLV.setLayoutManager(new LinearLayoutManager(this));

        mediaPlayer.setOnErrorListener(new MediaPlayer.OnErrorListener() {
            public boolean onError(MediaPlayer mp, int what, int extra) {
                Toast.makeText(MainActivity.this, "Unable to load "+selected_radio.getName(), Toast.LENGTH_LONG).show();
                resetAll();
                return false;
            }
        });

        mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            public void onPrepared(MediaPlayer mp) {
                mp.start();
                loadingAnimation.setVisibility(View.GONE);
                if (!visualizer.isActivated()) {
                    visualizer.startListening();
                    visualizer.setActivated(true);
                }
                // show showing
                showing.setVisibility(View.VISIBLE);
                setPlaying(selected_radio);

                if (floatPlay.getVisibility() == View.GONE)
                    floatPlay.setVisibility(View.VISIBLE);
            }
        });


    }

    public void resetAll(){
        mediaPlayer.reset();
        loadingAnimation.setVisibility(View.GONE);
        timeHandler.removeCallbacks(loadTimeRunnable);
    }

    public void setPlaying(Radio rad){
        playing_name.setText(rad.getName());
        try{
            playing_thumb.setImageResource(getImageId("r"+rad.getId(), "raw"));
        }catch (Exception e){
            playing_thumb.setImageResource(getImageId("ic_radio_64", "drawable"));
            playing_thumb.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        }
    }

    public int getImageId(String imageName, String folder) {
        return getResources().getIdentifier(imageName, folder, getPackageName());
    }

    public void togglePlayer(){
        if (mediaPlayer.isPlaying()){
            mediaPlayer.pause();
        }else{
            mediaPlayer.start();
        }
    }

    public void startTimer(){
        Log.d("radion", "Timer started");
        timeHandler = new Handler();
        loadTimeRunnable = new Runnable() {
            public void run() {
                Log.e("radion", "TIMER ENDED");
                Toast.makeText(MainActivity.this, "Timer: Unable to load "+selected_radio.getName(), Toast.LENGTH_LONG).show();
                resetAll();
            }
        };
        timeHandler.postDelayed(loadTimeRunnable, 1000);
    }

    public void loadDB(){
        LoadJson lj = new LoadJson(getText(R.string.radiojson).toString());
        lj.getJson();
        //radioList =
        lj.populate(mydb);
        //adapter.notifyDataSetChanged();
        radioList = new ArrayList<Radio>();
        radioList =mydb.getAllRadios();
        Toast.makeText(this, radioList.size()+" Radio stations", Toast.LENGTH_SHORT).show();
    }

    protected void onDestroy() {
        audioPlayerView.destroy();
        super.onDestroy();
    }
}
