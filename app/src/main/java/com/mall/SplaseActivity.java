package com.mall;

import android.content.Intent;
import android.util.Log;

import com.mall.base.BaseActivity;
import com.mall.base.BasePermissionActivity;
import com.mall.interfaces.IBasePersenter;

public class SplaseActivity extends BasePermissionActivity {
    @Override
    protected int getLayout() {
        return R.layout.activity_splase;
    }

    @Override
    protected void initView() {
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
        Log.i("tag","init");
    }

    @Override
    protected IBasePersenter initPersenter() {
        return null;
    }

    @Override
    protected void initData() {

    }
}
