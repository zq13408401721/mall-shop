package com.mall.ui.home;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.mall.R;
import com.mall.base.BaseFragment;
import com.mall.bean.HomeBean;
import com.mall.interfaces.IBasePersenter;

import butterknife.BindView;

public class HomeFragment extends BaseFragment {

    @BindView(R.id.text_home)
    TextView textHome;
    private HomeViewModel homeViewModel;


    @Override
    protected int getLayout() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initView() {
        //创建viewmodel
        homeViewModel = ViewModelProviders.of(this).get(HomeViewModel.class);

        //监听viewmodel中的数据对象的变化
        //new Observer匿名的内部类 --->类似回调接口获取数据
        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                //textHome.setText(s);
            }
        });

        homeViewModel.getHomeBean().observe(getViewLifecycleOwner(), new Observer<HomeBean>() {
            @Override
            public void onChanged(HomeBean homeBean) {
                textHome.setText(homeBean.getData().getAdvertiseList().get(0).getName());
            }
        });

        homeViewModel.getHomeData();
    }

    @Override
    protected IBasePersenter initPersenter() {
        return null;
    }

    @Override
    protected void initData() {
        homeViewModel.getHomeData();
    }
}