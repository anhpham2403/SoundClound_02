package com.framgia.soundcloud.service;

import android.app.Service;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.PowerManager;
import android.support.annotation.IntDef;
import android.support.annotation.Nullable;
import android.util.Log;
import com.framgia.soundcloud.data.model.Track;
import com.framgia.soundcloud.utils.Constant;
import java.io.IOException;
import java.lang.annotation.Retention;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static java.lang.annotation.RetentionPolicy.SOURCE;

/**
 * Created by anh on 26/10/2017.
 */

public class MusicService extends Service
        implements MediaPlayer.OnPreparedListener, MediaPlayer.OnErrorListener,
        MediaPlayer.OnCompletionListener {
    private MediaPlayer mPlayer;
    private List<Track> mTracks;
    private int mPostion;
    private int mResumePosition;
    private Random mRandom;
    private boolean mIsSuffle;
    private int mLoop;
    private int mMediaState = StateMode.STATE_IDLE;
    private IBinder mIBinder = new MusicService.MyBinder();
    private Handler mSeekBarHandler = new Handler();
    private Runnable mUpdateTime = new Runnable() {
        public void run() {
            if (mPlayer.isPlaying()) {
                Intent broadcastIntent = new Intent(Constant.BROADCAST_UPDATE_CONTROL);
                broadcastIntent.setAction(Constant.ACTION_UPDATE_SEEK_BAR);
                broadcastIntent.putExtra(Constant.BROADCAST_CURRENT_POSTION,
                        mPlayer.getCurrentPosition());
                getApplicationContext().sendBroadcast(broadcastIntent);
            }
            mSeekBarHandler.postDelayed(this, Constant.SEEKBAR_DELAY_TIME);
        }
    };

    public void updateSeekBar() {
        mSeekBarHandler.postDelayed(mUpdateTime, Constant.SEEKBAR_DELAY_TIME);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mPlayer = new MediaPlayer();
        mTracks = new ArrayList<>();
        mPostion = 0;
        mRandom = new Random();
        mIsSuffle = false;
        mLoop = LoopMode.NONE_LOOP;
        initMusicPlayer();
    }

    public void initMusicPlayer() {
        mPlayer.setWakeMode(getApplicationContext(), PowerManager.PARTIAL_WAKE_LOCK);
        mPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mPlayer.setOnPreparedListener(this);
        mPlayer.setOnCompletionListener(this);
        mPlayer.setOnErrorListener(this);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mIBinder;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        mPlayer.stop();
        mPlayer.release();
        return false;
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        if (mp.isLooping()) {
            return;
        }
        if (mp.getCurrentPosition() > 0) {
            mp.reset();
            playNext();
        }
    }

    @Override
    public boolean onError(MediaPlayer mp, int what, int extra) {
        mp.reset();
        return false;
    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        mp.start();
    }

    @Override
    public void onDestroy() {
    }

    public void playTrack() {
        mPlayer.reset();
        try {
            mPlayer.setDataSource(mTracks.get(mPostion).getFullUri());
        } catch (IOException e) {
            Log.getStackTraceString(e);
        }
        mPlayer.prepareAsync();
        updateSeekBar();
        mMediaState = StateMode.STATE_PLAYING;
    }

    public void pauseTrack() {
        if (!mPlayer.isPlaying()) {
            return;
        }
        mPlayer.pause();
        mResumePosition = mPlayer.getCurrentPosition();
        mSeekBarHandler.removeCallbacks(mUpdateTime);
        mMediaState = StateMode.STATE_PAUSE;
    }

    public void resumeTrack() {
        if (mPlayer.isPlaying()) {
            return;
        }
        mPlayer.seekTo(mResumePosition);
        mPlayer.start();
        updateSeekBar();
        mMediaState = StateMode.STATE_PLAYING;
    }

    public void playPrev() {
        if (mLoop == LoopMode.LOOP_TRACK) {
            return;
        }
        mPostion--;
        if (mPostion < 0) {
            mPostion = 0;
            if (mLoop == LoopMode.LOOP_LIST_TRACKS) {
                mPostion = mTracks.size() - 1;
            }
        }
        playTrack();
    }

    public void playNext() {
        if (mLoop == LoopMode.LOOP_TRACK) {
            return;
        }
        mPostion++;
        if (mPostion >= mTracks.size()) {
            mPostion = 0;
        }
        if (mIsSuffle) {
            mPostion = mRandom.nextInt(mTracks.size() - 1);
        }
        playTrack();
    }

    public void setSuffle(boolean isSuffle) {
        mIsSuffle = isSuffle;
    }

    public void seekTo(int position) {
        mPlayer.seekTo(position);
        if (mPlayer.isPlaying()) {
            return;
        }
        mPlayer.start();
        updateSeekBar();
    }

    public void setPostion(int postion) {
        mPostion = postion;
    }

    public List<Track> getTracks() {
        return mTracks;
    }

    public void setTracks(List<Track> tracks) {
        mTracks = tracks;
    }

    public Track getCurrentTrack() {
        return mTracks.get(mPostion);
    }

    @StateMode
    public int getMediaState() {
        return mMediaState;
    }

    public boolean isPlaying() {
        return mPlayer.isPlaying();
    }

    public int getLoop() {
        return mLoop;
    }

    public void setLoop(@LoopMode int loop) {
        mLoop = loop;
        if (mLoop == LoopMode.NONE_LOOP) {
            mPlayer.setLooping(false);
        } else {
            mPlayer.setLooping(true);
        }
    }

    /**
     * loop mode
     */
    @Retention(SOURCE)
    @IntDef({ LoopMode.NONE_LOOP, LoopMode.LOOP_LIST_TRACKS, LoopMode.LOOP_TRACK })
    public @interface LoopMode {
    int NONE_LOOP = 0;
    int LOOP_LIST_TRACKS = 1;
    int LOOP_TRACK = 2;
    }

    /**
     * state mode
     */
    @Retention(SOURCE)
    @IntDef({
            StateMode.STATE_IDLE, StateMode.STATE_PLAYING, StateMode.STATE_PAUSE,
            StateMode.STATE_STOP
    })
    public @interface StateMode {
    int STATE_IDLE = 0;
    int STATE_PLAYING = 1;
    int STATE_PAUSE = 2;
    int STATE_STOP = 3;
    }

    /**
     * binder class
     */
    public class MyBinder extends Binder {
        public MusicService getMyService() {
            return MusicService.this;
        }
    }
}
