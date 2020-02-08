package com.lzk.lib_audio.audioplayer.view;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.print.PrinterId;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;

import androidx.viewpager.widget.ViewPager;

import com.lzk.lib_audio.R;
import com.lzk.lib_audio.audioplayer.adapter.JukeboxPagerAdapter;
import com.lzk.lib_audio.audioplayer.app.AudioPlayerManager;
import com.lzk.lib_audio.audioplayer.core.AudioController;
import com.lzk.lib_audio.audioplayer.event.AudioLoadEvent;
import com.lzk.lib_audio.audioplayer.event.AudioPauseEvent;
import com.lzk.lib_audio.audioplayer.event.AudioStartEvent;
import com.lzk.lib_audio.audioplayer.event.EventBusHelper;
import com.lzk.lib_audio.audioplayer.model.AudioBean;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

/**
 * Author: LiaoZhongKai.
 * Date: 2020/2/6
 * Function:唱片机View
 */
public class JukeboxView extends RelativeLayout {

    private Context mContext;
    private ViewPager mViewPager;
    private JukeboxPagerAdapter mPagerAdapter;
    private ArrayList<AudioBean> mBeanArrayList;
    private AudioBean mAudioBean;

    public JukeboxView(Context context) {
        this(context, null);
    }

    public JukeboxView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        EventBusHelper.getInstance().register(this);
        initData();
        initView();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        EventBusHelper.getInstance().unregister(this);
    }

    private void initView() {
        View view = LayoutInflater.from(mContext).inflate(R.layout.layout_jukebox_view, this);
        mViewPager = view.findViewById(R.id.jukebox_view_vp);
        initViewPager();
        showLoadView(false);
    }

    private void initData() {
        mBeanArrayList = AudioController.getInstance().getQueue();
        mAudioBean = AudioController.getInstance().getAudio();
    }

    private void initViewPager() {
        mPagerAdapter = new JukeboxPagerAdapter(mBeanArrayList);
        mViewPager.setOverScrollMode(ViewPager.OVER_SCROLL_NEVER);
        mViewPager.setAdapter(mPagerAdapter);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                AudioBean audioBean = AudioController.getInstance().getQueue().get(position);
                //该AudioBean不是正在播放的AudioBean
                if (!AudioController.getInstance().getAudio().getUrl().equals(audioBean.getUrl())){
                    AudioController.getInstance().play(position);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                switch (state){
                    case ViewPager.SCROLL_STATE_IDLE:
                        showStartView();
                        break;
                    case ViewPager.SCROLL_STATE_DRAGGING:
                        showPauseView();
                        break;
                }
            }
        });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onLoadEvent(AudioLoadEvent event) {
        mAudioBean = event.mAudioBean;
        showLoadView(true);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onStartEvent(AudioStartEvent event) {
        showStartView();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onPauseEvent(AudioPauseEvent event){
        showPauseView();
    }

    private void showLoadView(boolean smooth){
        //暂停所有动画
        SparseArray<ObjectAnimator> animatorArray = mPagerAdapter.getAnimatorList();
        if (animatorArray != null && animatorArray.size() > 0){
            for (int i=0;i<animatorArray.size();i++){
                int key = animatorArray.keyAt(i);
                ObjectAnimator animator = animatorArray.get(key);
                if (animator.isStarted()){
                    animator.pause();
                }
            }
        }
        if (mAudioBean != null){
            mViewPager.setCurrentItem(mBeanArrayList.indexOf(mAudioBean),smooth);
        }
    }

    private void showStartView(){
        ObjectAnimator animator = mPagerAdapter.getAnimator(mViewPager.getCurrentItem());
        if (animator != null) {
            if (AudioController.getInstance().isPlaying()){
                if (animator.isPaused()) {
                    animator.resume();
                } else {
                    animator.start();
                }
            }
        }
    }

    private void showPauseView(){
        ObjectAnimator animator = mPagerAdapter.getAnimator(mViewPager.getCurrentItem());
        if (animator != null) animator.pause();
    }
}