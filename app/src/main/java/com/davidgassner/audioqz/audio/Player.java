package com.davidgassner.audioqz.audio;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.util.Log;

import com.davidgassner.audioqz.R;
import com.davidgassner.audioqz.model.Cue;
import com.davidgassner.audioqz.util.FileHelper;

import java.io.FileInputStream;
import java.io.IOException;

public class Player {

    private Context context;
    private Cue cue;
    private MediaPlayer mediaPlayer;
    private boolean isPrepared = false;
    private final String TAG = getClass().getSimpleName();

    public Player(Context context, Cue cue) {
        this.context = context;
        this.cue = cue;
    }

    public boolean prepareAndPlay() {

//        Temporary - right now all audio files are in assets
        String fullFileName =
                FileHelper.copyAssetToCache(context, cue.getTargetFile());

        mediaPlayer = new MediaPlayer();
        mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                isPrepared = true;
                play();
            }
        });

        FileInputStream stream = null;

        try {
            stream = new FileInputStream(fullFileName);
            mediaPlayer.setDataSource(stream.getFD());
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mediaPlayer.prepare();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } finally {
            if (stream != null) {
                try {
                    stream.close();
                } catch (IOException ignore) {
                }
            }
        }
        return true;
    }

    public void play() {
        if (isPrepared) {
            mediaPlayer.start();
        }
    }

    //  Stop this cue and set its player object to null
    public void stop() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

    //  Called from MainActivity when the user manipulates the SeekBar
    public void setVolume(int progress) {
        float volume = (float) progress / context.getResources().getInteger(R.integer.seek_bar_max);
        Log.d(TAG, "Volume: " + volume);
        mediaPlayer.setVolume(volume, volume);
    }

    public void changeVolume(float newVolume, int duration) {

    }

}
