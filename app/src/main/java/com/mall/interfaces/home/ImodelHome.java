package com.mall.interfaces.home;

import com.mall.bean.HomeBean;
import com.mall.interfaces.IBasePersenter;
import com.mall.interfaces.IBaseView;

import java.util.List;

public interface ImodelHome {

    interface IView extends IBaseView {
        void getHomeDataReturn(List<HomeBean.HomeListBean> result);
    }

    interface IPersenter extends IBasePersenter<IView> {
        void getHomeData();
    }

}
