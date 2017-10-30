package com.framgia.soundcloud.screen.listtracks;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import com.framgia.soundcloud.App;
import com.framgia.soundcloud.R;
import com.framgia.soundcloud.data.model.Category;
import com.framgia.soundcloud.databinding.ActivityListtracksBinding;
import com.framgia.soundcloud.screen.BaseActivity;
import com.framgia.soundcloud.utils.Constant;
import javax.inject.Inject;

/**
 * Listtracks Screen.
 */
public class ListtracksActivity extends BaseActivity {
    @Inject
    ListtracksContract.ViewModel mViewModel;

    public static Intent getCategory(Context context, Category category) {
        Intent intent = new Intent(context, ListtracksActivity.class);
        intent.putExtra(Constant.CATEGORY_BUNDLE, category);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Category category = getIntent().getParcelableExtra(Constant.CATEGORY_BUNDLE);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle(category.getName());
        DaggerListtracksComponent.builder()
                .appComponent(((App) getApplication()).getComponent())
                .listtracksModule(new ListtracksModule(this, category))
                .build()
                .inject(this);
        super.onCreate(savedInstanceState);
        ActivityListtracksBinding binding =
                DataBindingUtil.setContentView(this, R.layout.activity_listtracks);
        binding.setViewModel((ListtracksViewModel) mViewModel);
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
    protected void onDestroy() {
        mViewModel.onDestroy();
        super.onDestroy();
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}
