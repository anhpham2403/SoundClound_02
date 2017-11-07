package com.framgia.soundcloud.utils.binding;

import android.content.Context;
import android.databinding.BindingAdapter;
import android.graphics.Rect;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
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

    @BindingAdapter({ "bind:progressPercentage", "bind:secondProgressPercentage" })
    public static void setProgressPercentage(SeekBar seekBar, int progressPercentage,
            int secondProgressPercentage) {
        seekBar.setProgress(progressPercentage);
        seekBar.setSecondaryProgress(secondProgressPercentage);
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

    @BindingAdapter({ "scrollListenner" })
    public static void setScrollListenner(RecyclerView recyclerView,
            RecyclerView.OnScrollListener listener) {
        recyclerView.addOnScrollListener(listener);
    }

    @BindingAdapter({ "app:manager", "app:fragment", "app:service" })
    public static void setFragmentManager(FrameLayout layout, FragmentManager manager,
            Fragment fragment, boolean isServiceRunning) {
        if (fragment == null) {
            return;
        }
        if (!isServiceRunning) {
            return;
        }
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(layout.getId(), fragment).commit();
    }

    @BindingAdapter({ "bind:decorationItem", "bind:marginItem" })
    public static void setDecorationRecyclerView(final RecyclerView recyclerView, final int column,
            final int margin) {
        final Context context = recyclerView.getContext();
        final int dp = (int) (margin * context.getResources().getDisplayMetrics().density);
        RecyclerView.ItemDecoration itemDecoration = new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent,
                    RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                int position = parent.getChildLayoutPosition(view);
                outRect.right = dp;
                outRect.bottom = dp;
                if (position < column) {
                    outRect.top = dp;
                }
                if (position % column == 0) {
                    outRect.left = dp;
                }
            }
        };
        recyclerView.addItemDecoration(itemDecoration);
    }

    @BindingAdapter({ "bind:setSelected" })
    public static void setSelectedTextView(TextView view, boolean b) {
        view.setSelected(b);
    }
}
