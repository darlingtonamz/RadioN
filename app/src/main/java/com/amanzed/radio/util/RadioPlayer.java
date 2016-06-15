/*
package com.amanzed.radion.util;

import android.content.Context;
import android.media.AudioManager;
import android.os.AsyncTask;
import android.util.Log;

import com.amanzed.radio.ScrollingActivity;
import com.amanzed.radio.list.Radio;

import java.io.IOException;

*/
/**
 * Created by Amanze on 10/24/2015.
 *//*

public class RadioPlayer {
    static Context ctx;
    public static Radio rd;

    public static void play(Radio rad){
        ctx = ScrollingActivity.ctx;
        rd = rad;
        ScrollingActivity.mediaPlayer.reset();
        ScrollingActivity.mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        try {
            Log.e("radio", "radio click: "+ rd.getName());
            ScrollingActivity.mediaPlayer.setDataSource(rd.getUrl());
        } catch (IllegalArgumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (SecurityException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IllegalStateException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        if (!ScrollingActivity.mediaPlayerIsSet) {
            ScrollingActivity.mediaPlayerIsSet = true;
        } else {
            ScrollingActivity.isPaused = false;
        }
        ScrollingActivity.state = false;
        playIt(); // reset the display and pause the system
        //}

        //thumb.setImageUrl(server + "img/radio/" + rd.getId() + ".png", imageLoader);
        //name.setText(rd.getName());
    }

    public static void playIt(){
        if(ScrollingActivity.mediaPlayer.isPlaying() && ScrollingActivity.state){
            ScrollingActivity.mediaPlayer.pause();
            ScrollingActivity.isPaused = true;
            //play.setImageResource(R.drawable.ic_play_arrow_black_48dp);
            //placeholder.setVisibility(View.GONE);
            ScrollingActivity.state = false;
        }
        else if(!ScrollingActivity.state && ScrollingActivity.internetState){
            new StartMediaPlayer().execute();
        }
    }

    static class StartMediaPlayer extends AsyncTask<String, Void, Boolean> {
        //SweetAlertDialog pDialog;
        protected void onPreExecute() {
            super.onPreExecute();
        }
        protected Boolean doInBackground(String... params) {
            boolean res = false;
            try {
                if (!ScrollingActivity.isPaused)
                    ScrollingActivity.mediaPlayer.prepare();
                ScrollingActivity.state = true;
                res = true;
            } catch (IllegalStateException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            ScrollingActivity.mediaPlayer.start();
            return res;
        }

        protected void onPostExecute(Boolean feed) {
            String msg;
            if (feed) {
                msg = "Playing";
                ((ScrollingActivity)ScrollingActivity.activity).hidePDialog();
                //play.setImageResource(R.drawable.ic_stop_black_48dp);
            }else {
                msg = "Failed to load radio station";
            }
            ScrollingActivity.displayMessage(msg);
            //Toast.makeText(ctx, msg, Toast.LENGTH_SHORT).show();
        }
    }
}
*/
