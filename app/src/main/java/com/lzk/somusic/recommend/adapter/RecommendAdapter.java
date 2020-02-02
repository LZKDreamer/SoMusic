package com.lzk.somusic.recommend.adapter;

import android.content.Context;
import android.util.Log;
import android.widget.ImageView;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lzk.lib_common_ui.utils.CommonUtil;
import com.lzk.lib_image_loader.ImageLoadManager;
import com.lzk.somusic.R;
import com.lzk.somusic.recommend.model.RecommendData;

import java.util.List;

/**
 * Author: LiaoZhongKai.
 * Date: 2020/2/1
 * Function:推荐Adapter
 */
public class RecommendAdapter extends BaseQuickAdapter<RecommendData.DataBean.GuessBean, BaseViewHolder> {

    private Context mContext;

    public RecommendAdapter(Context context,int layoutResId, @Nullable List<RecommendData.DataBean.GuessBean> data) {
        super(layoutResId, data);
        mContext = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, RecommendData.DataBean.GuessBean item) {
        helper.setText(R.id.recommend_item_name_tv,item.getName());
        helper.setText(R.id.recommend_item_duration_tv,item.getDuration());
        helper.setText(R.id.recommend_item_author_tv,item.getAuthor());
        ImageView imageView = helper.getView(R.id.recommend_item_pic_iv);
        ImageLoadManager.getInstance().loadRoundedRectImg(imageView,item.getImg(), CommonUtil.dip2px(mContext,mContext.getResources().getDimension(R.dimen.img_rounded_corner)));
    }
}
