package com.vicky.android.baselib.adapter.core;

import android.view.ViewGroup;
import android.widget.CompoundButton;

/**
 * for recyclerView and adapterView subItem
 */
public interface OnItemChildCheckedChangeListener {
    void onItemChildCheckedChanged(ViewGroup parent, CompoundButton childView, int position, boolean isChecked);
}