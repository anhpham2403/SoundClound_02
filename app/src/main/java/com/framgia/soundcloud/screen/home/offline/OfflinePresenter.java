package com.framgia.soundcloud.screen.home.offline;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import com.framgia.soundcloud.data.model.Track;
import com.framgia.soundcloud.data.model.User;
import java.util.ArrayList;
import java.util.List;

/**
 * Listens to user actions from the UI ({@link OfflineFragment}), retrieves the data and updates
 * the UI as required.
 */
final class OfflinePresenter implements OfflineContract.Presenter {
    private static final String TAG = OfflinePresenter.class.getName();
    private static Uri sArtworkUri = Uri.parse("content://media/external/audio/albumart");
    private OfflineContract.ViewModel mViewModel;
    private Context mContext;

    public OfflinePresenter(Context context) {
        mContext = context;
    }

    @Override
    public void onStart() {
    }

    @Override
    public void onStop() {
    }

    @Override
    public void setViewModel(OfflineContract.ViewModel viewModel) {
        mViewModel = viewModel;
    }

    @Override
    public void getTracks() {
        ContentResolver resolver = mContext.getContentResolver();
        Uri uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        Cursor cursor = resolver.query(uri, null, null, null, null);
        List<Track> tracks = new ArrayList<>();
        if (cursor != null & cursor.moveToFirst()) {
            do {
                Track track = new Track();
                track.setTitle(cursor.getString(
                        cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.TITLE)));
                track.setUser(new User(cursor.getString(
                        cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ARTIST))));
                track.setArtworkUrl(String.valueOf(ContentUris.withAppendedId(sArtworkUri,
                        cursor.getInt(
                                cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ALBUM_ID)))));
                track.setDuration(cursor.getInt(
                        cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DURATION)));
                track.setUri(cursor.getString(
                        cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA)));
                tracks.add(track);
            } while (cursor.moveToNext());
            mViewModel.onLoadTrack(tracks);
        }
    }
}
