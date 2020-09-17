package com.mall.model.api;

import com.mall.bean.AddCartInfoBean;
import com.mall.bean.CartBean;
import com.mall.bean.DeleteCartBean;
import com.mall.bean.GoodDetailBean;
import com.mall.bean.HomeBean;

import org.intellij.lang.annotations.Flow;

import io.reactivex.Flowable;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface Imall {

    @GET("index")
    Flowable<HomeBean> getHomeContent();

    //商品购买页详情
    @GET("goods/detail")
    Flowable<GoodDetailBean> getGoodDetail(@Query("id") int id);

    //添加到购物车
    @POST("cart/add")
    @FormUrlEncoded
    Flowable<AddCartInfoBean> addCart(@Field("goodsId") int goodsId,@Field("number") int number, @Field("productId") int productId);

    @GET("cart/index")
    @FormUrlEncoded
    Flowable<CartBean> getCartList();

    //删除购物车
    @POST("cart/delete")
    Flowable<DeleteCartBean> cartDelete(@Field("productIds") String productIds);

}
