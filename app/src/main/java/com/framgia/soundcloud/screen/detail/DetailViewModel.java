package com.framgia.soundcloud.screen.detail;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.os.IBinder;
import com.framgia.soundcloud.BR;
import com.framgia.soundcloud.data.model.Track;
import com.framgia.soundcloud.service.MusicService;
import com.framgia.soundcloud.utils.Constant;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Exposes the data to be used in the Detail screen.
 */

public class DetailViewModel extends BaseObservable implements DetailContract.ViewModel {

    private DetailContract.Presenter mPresenter;
    private boolean mIsPlay;
    private int mCurrentPostion;
    private Context mContext;
    private Intent mPlayIntent;
    private MusicService mMusicService;
    private List<Track> mTracks;
    private int mPostion;
    private Track mTrack;
    private int mProgressPercentage;
    private BroadcastReceiver mUpdateInfo = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            switch (intent.getAction()) {
                case Constant.ACTION_UPDATE_SEEK_BAR:
                    loadSeekBar(intent);
                    break;
                default:
                    break;
            }
        }
    };
    private ServiceConnection mMusicConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            MusicService.MyBinder binder = (MusicService.MyBinder) service;
            mMusicService = binder.getMyService();
            mMusicService.setTracks(mTracks);
            mMusicService.setPostion(mPostion);
            onPlayTrack();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
        }
    };

    public DetailViewModel(Context context, DetailContract.Presenter presenter, List<Track> tracks,
            int postion) {
        mPresenter = presenter;
        mPresenter.setViewModel(this);
        mTracks = tracks;
        mPostion = postion;
        mIsPlay = isPlaying();
        mContext = context;
        mTrack = tracks.get(postion);
        registerBroadcast();
    }

    private void registerBroadcast() {
        IntentFilter filterUpdate = new IntentFilter(Constant.BROADCAST_UPDATE_CONTROL);
        filterUpdate.addAction(Constant.ACTION_UPDATE_SEEK_BAR);
        mContext.registerReceiver(mUpdateInfo, filterUpdate);
    }

    @Override
    public void onStart() {
        mPresenter.onStart();
        if (mPlayIntent == null) {
            mPlayIntent = new Intent(mContext, MusicService.class);
            mContext.bindService(mPlayIntent, mMusicConnection, Context.BIND_AUTO_CREATE);
            mContext.startService(mPlayIntent);
        }
    }

    @Override
    public void onStop() {
        mPresenter.onStop();
    }

    @Bindable
    public Track getTrack() {
        return mTrack;
    }

    public void setTrack(Track track) {
        mTrack = track;
        notifyPropertyChanged(BR.track);
    }

    @Bindable
    public String getTime() {
        SimpleDateFormat format = new SimpleDateFormat("mm:ss", Locale.getDefault());
        return format.format(new Date(mTrack.getDuration()));
    }

    @Override
    public void onDestroy() {
        mContext.stopService(mPlayIntent);
        mMusicService = null;
    }

    @Override
    public void onPlayTrack() {
        if (mMusicService.getMediaState() == MusicService.StateMode.STATE_PAUSE) {
            mMusicService.resumeTrack();
        } else {
            mMusicService.playTrack();
        }
        setPlay(true);
    }

    @Override
    public void onPauseTrack() {
        mMusicService.pauseTrack();
        setPlay(false);
    }

    @Override
    public void onNextTrack() {
        mMusicService.playNext();
        setTrack(mMusicService.getCurrentTrack());
    }

    @Override
    public void onPrevTrack() {
        mMusicService.playPrev();
        setTrack(mMusicService.getCurrentTrack());
    }

    @Override
    public boolean isPlaying() {
        if (mMusicService == null) {
            return false;
        }
        return mMusicService.isPlaying();
    }

    @Override
    public void setSeekBar(int progressPercentage) {
        setProgressPercentage(progressPercentage);
    }

    public void loadSeekBar(Intent intent) {
        int time = intent.getIntExtra(Constant.BROADCAST_CURRENT_POSTION, 0);
        setCurrentPosition(time);
        mPresenter.updateSeekBar(time, mTrack.getDuration());
    }

    public void onClickPlay() {
        if (mIsPlay) {
            onPauseTrack();
        } else {
            onPlayTrack();
        }
    }

    @Bindable
    public boolean isPlay() {
        return mIsPlay;
    }

    public void setPlay(boolean play) {
        mIsPlay = play;
        notifyPropertyChanged(BR.play);
    }

    @Bindable
    public String getCurrentPosition() {
        SimpleDateFormat format = new SimpleDateFormat("mm:ss", Locale.getDefault());
        return format.format(new Date(mCurrentPostion));
    }

    public void setCurrentPosition(int currentPosition) {
        mCurrentPostion = currentPosition;
        notifyPropertyChanged(BR.currentPosition);
    }

    @Bindable
    public int getProgressPercentage() {
        return mProgressPercentage;
    }

    public void setProgressPercentage(int progressPercentage) {
        mProgressPercentage = progressPercentage;
        notifyPropertyChanged(BR.progressPercentage);
    }
}
