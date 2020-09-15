package com.mall.interfaces.cart;

import com.mall.bean.GoodDetailBean;
import com.mall.interfaces.IBasePersenter;
import com.mall.interfaces.IBaseView;

public interface ICart {
    interface IView extends IBaseView{

        void getGoodDetailReturn(GoodDetailBean result);
    }

    interface IPersenter extends IBasePersenter<IView>{
        void getGoodDetail(int id);
    }
}
