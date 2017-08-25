package com.vicky.android.baselib.adapter.core;

import android.view.View;
import android.view.ViewGroup;

/**
 * for RecyclerView item
 */
public interface OnRVItemClickListener {
    void onRVItemClick(ViewGroup parent, View itemView, int position);
}