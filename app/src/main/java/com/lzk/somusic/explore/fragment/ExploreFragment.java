package com.lzk.somusic.explore.fragment;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lzk.lib_common_ui.base.fragment.BaseFragment;
import com.lzk.somusic.R;

/**
 * 发现
 * A simple {@link Fragment} subclass.
 */
public class ExploreFragment extends BaseFragment {

    public static ExploreFragment newInstance() {

        Bundle args = new Bundle();

        ExploreFragment fragment = new ExploreFragment();
        fragment.setArguments(args);
        return fragment;
    }

    private ExploreFragment() {
        // Required empty public constructor
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_explore;
    }

    @Override
    protected void loadData() {

    }

}
