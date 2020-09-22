package com.mall.ui.cart;

import android.graphics.drawable.BitmapDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.mall.R;
import com.mall.base.BaseActivity;
import com.mall.bean.AdressBean;
import com.mall.interfaces.cart.ICart;
import com.mall.persenter.cart.AdressPersenter;
import com.mall.utils.SystemUtils;
import com.weigan.loopview.LoopView;
import com.weigan.loopview.OnItemSelectedListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

public class AdressActivity extends BaseActivity<ICart.IAdressPersenter> implements ICart.IAdressView {
    @BindView(R.id.txt_adress)
    TextView txtAdress;
    @BindView(R.id.layout_adress_select)
    LinearLayout layoutAdressSelect;
    private PopupWindow mPopWindow;
    @BindView(R.id.layout_adress)
    LinearLayout layoutAdress;

    private Map<Integer, List<AdressBean.DataBean>> addressMap;

    private LoopView province,city,area;
    private TextView txtProvince,txtCity,txtArea;

    private int curProvinceId,curCityId,curAreaId; //当前省市区的ID

    @Override
    protected int getLayout() {
        return R.layout.activity_adress_add;
    }

    @Override
    protected void initView() {
        addressMap = new HashMap<>();
    }

    @Override
    protected ICart.IAdressPersenter initPersenter() {
        return new AdressPersenter();
    }

    @Override
    protected void initData() {
        //加载省份数据
        persenter.getAdressById(1);
    }


    @OnClick({R.id.layout_adress_select})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.layout_adress_select:
                openAdressSelect();
                break;
        }
    }

    private void openAdressSelect(){
        if(mPopWindow != null && mPopWindow.isShowing()){

        }else{
            View contentView = LayoutInflater.from(this).inflate(R.layout.layout_popwindow_adress, null);
            int height = SystemUtils.dp2px(this,250);
            mPopWindow = new PopupWindow(contentView, LinearLayout.LayoutParams.MATCH_PARENT,height);
            mPopWindow.setBackgroundDrawable(new BitmapDrawable());
            mPopWindow.setFocusable(false);
            mPopWindow.setOutsideTouchable(false);
            mPopWindow.setContentView(contentView);
            contentView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
            province = contentView.findViewById(R.id.adress_province);
            city = contentView.findViewById(R.id.adress_city);
            area = contentView.findViewById(R.id.adress_area);
            txtProvince = contentView.findViewById(R.id.txt_province);
            txtCity = contentView.findViewById(R.id.txt_city);
            txtArea = contentView.findViewById(R.id.txt_area);
            TextView txt_submit = contentView.findViewById(R.id.txt_submit);
            txt_submit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mPopWindow.dismiss();
                    mPopWindow = null;
                }
            });
            mPopWindow.showAtLocation(layoutAdress,Gravity.BOTTOM,0,0);

            //省份
            province.setListener(new OnItemSelectedListener() {
                @Override
                public void onItemSelected(int index) {
                    List<AdressBean.DataBean> proviceList = addressMap.get(1); //key为1固定为省的数据
                    AdressBean.DataBean dataBean = proviceList.get(index);
                    curProvinceId = dataBean.getId();

                    List<String> items = new ArrayList<>();
                    items.add("请选择");
                    txtProvince.setText(dataBean.getName());
                    //判断当前的缓存中是否有对应的城市数据
                    List<AdressBean.DataBean> curCitys = addressMap.get(curProvinceId);
                    if(curCitys != null && curCitys.size() > 0){
                        items = getAdressStrings(curCitys);
                        city.setItems(items);
                    }else{
                        persenter.getAdressById(curProvinceId);
                        txtCity.setText("请选择城市");
                        txtArea.setText("请选中区域");
                    }
                }
            });

            city.setListener(new OnItemSelectedListener() {
                @Override
                public void onItemSelected(int index) {
                    List<AdressBean.DataBean> cityList = addressMap.get(curProvinceId); //key省份id
                    AdressBean.DataBean dataBean = cityList.get(index);
                    curCityId = dataBean.getId();
                    persenter.getAdressById(curCityId);
                    area.setItems(new ArrayList<>());
                    txtCity.setText(dataBean.getName());
                    txtArea.setText("请选中区域");
                }
            });

            area.setListener(new OnItemSelectedListener() {
                @Override
                public void onItemSelected(int index) {
                    List<AdressBean.DataBean> areaList = addressMap.get(curCityId); //key省份id
                    AdressBean.DataBean dataBean = areaList.get(index);
                    curAreaId = dataBean.getId();
                    txtArea.setText(dataBean.getName());
                }
            });

            //初始化省份的数据
            List<AdressBean.DataBean> pList = addressMap.get(1);
            if(pList == null) return;
            List<String> adresses = getAdressStrings(pList);
            if(pList == null || adresses.size() == 0){
                persenter.getAdressById(1);
            }else{
                province.setItems(adresses);
                curProvinceId = pList.get(0).getId();
                txtProvince.setText(adresses.get(0));
            }

        }
    }

    /**
     * 请求数据回来的方法
     * @param result
     */
    @Override
    public void getAdressByIdReturn(AdressBean result) {
        List<AdressBean.DataBean> list = null;
        int type = 0;
        for(AdressBean.DataBean item:result.getData()){
            int key = item.getParent_id();
            list = addressMap.get(key);
            if(list == null){
                list = new ArrayList<>();
                addressMap.put(key,list);
            }
            boolean bool = hasList(item.getId(),list);
            if(!bool) list.add(item);
            if(type == 0){
                type = item.getType();
            }
        }
        if(list == null) return;
        List<String> adresses = getAdressStrings(list);
        if(type == 1){
            //刷新省的数据
            if(province != null){
                curProvinceId = list.get(0).getId();
                txtProvince.setText(list.get(0).getName());
                province.setItems(adresses);
                province.setCurrentPosition(0);
            }
        }else if(type == 2){
            //刷新市的数据
            if(city != null){
                curCityId = list.get(0).getId();
                txtCity.setText(list.get(0).getName());
                city.setItems(adresses);
                city.setCurrentPosition(0);
            }
        }else{
            //区
            if(area != null){
                curAreaId = list.get(0).getId();
                txtArea.setText(list.get(0).getName());
                area.setItems(adresses);
                area.setCurrentPosition(0);
            }
        }
    }

    /**
     * 判断当前的地址列表中是否有这个地址
     * @param id
     * @param list
     * @return
     */
    private boolean hasList(int id, List<AdressBean.DataBean> list){
        boolean bool = false;
        for(AdressBean.DataBean item:list){
            if(item.getId() == id){
                bool = true;
                break;
            }
        }
        return bool;
    }

    /**
     * 提取省市区的名字
     * @param list
     * @return
     */
    private List<String> getAdressStrings(List<AdressBean.DataBean> list){
        List<String> adresses = new ArrayList<>();
        for(AdressBean.DataBean item:list){
            adresses.add(item.getName());
        }
        return adresses;
    }

    /**
     * 通过id获取当前数据中的对象
     * @param id
     * @param list
     * @return
     */
    private AdressBean.DataBean getDataBeanById(int id, List<AdressBean.DataBean> list){
        for(AdressBean.DataBean item:list){
            if(item.getId() == id) return item;
        }
        return null;
    }
}
