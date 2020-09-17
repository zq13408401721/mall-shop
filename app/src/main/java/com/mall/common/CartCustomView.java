package com.mall.common;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.mall.R;

public class CartCustomView extends LinearLayout implements View.OnClickListener {

    private Context context;
    TextView txtSubtract;
    TextView txtValue;
    TextView txtAdd;

    private IClick cb;

    private int value=1;

    private int min=1;
    private int max=9999;


    public CartCustomView(Context context) {
        super(context);
        this.context = context;
    }

    public CartCustomView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
    }

    public CartCustomView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
    }

    public CartCustomView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        this.context = context;
    }

    /**
     * 初始化界面由外部控制
     */
    public void initView(){
        txtSubtract = findViewById(R.id.txt_subtract);
        txtValue = findViewById(R.id.txt_value);
        txtAdd = findViewById(R.id.txt_add);

        if(txtSubtract != null && txtValue != null && txtAdd != null){
            txtSubtract.setOnClickListener(this);
            txtAdd.setOnClickListener(this);
        }else{
            Toast.makeText(context, "初始化调用错误", Toast.LENGTH_SHORT).show();
        }

    }

    /**
     * 初始化
     * @param min
     * @param max
     */
    public void initView(int min,int max){
        this.min = min;
        this.max = max;
        initView();
    }

    /**
     * 设置值
     * @param num
     */
    public void setValue(int num){
        this.value = num;
        txtValue.setText(this.value);
    }

    /**
     * 设置接口回调
     * @param cb
     */
    public void setOnClickListener(IClick cb){
        this.cb = cb;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.txt_add:
                value++;
                if(value > max){
                    value = max;
                }else{
                    if(cb != null){
                        cb.clickCB(value);
                    }
                }
                txtValue.setText(String.valueOf(value));
                break;
            case R.id.txt_subtract:
                value--;
                if(value < 1){
                    value = 1;
                }else{
                    txtValue.setText(String.valueOf(value));
                    if(cb != null){
                        cb.clickCB(value);
                    }
                }
                break;
        }
    }


    public interface IClick{
        void clickCB(int value);
    }


}
