package com.lzk.somusic.view;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.lzk.lib_audio.audioplayer.model.AudioBean;
import com.lzk.somusic.R;

/**
 * Author: LiaoZhongKai.
 * Date: 2020/2/12
 * Function:音乐跳动View
 */
public class AudioBeatView extends RelativeLayout {

    private Context mContext;
    private ImageView mImageView;
    private AnimationDrawable mAnimationDrawable;
    private AudioBean mAudioBean;

    public AudioBeatView(Context context) {
        this(context,null);
    }

    public AudioBeatView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        initView();
    }

    private void initView(){
        View view = LayoutInflater.from(mContext).inflate(R.layout.layout_audio_beat_view,this);
        mImageView = view.findViewById(R.id.audio_beat_view_iv);
        mAnimationDrawable = (AnimationDrawable) mImageView.getBackground();
    }

    public void setAudioBean(AudioBean audioBean){
        mAudioBean = audioBean;
    }

    public void startBeat(){
        if (mImageView.getVisibility() == GONE) {
            mImageView.setVisibility(VISIBLE);
        }
        mAnimationDrawable.start();
    }

    public void stopBeat(){if (mImageView.getVisibility() == VISIBLE) mAnimationDrawable.stop(); }

    public void hide(){
        if (mImageView.getVisibility() == VISIBLE) {
            mImageView.setVisibility(GONE);
        }
    }
}
