package com.lzk.somusic.me.fragment;


import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListAdapter;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.lzk.lib_audio.audioplayer.app.AudioPlayerManager;
import com.lzk.lib_audio.audioplayer.event.AudioStartEvent;
import com.lzk.lib_audio.audioplayer.event.EventBusHelper;
import com.lzk.lib_audio.audioplayer.model.PlayBean;
import com.lzk.lib_common_ui.base.fragment.BaseFragment;
import com.lzk.somusic.R;
import com.lzk.somusic.me.adapter.PlayListAdapter;
import com.lzk.somusic.view.FavouriteListView;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import butterknife.BindView;

/**
 * 我的
 * A simple {@link Fragment} subclass.
 */
public class MeFragment extends BaseFragment {

    @BindView(R.id.me_rv)
    RecyclerView mMeRv;
    @BindView(R.id.me_favourite_view)
    FavouriteListView mMeFavouriteView;
    @BindView(R.id.me_playback_record_empty_view_ll)
    LinearLayout mEmptyViewLl;
    private PlayListAdapter mPlayListAdapter = null;
    private List<PlayBean> mPlayBeanList = null;

    public static MeFragment newInstance() {

        Bundle args = new Bundle();

        MeFragment fragment = new MeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_me;
    }

    @Override
    protected void loadData() {
        EventBusHelper.getInstance().register(this);
        mPlayBeanList = AudioPlayerManager.getPlayback();
        mMeFavouriteView.bindActivity(mActivity);
        setStateView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBusHelper.getInstance().unregister(this);
    }

    private void initRecyclerView() {
        if (mPlayListAdapter == null){
            mPlayListAdapter = new PlayListAdapter(R.layout.layout_play_list_item,mPlayBeanList);
            LinearLayoutManager layoutManager = new LinearLayoutManager(mActivity);
            mMeRv.setLayoutManager(layoutManager);
            mMeRv.setAdapter(mPlayListAdapter);
            mPlayListAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                    PlayBean playBean = (PlayBean) adapter.getData().get(position);
                    if (playBean != null && playBean.getAudioBean() != null){
                        AudioPlayerManager.addAudio(mActivity,playBean.getAudioBean());
                    }
                }
            });
        }else {
            mPlayListAdapter.setNewData(mPlayBeanList);
        }
    }

    private void setStateView(){
        if (mPlayBeanList == null || mPlayBeanList.size() == 0){
            mEmptyViewLl.setVisibility(View.VISIBLE);
            mMeRv.setVisibility(View.GONE);
        }else {
            mEmptyViewLl.setVisibility(View.GONE);
            mMeRv.setVisibility(View.VISIBLE);
            initRecyclerView();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onStartEvent(AudioStartEvent event) {
        mPlayBeanList = AudioPlayerManager.getPlayback();
        setStateView();
    }
}
