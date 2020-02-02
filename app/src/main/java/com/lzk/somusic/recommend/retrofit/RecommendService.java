package com.lzk.somusic.recommend.retrofit;

import com.lzk.somusic.http.HttpConstants;
import com.lzk.somusic.recommend.model.RecommendData;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Author: LiaoZhongKai.
 * Date: 2020/2/1
 * Function:推荐接口
 */
public interface RecommendService {
    @GET(HttpConstants.RECOMMEND)
    Call<RecommendData> getCall();
}
