package com.lzk.somusic.view;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lzk.lib_audio.audioplayer.app.AudioPlayerManager;
import com.lzk.lib_audio.audioplayer.event.AudioCollectMusicEvent;
import com.lzk.lib_audio.audioplayer.event.EventBusHelper;
import com.lzk.lib_audio.audioplayer.model.FavouriteBean;
import com.lzk.lib_common_ui.utils.CommonUtil;
import com.lzk.lib_image_loader.ImageLoadManager;
import com.lzk.somusic.R;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

/**
 * Author: LiaoZhongKai.
 * Date: 2020/2/11
 * Function:我的--收藏View
 */
public class FavouriteListView extends RelativeLayout {

    private RecyclerView mRecyclerView;
    private LinearLayout mEmptyViewLl;
    private Context mContext;
    private FavouriteAdapter mAdapter;
    private List<FavouriteBean> mAudioBeans;
    private Activity mActivity;

    public FavouriteListView(Context context){
        this(context,null);
    }

    public FavouriteListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        initView();
        queryFavouriteAudioList();
    }

    private void initView(){
        View view = LayoutInflater.from(mContext).inflate(R.layout.layout_favourite_view,this);
        mRecyclerView = view.findViewById(R.id.favourite_view_rv);
        mEmptyViewLl = view.findViewById(R.id.favourite_empty_view_ll);
        EventBusHelper.getInstance().register(this);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        EventBusHelper.getInstance().unregister(this);
    }

    private void queryFavouriteAudioList(){
        mAudioBeans = AudioPlayerManager.getFavouriteAudioList();
        if (mAudioBeans != null && mAudioBeans.size() > 0){
            if (mEmptyViewLl.getVisibility() == VISIBLE) mEmptyViewLl.setVisibility(GONE);
            mRecyclerView.setVisibility(VISIBLE);
            if (mAdapter == null){
                initRecyclerView();
            }else {
                mAdapter.setNewData(mAudioBeans);
            }
        }else {
            mEmptyViewLl.setVisibility(VISIBLE);
            mRecyclerView.setVisibility(GONE);
        }
    }

    private void initRecyclerView(){
        mAdapter = new FavouriteAdapter(R.layout.layout_favourite_list_item,mAudioBeans);
        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext,RecyclerView.HORIZONTAL,false);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                FavouriteBean favouriteBean = (FavouriteBean) adapter.getData().get(position);
                if (mActivity != null){
                    AudioPlayerManager.addAudio(mActivity,favouriteBean.getAudioBean());
                }else {
                    AudioPlayerManager.addAudio(favouriteBean.getAudioBean());
                }
            }
        });
    }

    public void bindActivity(Activity activity){
        this.mActivity = activity;
    }

    class FavouriteAdapter extends BaseQuickAdapter<FavouriteBean, BaseViewHolder>{

        public FavouriteAdapter(int layoutResId, @Nullable List<FavouriteBean> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, FavouriteBean item) {
            if (item.getAudioBean() != null && !TextUtils.isEmpty(item.getAudioBean().getPicUrl())){
                ImageView imageView = helper.getView(R.id.favourite_list_item_iv);
                ImageLoadManager.getInstance().loadRoundedRectImg(imageView,item.getAudioBean().getPicUrl(),
                        CommonUtil.dip2px(mContext,mContext.getResources().getDimension(R.dimen.img_rounded_corner)));
            }
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onCollectMusicEvent(AudioCollectMusicEvent event){
        queryFavouriteAudioList();
    }
}
