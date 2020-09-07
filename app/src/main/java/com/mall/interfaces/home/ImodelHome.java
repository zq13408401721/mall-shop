package com.mall.interfaces.home;

import com.mall.bean.HomeBean;
import com.mall.interfaces.IBasePersenter;

public interface ImodelHome {

    interface IModel{
        void getHomeDataReturn(HomeBean result);
    }

    interface IPersenter extends IBasePersenter {
        void getHomeData();
    }

}
