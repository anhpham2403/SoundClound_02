package com.framgia.soundcloud.screen.detail;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import com.framgia.soundcloud.App;
import com.framgia.soundcloud.R;
import com.framgia.soundcloud.data.model.Track;
import com.framgia.soundcloud.databinding.ActivityDetailBinding;
import com.framgia.soundcloud.screen.BaseActivity;
import com.framgia.soundcloud.screen.listtracks.ListtracksViewModel;
import com.framgia.soundcloud.utils.Constant;
import java.util.List;
import javax.inject.Inject;

/**
 * Detail Screen.
 */
public class DetailActivity extends BaseActivity {
    @Inject
    DetailContract.ViewModel mViewModel;

    public static Intent getIntentDetailActivity(Context context, int postion) {
        Intent intent = new Intent(context, DetailActivity.class);
        intent.putExtra(Constant.POSTION_BUNDLE, postion);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        List<Track> tracks = ListtracksViewModel.getsTracks();
        int postion = getIntent().getIntExtra(Constant.POSTION_BUNDLE, Constant.DEFAULT_VALUE_INT);
        DaggerDetailComponent.builder()
                .appComponent(((App) getApplication()).getComponent())
                .detailModule(new DetailModule(this, tracks, postion))
                .build()
                .inject(this);
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle(tracks.get(postion).getTitle());
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
}
