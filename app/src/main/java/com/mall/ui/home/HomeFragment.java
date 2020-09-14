package com.mall.ui.home;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.mall.R;
import com.mall.base.BaseFragment;
import com.mall.bean.HomeBean;
import com.mall.interfaces.IBasePersenter;
import com.mall.interfaces.home.ImodelHome;
import com.mall.persenter.home.HomePresenter;
import com.mall.ui.home.adapter.HomeListAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class HomeFragment extends BaseFragment<ImodelHome.IPersenter> implements ImodelHome.IView {

    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;

    HomeListAdapter homeListAdapter;
    List<HomeBean.HomeListBean> list;

    private HomeViewModel homeViewModel;


    @Override
    protected int getLayout() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initView() {
        /*//创建viewmodel
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

            }
        });*/
        list = new ArrayList<>();
        homeListAdapter = new HomeListAdapter(list,context);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(context,2);
        homeListAdapter.setSpanSizeLookup(new BaseQuickAdapter.SpanSizeLookup() {
            @Override
            public int getSpanSize(GridLayoutManager gridLayoutManager, int i) {
                int type = list.get(i).currentType;
                switch (type){
                    case HomeBean.ITEM_TYPE_BANNER:
                    case HomeBean.ITEM_TYPE_TITLE:
                    case HomeBean.ITEM_TYPE_TITLETOP:
                    case HomeBean.ITEM_TYPE_TOPIC:
                        return 2;
                    case HomeBean.ITEM_TYPE_BRAND:
                    case HomeBean.ITEM_TYPE_HOT:
                    case HomeBean.ITEM_TYPE_NEW:
                    case HomeBean.ITEM_TYPE_CATEGORY:
                        return 1;

                }
                return 0;
            }
        });
        recyclerview.setLayoutManager(gridLayoutManager);
        homeListAdapter.bindToRecyclerView(recyclerview);

    }

    @Override
    protected ImodelHome.IPersenter initPersenter() {
        return new HomePresenter();
    }

    @Override
    protected void initData() {
        //homeViewModel.getHomeData();
        persenter.getHomeData();
    }

    @Override
    public void getHomeDataReturn(List<HomeBean.HomeListBean> result) {
        list.addAll(result);
        homeListAdapter.notifyDataSetChanged();
    }
}