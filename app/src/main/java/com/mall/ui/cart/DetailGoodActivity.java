package com.mall.ui.cart;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.WebView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.mall.R;
import com.mall.base.BaseActivity;
import com.mall.bean.GoodDetailBean;
import com.mall.bean.HomeBean;
import com.mall.interfaces.cart.ICart;
import com.mall.persenter.cart.CartPersenter;
import com.youth.banner.Banner;
import com.youth.banner.adapter.BannerImageAdapter;
import com.youth.banner.holder.BannerImageHolder;
import com.youth.banner.indicator.CircleIndicator;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailGoodActivity extends BaseActivity<ICart.IPersenter> implements ICart.IView {
    @BindView(R.id.layout_back)
    RelativeLayout layoutBack;
    @BindView(R.id.txt_title)
    TextView txtTitle;
    @BindView(R.id.banner)
    Banner banner;
    @BindView(R.id.txt_name)
    TextView txtName;
    @BindView(R.id.txt_des)
    TextView txtDes;
    @BindView(R.id.txt_price)
    TextView txtPrice;
    @BindView(R.id.txt_product)
    TextView txtProduct;
    @BindView(R.id.layout_norms)
    FrameLayout layoutNorms;
    @BindView(R.id.layout_comment)
    FrameLayout layoutComment;
    @BindView(R.id.layout_imgs)
    LinearLayout layoutImgs;
    @BindView(R.id.layout_parameter)
    LinearLayout layoutParameter;
    @BindView(R.id.webView)
    WebView webView;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;
    @BindView(R.id.layout_collect)
    RelativeLayout layoutCollect;
    @BindView(R.id.img_cart)
    ImageView imgCart;
    @BindView(R.id.layout_cart)
    RelativeLayout layoutCart;
    @BindView(R.id.txt_buy)
    TextView txtBuy;

    private String html = "<html>\n" +
            "            <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no\"/>\n" +
            "            <head>\n" +
            "                <style>\n" +
            "                    p{\n" +
            "                        margin:0px;\n" +
            "                    }\n" +
            "                </style>\n" +
            "            </head>\n" +
            "            <body>\n" +
            "                $\n" +
            "            </body>\n" +
            "        </html>";



    @Override
    protected int getLayout() {
        return R.layout.activity_good_detail;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected ICart.IPersenter initPersenter() {
        return new CartPersenter();
    }

    @Override
    protected void initData() {
        int id = getIntent().getIntExtra("id", 0);
        persenter.getGoodDetail(id);
    }

    @Override
    public void getGoodDetailReturn(GoodDetailBean result) {
        //banner刷新
        updateBanner(result.getData().getGallery());
        //评论
        if(result.getData().getComment().getCount() > 0){
            layoutComment.setVisibility(View.VISIBLE);
            updateComment(result.getData().getComment());
        }else{
            layoutComment.setVisibility(View.GONE);
        }
        //设置参数
        updateParameter(result.getData().getAttribute());
        //详情信息的展示
        updateDetailInfo(result.getData().getInfo());
    }



    /**
     * 刷新banner
     */
    private void updateBanner(List<GoodDetailBean.DataBeanX.GalleryBean> gallery){
        if(banner.getTag() == null || (int)banner.getTag() == 0){
            List<String> imgs = new ArrayList<>();
            for (GoodDetailBean.DataBeanX.GalleryBean item : gallery) {
                imgs.add(item.getImg_url());
            }
            banner.setTag(1);
            banner.setAdapter(new BannerImageAdapter<String>(imgs) {


                @Override
                public void onBindView(BannerImageHolder holder, String data, int position, int size) {
                    Glide.with(holder.itemView)
                            .load(data)
                            .apply(RequestOptions.bitmapTransform(new RoundedCorners(30)))
                            .into(holder.imageView);
                }

            })
                    //.addBannerLifecycleObserver(this)
                    .setIndicator(new CircleIndicator(this));
        }
    }

    /**
     * 刷新评论
     * @param commentBean
     */
    private void updateComment(GoodDetailBean.DataBeanX.CommentBean commentBean){

    }

    /**
     * 刷新参数的布局
     * @param attributeBean
     */
    private void updateParameter(List<GoodDetailBean.DataBeanX.AttributeBean> attributeBean){
        layoutParameter.removeAllViews(); //清空
        for(GoodDetailBean.DataBeanX.AttributeBean item:attributeBean){
            View view = LayoutInflater.from(this).inflate(R.layout.layout_parameter,null);
            layoutParameter.addView(view);
        }
    }

    private void updateDetailInfo(GoodDetailBean.DataBeanX.InfoBean infoBean) {
        if(!TextUtils.isEmpty(infoBean.getGoods_desc())){
            String h5 = infoBean.getGoods_desc();
            html = html.replace("$",h5);
            webView.loadData(html,"text/html","utf-8");
        }
    }
}
