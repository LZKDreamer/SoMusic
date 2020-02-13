package com.lzk.lib_common_ui.base.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Author: LiaoZhongKai.
 * Date: 2020/1/31
 * Function:Fragment基类
 */
public abstract class BaseFragment extends Fragment {

    protected Unbinder mUnbinder;

    protected Activity mActivity;
    //懒加载
    /**
     * 是否初始化过布局
     */
    protected boolean isViewInitiated;
    /**
     * 是否加载过数据
     */
    protected boolean isDataInitiated;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mActivity = getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(getLayoutId(),container,false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mUnbinder = ButterKnife.bind(this,view);
    }

    @Override
    public void onDestroy() {
        mUnbinder.unbind();
        super.onDestroy();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            lazyLoadData();
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        isViewInitiated=true;
        //加载数据
        lazyLoadData();
    }

    /**
     * 获取布局文件Id
     * @return
     */
    protected abstract int getLayoutId();

    /**
     * 懒加载数据
     */
    private void lazyLoadData(){
        if (getUserVisibleHint() && isViewInitiated && !isDataInitiated) {
            loadData();
            isDataInitiated = true;//不再重复加载
        }
    }

    /**
     * 加载数据
     */
    protected abstract void loadData();

    protected void showToast(String text){
        Toast.makeText(mActivity,text,Toast.LENGTH_SHORT).show();
    }
}
