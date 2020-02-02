package com.lzk.somusic.recommend.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.lzk.lib_common_ui.utils.CommonUtil;
import com.lzk.lib_image_loader.ImageLoadManager;
import com.lzk.somusic.R;
import com.lzk.somusic.recommend.model.RecommendData;
import com.youth.banner.adapter.BannerAdapter;

import java.util.List;

/**
 * Author: LiaoZhongKai.
 * Date: 2020/2/2
 * Function:轮播图Banner的适配器
 */
public class BannerImgAdapter extends BannerAdapter<RecommendData.DataBean.BannerBean, BannerImgAdapter.ImageHolder> {

    private Context mContext;

    public BannerImgAdapter(Context context,List<RecommendData.DataBean.BannerBean> datas) {
        super(datas);
        mContext = context;
    }

    @Override
    public ImageHolder onCreateHolder(ViewGroup parent, int viewType) {
        ImageView imageView = new ImageView(parent.getContext());
        imageView.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        return new ImageHolder(imageView);
    }

    @Override
    public void onBindView(ImageHolder holder, RecommendData.DataBean.BannerBean data, int position, int size) {
        ImageLoadManager.getInstance().loadRoundedRectImg(holder.mImageView,data.getImg(),
                CommonUtil.dip2px(mContext,mContext.getResources().getDimension(R.dimen.img_rounded_corner)));
    }

    class ImageHolder extends RecyclerView.ViewHolder{

        ImageView mImageView;

        public ImageHolder(@NonNull View itemView) {
            super(itemView);
            mImageView = (ImageView) itemView;
        }
    }
}
