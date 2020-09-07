package com.mall.base;

import android.Manifest;
import android.app.AlertDialog;
import android.app.AppOpsManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Process;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.bumptech.glide.load.resource.gif.GifDrawableEncoder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cn.jzvd.BuildConfig;

/**
 * 权限的基类
 */
public abstract class BasePermissionActivity extends BaseActivity {

    private static final int PERMISSION_SET_CODE = 1000; //权限设置

    private String[] permissionArr;
    private PermisssionCB cb;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //通过系统版本判断当前是否需要动态权限的添加
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            //动态权限申请
            String[] storages = {Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE};
            setPermission(storages);
        }
    }

    protected void setPermission(String[] permissions){
        permissionArr = permissions;
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            setPermission();
        }
    }


    protected void setPermission(String[] permissions,PermisssionCB cb){
        this.cb = cb;
        setPermission(permissions);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    protected void setPermission() {
        int checkCallPhonePermisssion = checkOpsPermission();
        if (checkCallPhonePermisssion != PackageManager.PERMISSION_GRANTED) {
            if (permissionArr != null) {
                //判断当前需要设置的权限需要弹框提示
                boolean permission_dialog = false;
                List<String> list = new ArrayList<>();
                for (int i = 0; i < permissionArr.length; i++) {
                    AppOpsManager appOpsManager = (AppOpsManager) this.getSystemService(Context.APP_OPS_SERVICE);
                    String opsName = null;
                    opsName = AppOpsManager.permissionToOp(permissionArr[i]);
                    if (opsName == null) {
                        break;
                    }
                    int opsMode = appOpsManager.checkOpNoThrow(opsName, Process.myUid(), this.getPackageName());
                    //权限检查结果
                    if (opsMode == AppOpsManager.MODE_ALLOWED) {
                        break;
                    } else {
                        if (!permission_dialog) {
                            permission_dialog = ActivityCompat.shouldShowRequestPermissionRationale(this, permissionArr[i]);
                        }
                        list.add(permissionArr[i]);
                    }
                }
                String[] stringArray = list.toArray(new String[list.size()]);
                //判断是否需要弹框提示
                if (permission_dialog) {
                    new AlertDialog.Builder(this)
                            .setTitle("提示")
                            .setMessage("应用需要开启拍照的权限，是否继续？")
                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    ActivityCompat.requestPermissions(BasePermissionActivity.this, stringArray, PERMISSION_SET_CODE);
                                }
                            })
                            .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    //取消了权限请求
                                    if(cb != null) {
                                        cb.callback(false);
                                    }
                                }
                            }).show();
                } else {
                    ActivityCompat.requestPermissions(this, stringArray, PERMISSION_SET_CODE);
                }

            } else {
                if(cb != null){
                    cb.callback(false);
                }
            }
        }
    }

    //权限检测
    private int checkOpsPermission(){
        //6.0 api 23
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            try {
                //检查权限的结果
                boolean result = false;
                //检查当前权限组
                for (int i = 0; i < permissionArr.length; i++) {
                    AppOpsManager appOpsManager = (AppOpsManager) this.getSystemService(Context.APP_OPS_SERVICE);
                    String opsName = null;
                    opsName = AppOpsManager.permissionToOp(permissionArr[i]);
                    if (opsName == null) {
                        return -1;
                    }
                    int opsMode = appOpsManager.checkOpNoThrow(opsName, Process.myUid(), this.getPackageName());
                    //权限检查结果
                    result = opsMode == AppOpsManager.MODE_ALLOWED;
                    if (!result) {
                        break;
                    }
                }
                return result ? 0 : -1;
            } catch (Exception ex) {
                return -1;
            }
        }
        return -1;
    }

    /**
     * 权限申请结果
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        //super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case PERMISSION_SET_CODE:
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    if(cb != null){
                        cb.callback(true);
                    }
                }else{
                    if(cb != null) cb.callback(false);
                }
                break;
        }
    }

    /**
     * 权限设置回调接口
     */
    protected interface PermisssionCB {
        void callback(boolean bool);
    }
}
