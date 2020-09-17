package com.mall.ui.cart.adapter;

import android.content.Context;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.mall.R;
import com.mall.base.BaseAdapter;
import com.mall.bean.CartBean;
import com.mall.common.CartCustomView;

import java.util.List;

public class CartListAdapter extends BaseAdapter {

    public boolean isEditor; //是否是编辑状态

    private CheckBoxClick click;

    public CartListAdapter(Context context, List data) {
        super(context, data);
    }

    @Override
    protected int getLayout() {
        return R.layout.layout_cartlist_item;
    }

    @Override
    protected void bindData(BaseViewHolder vh, Object data) {
        CartBean.DataBean.CartListBean bean = (CartBean.DataBean.CartListBean) data;

        CheckBox checkBox = (CheckBox) vh.getViewById(R.id.checkbox_select);
        TextView txtName = (TextView) vh.getViewById(R.id.txt_name);
        TextView txtNumber = (TextView) vh.getViewById(R.id.txt_number);
        TextView txtPrice = (TextView) vh.getViewById(R.id.txt_price);
        TextView txtSelect = (TextView) vh.getViewById(R.id.txt_select);
        CartCustomView cartCustomView = (CartCustomView) vh.getViewById(R.id.view_number);

        //通过编辑状态控制界面组件
        if(isEditor){
            txtName.setVisibility(View.GONE);
            txtNumber.setVisibility(View.GONE);
            txtSelect.setVisibility(View.VISIBLE);
            cartCustomView.setVisibility(View.VISIBLE);
        }else{
            txtName.setVisibility(View.VISIBLE);
            txtNumber.setVisibility(View.VISIBLE);
            txtSelect.setVisibility(View.GONE);
            cartCustomView.setVisibility(View.GONE);
        }
        txtName.setText(bean.getGoods_name());
        txtNumber.setText("X"+bean.getNumber());
        txtPrice.setText("￥"+bean.getRetail_price());
        cartCustomView.setValue(bean.getNumber());
        cartCustomView.setOnClickListener(new CartCustomView.IClick() {
            @Override
            public void clickCB(int value) {
                bean.setNumber(value);
            }
        });
        checkBox.setChecked(bean.select);
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                bean.select = isChecked;
                if(click != null){
                    click.checkChange();
                }
            }
        });


    }

    public void setOnItemCheckBoxClickListener(CheckBoxClick click){
        this.click = click;
    }


    public interface CheckBoxClick{
        void checkChange();
    }
}
