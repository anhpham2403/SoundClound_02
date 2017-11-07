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
import android.widget.SeekBar;
import com.framgia.soundcloud.BR;
import com.framgia.soundcloud.R;
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

public class DetailViewModel extends BaseObservable
        implements DetailContract.ViewModel, SeekBar.OnSeekBarChangeListener {

    private static final int PERCENT = 100;
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
    private int mSecondProgressPercentage;
    private boolean mIsSuffle;
    private int mIconPlay;
    private int mIconShuffle;
    private int mIconLoop;
    private String mTime;
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
    private BroadcastReceiver mUpdateState = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            switch (intent.getAction()) {
                case Constant.IntentKey.ACTION_PAUSE_SONG_FROM_NOTIFICATION:
                    updateState(intent);
                    break;
                default:
                    break;
            }
        }
    };
    private BroadcastReceiver mUpdateSong = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            switch (intent.getAction()) {
                case Constant.IntentKey.ACTION_POSTION:
                    setUpdateSong(intent);
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
            onStartMedia();
            mPresenter.getStateMediaPlayer();
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
        mIconShuffle = R.drawable.ic_shuffle_off;
        mIconLoop = R.drawable.ic_repeat_off;
        mIconPlay = R.drawable.ic_pause;
        registerBroadcast();
    }

    private void registerBroadcast() {
        IntentFilter filterUpdate = new IntentFilter(Constant.BROADCAST_UPDATE_CONTROL);
        filterUpdate.addAction(Constant.ACTION_UPDATE_SEEK_BAR);
        mContext.registerReceiver(mUpdateInfo, filterUpdate);
        IntentFilter filterState =
                new IntentFilter(Constant.IntentKey.ACTION_PAUSE_SONG_FROM_NOTIFICATION);
        mContext.registerReceiver(mUpdateState, filterState);
        IntentFilter filterSong = new IntentFilter(Constant.IntentKey.ACTION_POSTION);
        mContext.registerReceiver(mUpdateSong, filterSong);
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
        notifyPropertyChanged(BR.time);
    }

    @Bindable
    public String getTime() {
        SimpleDateFormat format = new SimpleDateFormat("mm:ss", Locale.getDefault());
        return format.format(new Date(mTrack.getDuration()));
    }

    @Override
    public void setStateLoop(int loop) {
        switch (loop) {
            case MusicService.LoopMode.NONE_LOOP:
                setLoop(MusicService.LoopMode.NONE_LOOP);
                setIconLoop(R.drawable.ic_repeat_off);
                break;
            case MusicService.LoopMode.LOOP_LIST_TRACKS:
                setLoop(MusicService.LoopMode.LOOP_LIST_TRACKS);
                setIconLoop(R.drawable.ic_repeat_on);
                break;
            case MusicService.LoopMode.LOOP_TRACK:
                setLoop(MusicService.LoopMode.LOOP_TRACK);
                setIconLoop(R.drawable.ic_repeat_one);
                break;
            default:
                break;
        }
    }

    @Override
    public void setStateShuffle(boolean isShuffle) {
        setSuffle(isShuffle);
        setIconShuffle(isShuffle ? R.drawable.ic_shuffle_on : R.drawable.ic_shuffle_off);
    }

    @Override
    public void onDestroy() {
        mContext.stopService(mPlayIntent);
        mMusicService = null;
        mContext.unregisterReceiver(mUpdateState);
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

    public void onStartMedia() {
        if (mMusicService.getMediaState() == MusicService.StateMode.STATE_PAUSE
                && DetailActivity.getsTracks().indexOf(mMusicService.getCurrentTrack())
                == mPostion) {
            mMusicService.updateStateMedia();
        } else if (mMusicService.getMediaState() == MusicService.StateMode.STATE_PLAYING
                && DetailActivity.getsTracks().indexOf(mMusicService.getCurrentTrack())
                == mPostion) {
            mMusicService.updateStateMedia();
        } else {
            mMusicService.playTrack();
        }
    }

    @Override
    public void onPauseTrack() {
        mMusicService.pauseTrack();
        setPlay(false);
    }

    @Override
    public void onNextTrack() {
        mMusicService.playNext();
    }

    @Override
    public void onPrevTrack() {
        mMusicService.playPrev();
    }

    @Override
    public void onSuffleTrackClick() {
        setSuffle(!mIsSuffle);
        setIconShuffle(mIsSuffle ? R.drawable.ic_shuffle_on : R.drawable.ic_shuffle_off);
    }

    @Override
    public void onLoopTrackClick() {
        switch (mMusicService.getLoop()) {
            case MusicService.LoopMode.NONE_LOOP:
                setLoop(MusicService.LoopMode.LOOP_LIST_TRACKS);
                setIconLoop(R.drawable.ic_repeat_on);
                break;
            case MusicService.LoopMode.LOOP_LIST_TRACKS:
                setLoop(MusicService.LoopMode.LOOP_TRACK);
                setIconLoop(R.drawable.ic_repeat_one);
                break;
            case MusicService.LoopMode.LOOP_TRACK:
                setLoop(MusicService.LoopMode.NONE_LOOP);
                setIconLoop(R.drawable.ic_repeat_off);
                break;
            default:
                break;
        }
    }

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
        int bufferingLevel = intent.getIntExtra(Constant.BUFFERING_LEVEL, 0);
        setCurrentPosition(time);
        mPresenter.updateSeekBar(time, mTrack.getDuration());
        setSecondProgressPercentage(bufferingLevel);
    }

    public void updateState(Intent intent) {
        setPlay(intent.getBooleanExtra(Constant.IntentKey.KEY_SEND_PAUSE, false));
        setIconPlay(isPlay() ? R.drawable.ic_pause : R.drawable.ic_play);
    }

    public void setUpdateSong(Intent intent) {
        setTrack(DetailActivity.getsTracks()
                .get(intent.getIntExtra(Constant.IntentKey.ACTION_POSTION, 0)));
    }

    public void onClickPlay() {
        if (mIsPlay) {
            onPauseTrack();
            setIconPlay(R.drawable.ic_play);
        } else {
            onPlayTrack();
            setIconPlay(R.drawable.ic_pause);
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

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        if (!fromUser) {
            return;
        }
        int time = progress * mTrack.getDuration() / PERCENT;
        mMusicService.seekTo(time);
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }

    @Bindable
    public boolean isSuffle() {
        return mIsSuffle;
    }

    public void setSuffle(boolean suffle) {
        mMusicService.setSuffle(suffle);
        mIsSuffle = suffle;
        notifyPropertyChanged(BR.suffle);
    }

    @Bindable
    public int getLoop() {
        if (mMusicService == null) {
            return MusicService.LoopMode.NONE_LOOP;
        }
        return mMusicService.getLoop();
    }

    public void setLoop(int loop) {
        if (mMusicService == null) {
            return;
        }
        mMusicService.setLoop(loop);
        notifyPropertyChanged(BR.loop);
    }

    @Bindable
    public int getIconPlay() {
        return mIconPlay;
    }

    public void setIconPlay(int iconPlay) {
        mIconPlay = iconPlay;
        notifyPropertyChanged(BR.iconPlay);
    }

    @Bindable
    public int getIconShuffle() {
        return mIconShuffle;
    }

    public void setIconShuffle(int iconShuffle) {
        mIconShuffle = iconShuffle;
        notifyPropertyChanged(BR.iconShuffle);
    }

    @Bindable
    public int getIconLoop() {
        return mIconLoop;
    }

    public void setIconLoop(int iconLoop) {
        mIconLoop = iconLoop;
        notifyPropertyChanged(BR.iconLoop);
    }

    @Bindable
    public int getSecondProgressPercentage() {
        return mSecondProgressPercentage;
    }

    public void setSecondProgressPercentage(int secondProgressPercentage) {
        mSecondProgressPercentage = secondProgressPercentage;
        notifyPropertyChanged(BR.secondProgressPercentage);
    }
}
