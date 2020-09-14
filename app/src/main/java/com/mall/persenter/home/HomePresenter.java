package com.mall.persenter.home;

import com.mall.base.BasePersenter;
import com.mall.bean.HomeBean;
import com.mall.common.CommonSubscriber;
import com.mall.interfaces.home.ImodelHome;
import com.mall.model.HttpManager;
import com.mall.utils.RxUtils;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.functions.Function;

public class HomePresenter extends BasePersenter<ImodelHome.IView> implements ImodelHome.IPersenter {
    @Override
    public void getHomeData() {
        addSubscribe(HttpManager.getInstance().getMallApi().getHomeContent()
                .compose(RxUtils.<HomeBean>rxScheduler())
                //数据加工 把HomeBean转换成List<HomeListBean>
                .map(new Function<HomeBean, List<HomeBean.HomeListBean>>() {
                    @Override
                    public List<HomeBean.HomeListBean> apply(HomeBean homeBean) throws Exception {
                        List<HomeBean.HomeListBean> list = new ArrayList<>();
                        //第一个对象的封装 Banner
                        HomeBean.HomeListBean banner = new HomeBean.HomeListBean();
                        banner.currentType = HomeBean.ITEM_TYPE_BANNER;
                        banner.data = homeBean.getData().getBanner();
                        list.add(banner);
                        //导航的封装
                        HomeBean.HomeListBean tab = new HomeBean.HomeListBean();
                        tab.currentType = HomeBean.ITEM_TYPE_TAB;
                        tab.data = homeBean.getData().getChannel();
                        list.add(tab);
                        //封装带top边距的标题
                        HomeBean.HomeListBean title1 = new HomeBean.HomeListBean();
                        title1.currentType = HomeBean.ITEM_TYPE_TITLETOP;
                        title1.data = "品牌制造商直供";
                        list.add(title1);
                        //封装品牌制造商直供的列表数据
                        for (int i=0; i<homeBean.getData().getBrandList().size(); i++){
                            HomeBean.HomeListBean brand = new HomeBean.HomeListBean();
                            brand.currentType = HomeBean.ITEM_TYPE_BRAND;
                            brand.data = homeBean.getData().getBrandList().get(i);
                            list.add(brand);
                        }
                        //新品首发标题
                        HomeBean.HomeListBean title2 = new HomeBean.HomeListBean();
                        title2.currentType = HomeBean.ITEM_TYPE_TITLE;
                        title2.data = "周一周四·新品首发";
                        list.add(title2);
                        //新品首发数据封装
                        for (int i=0; i<homeBean.getData().getNewGoodsList().size(); i++){
                            HomeBean.HomeListBean brand = new HomeBean.HomeListBean();
                            brand.currentType = HomeBean.ITEM_TYPE_NEW;
                            brand.data = homeBean.getData().getNewGoodsList().get(i);
                            list.add(brand);
                        }
                        //人气推荐
                        HomeBean.HomeListBean title3 = new HomeBean.HomeListBean();
                        title3.currentType = HomeBean.ITEM_TYPE_TITLETOP;
                        title3.data = "人气推荐";
                        list.add(title3);
                        //人气推荐数据
                        for (int i=0; i<homeBean.getData().getHotGoodsList().size(); i++){
                            HomeBean.HomeListBean brand = new HomeBean.HomeListBean();
                            brand.currentType = HomeBean.ITEM_TYPE_HOT;
                            brand.data = homeBean.getData().getHotGoodsList().get(i);
                            list.add(brand);
                        }
                        //专题精选
                        HomeBean.HomeListBean title4 = new HomeBean.HomeListBean();
                        title4.currentType = HomeBean.ITEM_TYPE_TITLETOP;
                        title4.data = "专题精选";
                        list.add(title4);
                        //人气推荐数据
                        HomeBean.HomeListBean brand = new HomeBean.HomeListBean();
                        brand.currentType = HomeBean.ITEM_TYPE_TOPIC;
                        brand.data = homeBean.getData().getTopicList();
                        list.add(brand);
                        /*for (int i=0; i<homeBean.getData().getTopicList().size(); i++){
                            HomeBean.HomeListBean brand = new HomeBean.HomeListBean();
                            brand.currentType = HomeBean.ITEM_TYPE_TOPIC;
                            brand.data = homeBean.getData().getTopicList().get(i);
                            list.add(brand);
                        }*/
                        return list;
                    }
                })
                .subscribeWith(new CommonSubscriber<List<HomeBean.HomeListBean>>(mView) {
                    @Override
                    public void onNext(List<HomeBean.HomeListBean> list) {
                        mView.getHomeDataReturn(list);
                    }
                }));
    }
}
