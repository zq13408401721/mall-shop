package com.mall.persenter.cart;

import com.mall.base.BasePersenter;
import com.mall.bean.CartBean;
import com.mall.bean.DeleteCartBean;
import com.mall.common.CommonSubscriber;
import com.mall.interfaces.cart.ICart;
import com.mall.model.HttpManager;
import com.mall.utils.RxUtils;

public class CartListPersenter extends BasePersenter<ICart.ICartView> implements ICart.ICartPersenter {
    @Override
    public void getCartList() {
        addSubscribe(HttpManager.getInstance().getMallApi().getCartList()
                .compose(RxUtils.<CartBean>rxScheduler())
                .subscribeWith(new CommonSubscriber<CartBean>(mView) {
                    @Override
                    public void onNext(CartBean result) {
                        mView.getCartListReturn(result);
                    }
                }));
    }

    @Override
    public void deleteCartList(String productIds) {
        addSubscribe(HttpManager.getInstance().getMallApi().cartDelete(productIds)
                .compose(RxUtils.<DeleteCartBean>rxScheduler())
                .subscribeWith(new CommonSubscriber<DeleteCartBean>(mView) {
                    @Override
                    public void onNext(DeleteCartBean result) {
                        mView.deleteCartListReturn(result);
                    }
                }));
    }
}
