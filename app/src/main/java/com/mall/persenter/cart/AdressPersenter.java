package com.mall.persenter.cart;

import com.mall.base.BasePersenter;
import com.mall.bean.AdressBean;
import com.mall.common.CommonSubscriber;
import com.mall.interfaces.cart.ICart;
import com.mall.model.HttpManager;
import com.mall.utils.RxUtils;

public class AdressPersenter extends BasePersenter<ICart.IAdressView> implements ICart.IAdressPersenter {
    @Override
    public void getAdressById(int parentId) {
        addSubscribe(HttpManager.getInstance().getMallApi().getAdressById(parentId)
                .compose(RxUtils.<AdressBean>rxScheduler())
                .subscribeWith(new CommonSubscriber<AdressBean>(mView) {
                    @Override
                    public void onNext(AdressBean result) {
                        mView.getAdressByIdReturn(result);
                    }
                }));
    }
}
