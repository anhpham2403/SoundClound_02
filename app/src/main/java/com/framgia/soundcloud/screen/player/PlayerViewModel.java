package com.framgia.soundcloud.screen.player;

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
import com.framgia.soundcloud.R;
import com.framgia.soundcloud.data.model.Track;
import com.framgia.soundcloud.screen.detail.DetailActivity;
import com.framgia.soundcloud.service.MusicService;
import com.framgia.soundcloud.utils.Constant;

/**
 * Exposes the data to be used in the Player screen.
 */

public class PlayerViewModel extends BaseObservable implements PlayerContract.ViewModel {

    private PlayerContract.Presenter mPresenter;
    private Intent mPlayIntent;
    private MusicService mMusicService;
    private Context mContext;
    private boolean mIsPlay;
    private int mIconPlay;
    private Track mTrack;
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
            setTrack(mMusicService.getCurrentTrack());
            setPlay(mMusicService.isPlaying());
            if (mIsPlay) {
                setIconPlay(R.drawable.ic_pause);
            } else {
                setIconPlay(R.drawable.ic_play);
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
        }
    };

    public PlayerViewModel(Context context, PlayerContract.Presenter presenter) {
        mContext = context;
        mPresenter = presenter;
        mPresenter.setViewModel(this);
        registerBroadcast();
        mIconPlay = R.drawable.ic_play;
    }

    public void setUpdateSong(Intent intent) {
        setTrack(DetailActivity.getsTracks()
                .get(intent.getIntExtra(Constant.IntentKey.ACTION_POSTION, 0)));
    }

    private void registerBroadcast() {
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
        if (mMusicService != null) {
            setTrack(mMusicService.getCurrentTrack());
        }
    }

    @Override
    public void onStop() {
        mPresenter.onStop();
    }

    public void updateState(Intent intent) {
        setPlay(intent.getBooleanExtra(Constant.IntentKey.KEY_SEND_PAUSE, false));
        if (mIsPlay) {
            setIconPlay(R.drawable.ic_pause);
        } else {
            setIconPlay(R.drawable.ic_play);
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
    public int getIconPlay() {
        return mIconPlay;
    }

    public void setIconPlay(int iconPlay) {
        mIconPlay = iconPlay;
        notifyPropertyChanged(BR.iconPlay);
    }

    @Bindable
    public Track getTrack() {
        return mTrack;
    }

    public void setTrack(Track track) {
        mTrack = track;
        notifyPropertyChanged(BR.track);
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

    public void onClickPlay() {
        if (mIsPlay) {
            onPauseTrack();
            setIconPlay(R.drawable.ic_play);
        } else {
            onPlayTrack();
            setIconPlay(R.drawable.ic_pause);
        }
    }

    public void onClickFragment() {
        mContext.startActivity(DetailActivity.getIntentDetailActivity(mContext,
                DetailActivity.getsTracks().indexOf(mTrack)));
    }
}
