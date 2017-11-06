package com.framgia.soundcloud.screen.detail;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import com.framgia.soundcloud.App;
import com.framgia.soundcloud.R;
import com.framgia.soundcloud.data.model.Track;
import com.framgia.soundcloud.databinding.ActivityDetailBinding;
import com.framgia.soundcloud.screen.BaseActivity;
import com.framgia.soundcloud.utils.Constant;
import java.util.List;
import javax.inject.Inject;

/**
 * Detail Screen.
 */
public class DetailActivity extends BaseActivity {
    public static final int REQUEST_CODE = 100;
    private static List<Track> sTracks;
    @Inject
    DetailContract.ViewModel mViewModel;
    @Inject
    TrackDownloadManager mTrackDownloadManager;

    public static Intent getIntentDetailActivity(Context context, int postion) {
        Intent intent = new Intent(context, DetailActivity.class);
        intent.putExtra(Constant.POSTION_BUNDLE, postion);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        return intent;
    }

    public static List<Track> getsTracks() {
        return sTracks;
    }

    public static void setsTracks(List<Track> sTracks) {
        DetailActivity.sTracks = sTracks;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        int postion = getIntent().getIntExtra(Constant.POSTION_BUNDLE, Constant.DEFAULT_VALUE_INT);
        DaggerDetailComponent.builder()
                .appComponent(((App) getApplication()).getComponent())
                .detailModule(new DetailModule(this, sTracks, postion))
                .build()
                .inject(this);
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle(sTracks.get(postion).getTitle());
        ActivityDetailBinding binding =
                DataBindingUtil.setContentView(this, R.layout.activity_detail);
        binding.setViewModel((DetailViewModel) mViewModel);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mViewModel.onStart();
    }

    @Override
    protected void onStop() {
        mViewModel.onStop();
        super.onStop();
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    @Override
    protected void onDestroy() {
        mViewModel.onDestroy();
        super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_detail, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.download:
                if (isGrantPermission()) {
                    mTrackDownloadManager.startDownload();
                } else {
                    requestPermission();
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    protected boolean isGrantPermission() {
        return ContextCompat.checkSelfPermission(this,
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_GRANTED;
    }

    protected void requestPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(new String[] { Manifest.permission.WRITE_EXTERNAL_STORAGE },
                    REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,
            int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    mTrackDownloadManager.startDownload();
                }
                break;
            default:
                break;
        }
    }
}
