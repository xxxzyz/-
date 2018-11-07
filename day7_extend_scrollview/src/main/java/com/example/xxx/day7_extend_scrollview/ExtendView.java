package com.example.xxx.day7_extend_scrollview;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ScrollView;

/**
 * date:2018/11/6
 * author:薛鑫欣(吧啦吧啦)
 * function:
 */
public class ExtendView extends ScrollView {
    public ExtendView(Context context) {
        this(context,null);
    }

    public ExtendView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public ExtendView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        if(mScrollChangerListener!=null){
            mScrollChangerListener.onChanger(this,l,t,oldl,oldt);
        }
    }

    //这就是ScrollView的滑动监听,但是可惜谷歌没有把他暴露出去,让我们能在外界使用,所以我们要用接口
    private ScrollChangerListener mScrollChangerListener;
    public interface ScrollChangerListener {
        void onChanger(ExtendView extendView,int l,int t,int oldl,int oldt);
    }

    public void setScrollChangerListener(ScrollChangerListener scrollChangerListener) {
        mScrollChangerListener = scrollChangerListener;
    }
}
