package com.mall.model.api;

import com.mall.bean.HomeBean;

import io.reactivex.Flowable;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.GET;

public interface Imall {

    @GET("index")
    Flowable<HomeBean> getHomeContent();

}
