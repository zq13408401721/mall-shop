package com.mall.persenter.cart;

import com.mall.base.BasePersenter;
import com.mall.bean.GoodDetailBean;
import com.mall.bean.HomeBean;
import com.mall.common.CommonSubscriber;
import com.mall.interfaces.cart.ICart;
import com.mall.model.HttpManager;
import com.mall.utils.RxUtils;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.functions.Function;

public class CartPersenter extends BasePersenter<ICart.IView> implements ICart.IPersenter {
    @Override
    public void getGoodDetail(int id) {
        addSubscribe(HttpManager.getInstance().getMallApi().getGoodDetail(id)
                .compose(RxUtils.<GoodDetailBean>rxScheduler())
                .subscribeWith(new CommonSubscriber<GoodDetailBean>(mView) {
                    @Override
                    public void onNext(GoodDetailBean result) {
                        mView.getGoodDetailReturn(result);
                    }
                }));
    }

}
