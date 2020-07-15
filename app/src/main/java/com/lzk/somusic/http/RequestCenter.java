package com.lzk.somusic.http;

import androidx.annotation.NonNull;

import com.google.gson.Gson;
import com.lzk.somusic.recommend.model.RecommendData;
import com.lzk.somusic.recommend.retrofit.RecommendService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Author: LiaoZhongKai.
 * Date: 2020/2/1
 * Function:所有的网络请求
 */
public class RequestCenter {

    private static Retrofit getRetrofit(){
        return  new Retrofit.Builder()
                .baseUrl(HttpConstants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    /**
     * 获取推荐数据
     */
    public static void requestRecommendData(@NonNull IResultCallback callback){
        Gson gson = new Gson();
        RecommendData data = gson.fromJson(HttpConstants.RECOMMEND_JSON,RecommendData.class);
        callback.onSuccess(data);
//        callback.onSuccess();
//        Retrofit retrofit = getRetrofit();
//        RecommendService service = retrofit.create(RecommendService.class);
//        Call<RecommendData> call = service.getCall();
//        call.enqueue(new Callback<RecommendData>() {
//            @Override
//            public void onResponse(Call<RecommendData> call, Response<RecommendData> response) {
//                if (response.body().getErrCode() != HttpConstants.ERR_CODE){
//                    callback.onSuccess(response.body());
//                }else {
//                    callback.onFailed(response.body().getErrCode(),response.body().getErrMsg());
//                }
//            }
//
//            @Override
//            public void onFailure(Call<RecommendData> call, Throwable t) {
//                callback.onFailed(HttpConstants.ERR_CODE,t.getMessage());
//            }
//        });
    }
}
