package com.framgia.soundcloud.service;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.PowerManager;
import android.support.annotation.IntDef;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.Toast;
import com.framgia.soundcloud.R;
import com.framgia.soundcloud.data.model.Track;
import com.framgia.soundcloud.data.source.local.sharedpref.SharedPrefsImplement;
import com.framgia.soundcloud.screen.detail.DetailActivity;
import com.framgia.soundcloud.utils.Constant;
import com.squareup.picasso.Picasso;
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
        MediaPlayer.OnCompletionListener, MediaPlayer.OnBufferingUpdateListener {
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
    private int mBufferingLevel;
    private RemoteViews mRemoteViews;
    private BroadCastMusic mBroadCastMusic = new BroadCastMusic();
    private NotificationCompat.Builder mBuilder;
    private SharedPrefsImplement mPreferences;
    private Track mCurrenTrack;
    private Runnable mUpdateTime = new Runnable() {
        public void run() {
                Intent broadcastIntent = new Intent(Constant.BROADCAST_UPDATE_CONTROL);
                broadcastIntent.setAction(Constant.ACTION_UPDATE_SEEK_BAR);
                broadcastIntent.putExtra(Constant.BROADCAST_CURRENT_POSTION,
                        mPlayer.getCurrentPosition());
                broadcastIntent.putExtra(Constant.BUFFERING_LEVEL, mBufferingLevel);
                getApplicationContext().sendBroadcast(broadcastIntent);
            mSeekBarHandler.postDelayed(this, Constant.SEEKBAR_DELAY_TIME);
        }
    };

    public void updateSeekBar() {
        mSeekBarHandler.postDelayed(mUpdateTime, Constant.SEEKBAR_DELAY_TIME);
    }
    public void updateStateMedia() {
        mSeekBarHandler.postDelayed(mUpdateTime, 0);
        Intent intentPauseOrPlay =
                new Intent(Constant.IntentKey.ACTION_PAUSE_SONG_FROM_NOTIFICATION);
        intentPauseOrPlay.putExtra(Constant.IntentKey.KEY_SEND_PAUSE, mPlayer.isPlaying());
        sendBroadcast(intentPauseOrPlay);
    }
    @Override
    public void onCreate() {
        super.onCreate();
        mPreferences = new SharedPrefsImplement(getApplicationContext());
        mPlayer = new MediaPlayer();
        mTracks = new ArrayList<>();
        mPostion = 0;
        mRandom = new Random();
        mIsSuffle = mPreferences.get(Constant.PREF_IS_SUFFLE, Boolean.class);
        mLoop = mPreferences.get(Constant.PREF_LOOP_MODE, Integer.class);
        mBufferingLevel = 0;
        initMusicPlayer();
        registerReceiver();
    }

    public void registerReceiver() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Constant.IntentKey.ACTION_PREVIOUS);
        intentFilter.addAction(Constant.IntentKey.ACTION_NEXT);
        intentFilter.addAction(Constant.IntentKey.ACTION_PAUSE_OR_PLAY_SONG);
        intentFilter.addAction(Constant.IntentKey.ACTION_START_FOREGROUND);
        intentFilter.addAction(Constant.IntentKey.ACTION_SEND_DATA);
        intentFilter.addAction(Constant.IntentKey.ACTION_STOP);
        intentFilter.addAction(Constant.IntentKey.ACTION_POSTION);
        registerReceiver(mBroadCastMusic, intentFilter);
    }

    public void initMusicPlayer() {
        mPlayer.setWakeMode(getApplicationContext(), PowerManager.PARTIAL_WAKE_LOCK);
        mPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mPlayer.setOnPreparedListener(this);
        mPlayer.setOnCompletionListener(this);
        mPlayer.setOnErrorListener(this);
        mPlayer.setOnBufferingUpdateListener(this);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        runForeground();
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
        if (mLoop == LoopMode.LOOP_LIST_TRACKS) {
            mp.reset();
            playNext();
        }
    }

    @Override
    public boolean onError(MediaPlayer mp, int what, int extra) {
        mp.reset();
        mRemoteViews.setImageViewResource(R.id.btn_play, R.drawable.ic_play);
        mRemoteViews.setTextViewText(R.id.tv_title, getCurrentTrack().getTitle());
        mRemoteViews.setTextViewText(R.id.tv_actor, getCurrentTrack().getUser().getUserName());
        startForeground(Constant.IntentKey.NOTIFICATION_ID, mBuilder.build());
        Intent intentPauseOrPlay =
                new Intent(Constant.IntentKey.ACTION_PAUSE_SONG_FROM_NOTIFICATION);
        intentPauseOrPlay.putExtra(Constant.IntentKey.KEY_SEND_PAUSE, mPlayer.isPlaying());
        sendBroadcast(intentPauseOrPlay);
        Toast.makeText(this, Constant.CANT_PLAY_TRACK, Toast.LENGTH_LONG).show();
        return true;
    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        mp.start();
        mRemoteViews.setImageViewResource(R.id.btn_play, R.drawable.ic_pause);
        mRemoteViews.setTextViewText(R.id.tv_title, getCurrentTrack().getTitle());
        mRemoteViews.setTextViewText(R.id.tv_actor, getCurrentTrack().getUser().getUserName());
        startForeground(Constant.IntentKey.NOTIFICATION_ID, mBuilder.build());
        Intent intentPauseOrPlay =
                new Intent(Constant.IntentKey.ACTION_PAUSE_SONG_FROM_NOTIFICATION);
        intentPauseOrPlay.putExtra(Constant.IntentKey.KEY_SEND_PAUSE, mPlayer.isPlaying());
        sendBroadcast(intentPauseOrPlay);
    }

    @Override
    public void onDestroy() {
        unregisterReceiver(mBroadCastMusic);
        super.onDestroy();
    }

    public void playTrack() {
        if (mTracks.indexOf(mCurrenTrack) == mPostion) {
            return;
        }
        mPlayer.reset();
        try {
            mPlayer.setDataSource(mTracks.get(mPostion).getUri());
        } catch (IOException e) {
            Log.getStackTraceString(e);
        }
        mCurrenTrack = mTracks.get(mPostion);
        mPlayer.prepareAsync();
        updateSeekBar();
        mMediaState = StateMode.STATE_PLAYING;
        Intent intentPress =
                DetailActivity.getIntentDetailActivity(getApplicationContext(), mPostion);
        intentPress.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent pendingIntent =
                PendingIntent.getActivity(this, Constant.IntentKey.REQUEST_CODE_NOTIFICATION,
                        intentPress, PendingIntent.FLAG_UPDATE_CURRENT);
        mBuilder.setContentIntent(pendingIntent);
        mBuilder.setPriority(Notification.PRIORITY_MAX);
        if (getCurrentTrack().getArtworkUrl() != null) {
            Picasso.with(this)
                    .load(getCurrentTrack().getArtworkUrl())
                    .into(mRemoteViews, R.id.image_artwork, Constant.IntentKey.NOTIFICATION_ID,
                            mBuilder.build());
        } else {
            Picasso.with(this)
                    .load(R.drawable.ic_default)
                    .into(mRemoteViews, R.id.image_artwork, Constant.IntentKey.NOTIFICATION_ID,
                            mBuilder.build());
        }
    }

    public void pauseTrack() {
        if (!mPlayer.isPlaying()) {
            return;
        }
        mPlayer.pause();
        mResumePosition = mPlayer.getCurrentPosition();
        mSeekBarHandler.removeCallbacks(mUpdateTime);
        mMediaState = StateMode.STATE_PAUSE;
        mRemoteViews.setImageViewResource(R.id.btn_play, R.drawable.ic_play);
        startForeground(Constant.IntentKey.NOTIFICATION_ID, mBuilder.build());
        Intent intentPauseOrPlay =
                new Intent(Constant.IntentKey.ACTION_PAUSE_SONG_FROM_NOTIFICATION);
        intentPauseOrPlay.putExtra(Constant.IntentKey.KEY_SEND_PAUSE, mPlayer.isPlaying());
        sendBroadcast(intentPauseOrPlay);
    }

    public void resumeTrack() {
        if (mPlayer.isPlaying()) {
            return;
        }
        mPlayer.seekTo(mResumePosition);
        mPlayer.start();
        updateSeekBar();
        mMediaState = StateMode.STATE_PLAYING;
        mRemoteViews.setImageViewResource(R.id.btn_play, R.drawable.ic_pause);
        startForeground(Constant.IntentKey.NOTIFICATION_ID, mBuilder.build());
        Intent intentPauseOrPlay =
                new Intent(Constant.IntentKey.ACTION_PAUSE_SONG_FROM_NOTIFICATION);
        intentPauseOrPlay.putExtra(Constant.IntentKey.KEY_SEND_PAUSE, mPlayer.isPlaying());
        sendBroadcast(intentPauseOrPlay);
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
        Intent intentPosition = new Intent(Constant.IntentKey.ACTION_POSTION);
        intentPosition.putExtra(Constant.IntentKey.ACTION_POSTION, mPostion);
        sendBroadcast(intentPosition);
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
        Intent intentPosition = new Intent(Constant.IntentKey.ACTION_POSTION);
        intentPosition.putExtra(Constant.IntentKey.ACTION_POSTION, mPostion);
        sendBroadcast(intentPosition);
        playTrack();
    }

    public void setSuffle(boolean isSuffle) {
        mPreferences.put(Constant.PREF_IS_SUFFLE, isSuffle);
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
        return mCurrenTrack;
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
        mPreferences.put(Constant.PREF_LOOP_MODE, loop);
        if (mLoop == LoopMode.LOOP_TRACK) {
            mPlayer.setLooping(true);
        } else {
            mPlayer.setLooping(false);
        }
    }

    @Override
    public void onBufferingUpdate(MediaPlayer mp, int percent) {
        mBufferingLevel = percent;
    }

    private void runForeground() {
        mRemoteViews =
                new RemoteViews(getPackageName(), R.layout.layout_notification_music_service);
        mRemoteViews.setImageViewResource(R.id.btn_next, R.drawable.ic_next);
        mRemoteViews.setImageViewResource(R.id.btn_play, R.drawable.ic_play);
        mRemoteViews.setImageViewResource(R.id.btn_prev, R.drawable.ic_previous);
        mBuilder =
                new NotificationCompat.Builder(this).setSmallIcon(R.drawable.ic_stat_notification)
                        .setCustomBigContentView(mRemoteViews);
        PendingIntent pre = PendingIntent.getBroadcast(getApplicationContext(),
                Constant.IntentKey.REQUEST_CODE_NOTIFICATION,
                new Intent(Constant.IntentKey.ACTION_PREVIOUS), 0);
        mRemoteViews.setOnClickPendingIntent(R.id.btn_prev, pre);
        PendingIntent next = PendingIntent.getBroadcast(getApplicationContext(),
                Constant.IntentKey.REQUEST_CODE_NOTIFICATION,
                new Intent(Constant.IntentKey.ACTION_NEXT), 0);
        mRemoteViews.setOnClickPendingIntent(R.id.btn_next, next);
        Intent intentP = new Intent(Constant.IntentKey.ACTION_PAUSE_OR_PLAY_SONG);
        intentP.putExtra(Constant.IntentKey.KEY_SEND_PAUSE_OR_PLAY, mPlayer.isPlaying());
        PendingIntent pause = PendingIntent.getBroadcast(getApplicationContext(),
                Constant.IntentKey.REQUEST_CODE_NOTIFICATION, intentP, 0);
        mRemoteViews.setOnClickPendingIntent(R.id.btn_play, pause);
        startForeground(Constant.IntentKey.NOTIFICATION_ID, mBuilder.build());
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
     * BroadCast class
     */
    class BroadCastMusic extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            switch (intent.getAction()) {
                case Constant.IntentKey.ACTION_PREVIOUS:
                    playPrev();
                    break;
                case Constant.IntentKey.ACTION_NEXT:
                    playNext();
                    break;
                case Constant.IntentKey.ACTION_PAUSE_OR_PLAY_SONG:
                    if (mPlayer == null) {
                        return;
                    }
                    if (mPlayer.isPlaying()) {
                        pauseTrack();
                    } else {
                        resumeTrack();
                    }
                    Intent intentPauseOrPlay =
                            new Intent(Constant.IntentKey.ACTION_PAUSE_SONG_FROM_NOTIFICATION);
                    intentPauseOrPlay.putExtra(Constant.IntentKey.KEY_SEND_PAUSE,
                            mPlayer.isPlaying());
                    sendBroadcast(intentPauseOrPlay);
                    break;
            }
        }
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
