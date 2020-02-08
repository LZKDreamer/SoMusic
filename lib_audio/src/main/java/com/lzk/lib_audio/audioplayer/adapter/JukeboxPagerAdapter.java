package com.lzk.lib_audio.audioplayer.adapter;

import android.animation.ObjectAnimator;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.lzk.lib_audio.R;
import com.lzk.lib_audio.audioplayer.core.AudioController;
import com.lzk.lib_audio.audioplayer.model.AudioBean;
import com.lzk.lib_image_loader.ImageLoadManager;

import java.util.ArrayList;

/**
 * Author: LiaoZhongKai.
 * Date: 2020/2/6
 * Function:
 */
public class JukeboxPagerAdapter extends PagerAdapter {

    private ArrayList<AudioBean> mBeanArrayList;
    private SparseArray<ObjectAnimator> mAnimatorArray;

    public JukeboxPagerAdapter(ArrayList<AudioBean> list){
        mBeanArrayList = list;
        mAnimatorArray = new SparseArray<>();
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View view = LayoutInflater.from(container.getContext()).inflate(R.layout.layout_jukebox_pager_item,null);
        ImageView imageView = view.findViewById(R.id.jukebox_pager_item_pic_iv);
        ImageLoadManager.getInstance().loadRoundedImg(imageView,mBeanArrayList.get(position).getPicUrl());
        mAnimatorArray.put(position,createAnimator(view));
        container.addView(view);
        return view;
    }

    @Override
    public int getCount() {
        return mBeanArrayList == null ? 0 : mBeanArrayList.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return object == view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    private ObjectAnimator createAnimator(View view){
        ObjectAnimator animator = ObjectAnimator.ofFloat(view,View.ROTATION,0,360);
        animator.setDuration(10*1000);
        animator.setRepeatCount(-1);
        animator.setInterpolator(new LinearInterpolator());
        if (AudioController.getInstance().isPlaying()){
            animator.start();
        }
        return animator;
    }

    public ObjectAnimator getAnimator(int position){
        if (position < mAnimatorArray.size()){
            return mAnimatorArray.get(position);
        }
        return null;
    }

    public SparseArray<ObjectAnimator> getAnimatorList(){
        return mAnimatorArray;
    }
}
