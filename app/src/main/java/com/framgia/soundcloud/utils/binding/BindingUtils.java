package com.framgia.soundcloud.utils.binding;

import android.databinding.BindingAdapter;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.SeekBar;
import com.framgia.soundcloud.R;
import com.framgia.soundcloud.screen.home.ViewPagerAdapter;
import com.framgia.soundcloud.utils.LayoutManagers;
import com.squareup.picasso.Picasso;

/**
 * Created by anh on 18/09/2017.
 */

public final class BindingUtils {
    private BindingUtils() {
    }

    @BindingAdapter({ "bind:viewPagerAdapter" })
    public static void setAdapterForViewPager(ViewPager viewPager, ViewPagerAdapter adapter) {
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(6);
    }

    @BindingAdapter({ "bind:viewPager" })
    public static void setViewPagerForTabLayout(TabLayout tabLayout, ViewPager viewPager) {
        tabLayout.setupWithViewPager(viewPager);
    }

    @BindingAdapter({ "bind:imageUrl" })
    public static void loadImage(ImageView view, String imageUrl) {
        Picasso.with(view.getContext())
                .load(imageUrl)
                .placeholder(R.drawable.ic_default)
                .error(R.drawable.ic_default)
                .into(view);
    }

    @BindingAdapter({ "bind:imageUrl" })
    public static void loadImage(ImageView view, int imageUrl) {
        Picasso.with(view.getContext()).load(imageUrl).into(view);
    }

    @BindingAdapter("layoutManager")
    public static void setLayoutManager(RecyclerView recyclerView,
            LayoutManagers.LayoutManagerFactory layoutManagerFactory) {
        recyclerView.setLayoutManager(layoutManagerFactory.create(recyclerView));
    }

    @BindingAdapter({ "bind:recyclerAdapter" })
    public static void setAdapterForRecyclerView(RecyclerView recyclerView,
            RecyclerView.Adapter adapter) {
        recyclerView.setAdapter(adapter);
    }

    @BindingAdapter({ "bind:progressPercentage" })
    public static void setProgressPercentage(SeekBar seekBar, int progressPercentage) {
        seekBar.setProgress(progressPercentage);
    }

    @BindingAdapter({ "bind:setOnChangeListener" })
    public static void setOnChangeListener(SeekBar seekBar,
            SeekBar.OnSeekBarChangeListener listener) {
        seekBar.setOnSeekBarChangeListener(listener);
    }

    @BindingAdapter({ "bind:setIcon" })
    public static void setIcon(ImageView view, int id) {
        view.setImageResource(id);
    }
}
