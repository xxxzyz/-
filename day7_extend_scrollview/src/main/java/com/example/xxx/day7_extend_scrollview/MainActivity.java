package com.example.xxx.day7_extend_scrollview;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * 经过我们的观察:发现这个标题,是随着往下滑动,颜色由浅变深,并且这个变化和咱们的图片的高度息息相关
 *
 */
public class MainActivity extends AppCompatActivity {

    private ImageView mIvDetail;
    private ExtendView mScrollView;
    private TextView mTvTitlebar;
    private RelativeLayout mLayoutTitle;
    private int mImageHeight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mIvDetail = findViewById(R.id.iv_detail);
        mScrollView =findViewById(R.id.scrollView);
        mTvTitlebar = findViewById(R.id.tv_titlebar);
        mLayoutTitle = findViewById(R.id.layout_title);

        //获取顶部的图片高度
        initListener();
        mScrollView.setScrollChangerListener(new ExtendView.ScrollChangerListener() {


            public void onChanger(ExtendView mScrollView, int l, int t, int oldl, int oldt) {
                //对T轴进行判断,就两种形态:1.消失没有   2.随着滑动,颜色越来越深
//                System.out.println("t"+t);
                if(t<=0){
                    //标题隐藏
                    mTvTitlebar.setVisibility(View.GONE);
                    //设置标题所在的背景颜色为透明
                    mLayoutTitle.setBackgroundColor(Color.argb(0,0,0,0));
                }
                else if (t>0 && t<mImageHeight){
                    //让标题显示出来
                    mTvTitlebar.setVisibility(View.VISIBLE);
                    //获取ScrollView想下滑动,图片消失部分的比例
                    float scale = (float) t / mImageHeight;
                    //根据比例,让标题的颜色由浅入深
                    float alpha = 255 * scale;
                    //设置标题的颜色及内容
                    mTvTitlebar.setText("1606B颜值担当是谁???-----王金洪");
                    mTvTitlebar.setTextColor(Color.argb((int)alpha,0,0,0));
                    //设置标题布局的颜色
                    mLayoutTitle.setBackgroundColor(Color.argb((int)alpha,255,255,255));

                }

            }
        });
    }

    //获取顶部的图片高度,设置ScrollView的滚动监听时,要使用这个参数
    private void initListener() {
/*        int height = mIvDetail.getHeight();
        System.out.println("height"+height);*/

        //获取控件的视图观察者通过观察者得到控件的宽高参数
        ViewTreeObserver viewTreeObserver = mIvDetail.getViewTreeObserver();
        //使用视图观察者设置监听,以便获取所观察控件的高度
        viewTreeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onGlobalLayout() {
                //卸磨杀驴,回调监听后,第一件事情就是移除监听,这种监听非常消耗系统资源
                mIvDetail.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                //得到控件的高度
                mImageHeight=mIvDetail.getHeight();
                System.out.println("mImageHeight"+mImageHeight);
            }
        });

    }


}
