package com.mall.interfaces.cart;

import com.mall.bean.AddCartInfoBean;
import com.mall.bean.CartBean;
import com.mall.bean.DeleteCartBean;
import com.mall.bean.GoodDetailBean;
import com.mall.interfaces.IBasePersenter;
import com.mall.interfaces.IBaseView;

public interface ICart {
    interface IView extends IBaseView{

        void getGoodDetailReturn(GoodDetailBean result);

        //添加商品信息返回
        void addCartInfoReturn(AddCartInfoBean result);

    }

    interface IPersenter extends IBasePersenter<IView>{
        //获取商品详情
        void getGoodDetail(int id);

        //添加到购物车
        void addCart(int goodsId,int number,int productId);
    }

    /**
     * 购物车接口
     */
    interface ICartView extends IBaseView{
        void getCartListReturn(CartBean result);

        void deleteCartListReturn(DeleteCartBean result);
    }

    interface ICartPersenter extends IBasePersenter<ICartView>{

        //获取购物车的数据
        void getCartList();

        //删除购物车数据
        void deleteCartList(String productIds);

    }
}
