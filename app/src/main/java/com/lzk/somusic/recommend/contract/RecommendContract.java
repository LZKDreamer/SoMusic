package com.lzk.somusic.recommend.contract;

import com.lzk.lib_common_ui.base.mvp.BasePresenter;
import com.lzk.lib_common_ui.base.mvp.BaseView;
import com.lzk.somusic.recommend.model.RecommendData;

/**
 * Author: LiaoZhongKai.
 * Date: 2020/2/1
 * Function:
 */
public interface RecommendContract {

    interface View extends BaseView<Presenter>{
        void getRecommendData(RecommendData data);
        void showFailedView(String errMsg);
    }

    interface Presenter extends BasePresenter{
        void requestRecommendData();
    }

}
