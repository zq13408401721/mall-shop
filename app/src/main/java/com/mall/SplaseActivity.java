package com.mall;

import android.content.Intent;
import android.util.Log;

import com.mall.base.BaseActivity;
import com.mall.base.BasePermissionActivity;
import com.mall.interfaces.IBasePersenter;
import com.mall.utils.SpUtils;

public class SplaseActivity extends BasePermissionActivity {
    @Override
    protected int getLayout() {
        return R.layout.activity_splase;
    }

    @Override
    protected void initView() {
        SpUtils.getInstance().setValue("token","eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyX2lkIjoiN2I5NjU2NTYtNzBlYi00NzI2LWI0YTctYzUyMzY2ODYxNDg1IiwiaWF0IjoxNjAwMzMyOTM4fQ.9AFcOzuodY7IU2qtR2FVMwX0Aa9W42gRh8MezeS0abk");
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
