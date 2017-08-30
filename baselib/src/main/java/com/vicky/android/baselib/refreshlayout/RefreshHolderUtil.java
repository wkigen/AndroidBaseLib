package com.vicky.android.baselib.refreshlayout;

import android.content.Context;

import com.vicky.android.baselib.R;

public class RefreshHolderUtil {
    public static RefreshViewHolder getHolder(Context ctx) {
        MeiTuanRefreshViewHolder meiTuanRefreshViewHolder = new MeiTuanRefreshViewHolder(ctx, true);
        meiTuanRefreshViewHolder.setPullDownImageResource(R.mipmap.refresh_loading01);
        meiTuanRefreshViewHolder.setChangeToReleaseRefreshAnimResId(R.drawable.bga_refresh_mt_change_to_release_refresh);
        meiTuanRefreshViewHolder.setRefreshingAnimResId(R.drawable.bga_refresh_mt_refreshing);
        return meiTuanRefreshViewHolder;
    }

    public static RefreshViewHolder getHolder(Context ctx, boolean loadMoreEnable) {
        MeiTuanRefreshViewHolder meiTuanRefreshViewHolder = new MeiTuanRefreshViewHolder(ctx, loadMoreEnable);
        meiTuanRefreshViewHolder.setPullDownImageResource(R.mipmap.refresh_loading01);
        meiTuanRefreshViewHolder.setChangeToReleaseRefreshAnimResId(R.drawable.bga_refresh_mt_change_to_release_refresh);
        meiTuanRefreshViewHolder.setRefreshingAnimResId(R.drawable.bga_refresh_mt_refreshing);
        return meiTuanRefreshViewHolder;
    }

}
