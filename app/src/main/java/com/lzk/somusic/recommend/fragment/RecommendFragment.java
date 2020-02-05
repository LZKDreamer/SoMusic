package com.lzk.somusic.recommend.fragment;


import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.lzk.lib_audio.audioplayer.app.AudioPlayerManager;
import com.lzk.lib_audio.audioplayer.model.AudioBean;
import com.lzk.lib_common_ui.base.fragment.BaseFragment;
import com.lzk.lib_common_ui.utils.CommonUtil;
import com.lzk.somusic.R;
import com.lzk.somusic.recommend.adapter.BannerImgAdapter;
import com.lzk.somusic.recommend.adapter.RecommendAdapter;
import com.lzk.somusic.recommend.contract.RecommendContract;
import com.lzk.somusic.recommend.model.RecommendData;
import com.lzk.somusic.recommend.presenter.RecommendPresenter;
import com.lzk.somusic.view.RecommendGridView;
import com.youth.banner.Banner;
import com.youth.banner.config.IndicatorConfig;
import com.youth.banner.indicator.CircleIndicator;
import com.youth.banner.listener.OnBannerListener;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 推荐
 * A simple {@link Fragment} subclass.
 */
public class RecommendFragment extends BaseFragment implements RecommendContract.View {

    @BindView(R.id.recommend_rv)
    RecyclerView mRecommendRv;
    private RecommendContract.Presenter mPresenter;
    private RecommendAdapter mAdapter;

    public static RecommendFragment newInstance() {

        Bundle args = new Bundle();

        RecommendFragment fragment = new RecommendFragment();
        fragment.setArguments(args);
        return fragment;
    }

    private RecommendFragment() {
        // Required empty public constructor
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_recommend;
    }

    @Override
    protected void loadData() {
        mPresenter = new RecommendPresenter(this);
        if (CommonUtil.isNetworkConnected(mActivity)) {
            mPresenter.requestRecommendData();
        }
    }

    @Override
    public void getRecommendData(RecommendData data) {
        initRecyclerView(data);
    }

    @Override
    public void showFailedView(String errMsg) {
        showToast(getResources().getString(R.string.error_message));
    }

    @Override
    public void setPresenter(RecommendContract.Presenter presenter) {
        mPresenter = presenter;
    }

    /**
     * 初始化Banner
     *
     * @param dataBean
     * @return
     */
    private View initBanner(RecommendData.DataBean dataBean) {
        View view = LayoutInflater.from(mActivity).inflate(R.layout.layout_recommend_banner,null);
        Banner banner = view.findViewById(R.id.recommend_banner);
        banner.setAdapter(new BannerImgAdapter(mActivity, dataBean.getBanner()));
        banner.setIndicator(new CircleIndicator(mActivity));
        banner.setIndicatorGravity(IndicatorConfig.Direction.CENTER);
        banner.start();
        banner.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(Object data, int position) {
                RecommendData.DataBean.BannerBean bannerBean = dataBean.getBanner().get(position);
                AudioBean audioBean = new AudioBean();
                audioBean.setUrl(bannerBean.getUrl());
                audioBean.setPicUrl(bannerBean.getImg());
                audioBean.setAuthor(bannerBean.getAuthor());
                audioBean.setName(bannerBean.getName());
                audioBean.setDuration(bannerBean.getDuration());
                AudioPlayerManager.addAudio(audioBean);
            }

            @Override
            public void onBannerChanged(int position) {

            }
        });
        return view;
    }

    private void initRecyclerView(RecommendData data) {
        mAdapter = new RecommendAdapter(mActivity, R.layout.layout_recommend_item, data.getData().getGuess());
        mAdapter.addHeaderView(initBanner(data.getData()));
        RecommendGridView gridView = new RecommendGridView(mActivity,data.getData());
        gridView.setOnItemClickListener(new RecommendGridView.OnClickListener() {
            @Override
            public void onItemClick(int position, RecommendData.DataBean.RecommendBean item) { ;
                AudioBean audioBean = new AudioBean();
                audioBean.setUrl(item.getUrl());
                audioBean.setPicUrl(item.getImg());
                audioBean.setAuthor(item.getAuthor());
                audioBean.setName(item.getName());
                audioBean.setDuration(item.getDuration());
                AudioPlayerManager.addAudio(audioBean);
            }
        });
        mAdapter.addHeaderView(gridView);
        View loveHeader = LayoutInflater.from(mActivity).inflate(R.layout.layout_recommend_guess,null);
        mAdapter.addHeaderView(loveHeader);

        mRecommendRv.setLayoutManager(new LinearLayoutManager(mActivity));
        mRecommendRv.addItemDecoration(new DividerItemDecoration(mActivity,LinearLayoutManager.VERTICAL));
        mRecommendRv.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                RecommendData.DataBean.GuessBean guessBean = data.getData().getGuess().get(position);
                AudioBean audioBean = new AudioBean();
                audioBean.setUrl(guessBean.getUrl());
                audioBean.setPicUrl(guessBean.getImg());
                audioBean.setAuthor(guessBean.getAuthor());
                audioBean.setName(guessBean.getName());
                audioBean.setDuration(guessBean.getDuration());
                AudioPlayerManager.addAudio(audioBean);
            }
        });
    }

}
