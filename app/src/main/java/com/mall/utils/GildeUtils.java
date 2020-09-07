package com.mall.utils;

import android.content.Context;
import android.text.TextUtils;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

public class GildeUtils {


    public static void loadImg(Context context, String url, ImageView img){
        if(!TextUtils.isEmpty(url) && img != null){
            Glide.with(context).load(url).into(img);
        }else{

        }
    }

}
