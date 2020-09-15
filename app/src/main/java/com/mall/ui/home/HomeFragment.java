package com.mall.ui.home;

import android.content.Intent;
import android.util.Log;
import android.view.View;

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
import com.mall.ui.cart.DetailGoodActivity;
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
                    case HomeBean.ITEM_TYPE_HOT:
                        return 2;
                    case HomeBean.ITEM_TYPE_BRAND:
                    case HomeBean.ITEM_TYPE_NEW:
                    case HomeBean.ITEM_TYPE_CATEGORY:
                        return 1;

                }
                return 0;
            }
        });
        recyclerview.setLayoutManager(gridLayoutManager);
        homeListAdapter.bindToRecyclerView(recyclerview);
        homeListAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                int type = list.get(position).currentType;
                Intent intent = new Intent();
                switch (type){
                    case HomeBean.ITEM_TYPE_BANNER:
                        break;
                    case HomeBean.ITEM_TYPE_BRAND:
                        break;
                    case HomeBean.ITEM_TYPE_HOT:
                        HomeBean.DataBean.HotGoodsListBean bean = (HomeBean.DataBean.HotGoodsListBean) list.get(position).data;
                        intent.putExtra("id",bean.getId());
                        intent.setClass(context, DetailGoodActivity.class);
                        startActivity(intent);
                        break;
                    case HomeBean.ITEM_TYPE_TITLE:
                        break;
                    case HomeBean.ITEM_TYPE_TITLETOP:
                        break;
                    case HomeBean.ITEM_TYPE_TOPIC:
                        break;
                    case HomeBean.ITEM_TYPE_CATEGORY:
                        break;
                }
            }
        });

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

    private static final String TAG = "HomeFragment";
    @Override
    public void getHomeDataReturn(List<HomeBean.HomeListBean> result) {
        Log.d(TAG, "getHomeDataReturn: "+result.size());
        list.addAll(result);
        homeListAdapter.notifyDataSetChanged();
}
}