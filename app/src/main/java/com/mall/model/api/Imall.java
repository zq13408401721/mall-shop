package com.mall.model.api;

import com.mall.bean.GoodDetailBean;
import com.mall.bean.HomeBean;

import io.reactivex.Flowable;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface Imall {

    @GET("index")
    Flowable<HomeBean> getHomeContent();

    //商品购买页详情
    @GET("goods/detail")
    Flowable<GoodDetailBean> getGoodDetail(@Query("id") int id);

}
