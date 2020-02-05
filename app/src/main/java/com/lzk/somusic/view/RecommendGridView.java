package com.lzk.somusic.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lzk.lib_common_ui.utils.CommonUtil;
import com.lzk.lib_image_loader.ImageLoadManager;
import com.lzk.somusic.R;
import com.lzk.somusic.recommend.model.RecommendData;

import java.util.List;

import retrofit2.http.POST;

/**
 * Author: LiaoZhongKai.
 * Date: 2020/2/2
 * Function:推荐列表网格View
 */
public class RecommendGridView extends RelativeLayout {

    private Context mContext;
    private RecommendData.DataBean mDataBean;
    private RecyclerView mRecyclerView;
    private OnClickListener mOnClickListener;

    public RecommendGridView(Context context, RecommendData.DataBean dataBean) {
        this(context,null,dataBean);
    }

    public RecommendGridView(Context context, AttributeSet attrs, RecommendData.DataBean dataBean) {
        super(context, attrs);
        mContext = context;
        mDataBean = dataBean;
        initView();
    }

    private void initView(){
        View view = LayoutInflater.from(mContext).inflate(R.layout.layout_recommend_gird_view,this);
        mRecyclerView = view.findViewById(R.id.recommend_grid_rv);
        mRecyclerView.setLayoutManager(new GridLayoutManager(mContext,3));
        GridAdapter adapter = new GridAdapter(R.layout.layout_recommend_grid_item,mDataBean.getRecommend()) ;
        mRecyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (mOnClickListener != null){
                    mOnClickListener.onItemClick(position, (RecommendData.DataBean.RecommendBean) adapter.getData().get(position));
                }
            }
        });
    }

    class GridAdapter extends BaseQuickAdapter<RecommendData.DataBean.RecommendBean, BaseViewHolder>{

        public GridAdapter(int layoutResId, @Nullable List<RecommendData.DataBean.RecommendBean> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, RecommendData.DataBean.RecommendBean item) {
            helper.setText(R.id.recommend_item_tv,item.getName());
            ImageView imageView = helper.getView(R.id.recommend_item_iv);
            ImageLoadManager.getInstance().loadRoundedRectImg(imageView,item.getImg(), CommonUtil.dip2px(mContext,mContext.getResources().getDimension(R.dimen.img_rounded_corner)));
        }
    }

    public interface OnClickListener{
        void onItemClick(int position,RecommendData.DataBean.RecommendBean item);
    }

    public void setOnItemClickListener(OnClickListener listener){
        mOnClickListener = listener;
    }
}
