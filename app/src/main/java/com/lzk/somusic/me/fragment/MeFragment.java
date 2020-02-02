package com.lzk.somusic.me.fragment;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lzk.lib_common_ui.base.fragment.BaseFragment;
import com.lzk.somusic.R;

/**
 * 我的
 * A simple {@link Fragment} subclass.
 */
public class MeFragment extends BaseFragment {


    public static MeFragment newInstance() {

        Bundle args = new Bundle();

        MeFragment fragment = new MeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    private MeFragment() {
        // Required empty public constructor
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_me;
    }

    @Override
    protected void loadData() {

    }

}
