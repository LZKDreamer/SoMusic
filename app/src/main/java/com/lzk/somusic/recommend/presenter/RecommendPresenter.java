package com.lzk.somusic.recommend.presenter;

import com.lzk.somusic.http.IResultCallback;
import com.lzk.somusic.http.RequestCenter;
import com.lzk.somusic.recommend.contract.RecommendContract;
import com.lzk.somusic.recommend.model.RecommendData;

/**
 * Author: LiaoZhongKai.
 * Date: 2020/2/1
 * Function:
 */
public class RecommendPresenter implements RecommendContract.Presenter {

    private RecommendContract.View mView;

    public RecommendPresenter(RecommendContract.View view){
        mView = view;
        mView.setPresenter(this);
    }

    @Override
    public void requestRecommendData() {
        RequestCenter.requestRecommendData(new IResultCallback() {
            @Override
            public void onSuccess(Object object) {
                mView.getRecommendData((RecommendData) object);
            }

            @Override
            public void onFailed(int errCode, String errMsg) {
                mView.showFailedView(errMsg);
            }
        });
    }
}
