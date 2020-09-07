package com.mall.interfaces;

/**
 * P层接口基类 用来约束管理进来的V层接口
 * @param <T>
 */
public interface IBasePersenter<T extends IBaseView> {

    //V层接口的关联
    void attachView(T view);

    //V层接口的取消关联
    void detachView();

}
