package xst.app.com.mylibrary.utils.navigationbar;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;

import xst.app.com.mylibrary.R;


/**
 * Created by LiuZhaowei on 2018/12/7 0007.
 */
public class NavigationBar extends AbsNavigationBar<NavigationBar.Builder.DefaultNavigationParams> {

    public NavigationBar(NavigationBar.Builder.DefaultNavigationParams mParams) {
        super(mParams);
    }

    @Override
    public int bindLayoutId() {
        return R.layout.title_bar;
    }

    @Override
    public void applyView() {
        //绑定效果
        setText(R.id.center_title, getParams().mCenterTitle);
        if (!TextUtils.isEmpty(getParams().mRightText)) {
            setText(R.id.right_title, getParams().mRightText);
            setOnClickListener(R.id.right_title, getParams().mRightClickListener);
        }
        setText(R.id.left_title, getParams().mLeftText);
        if (!TextUtils.isEmpty(getParams().mLeftText)) {
            setVisible(R.id.left_image, View.GONE);
            setOnClickListener(R.id.left_title, getParams().mLeftClickListener);
        } else {
            setOnClickListener(R.id.left_image, getParams().mLeftClickListener);
        }
        if (getParams().mLeftIcon != 0) {
            setLeftIcon(R.id.left_image, getParams().mLeftIcon);
        }
        if (getParams().mStatusBarColor != 0) {
            setStatusBar(R.id.top_ll, getParams().mStatusBarColor);
        } else {
            setStatusBar(R.id.top_ll, Color.GRAY);
        }

        //左边写一个默认的, finish();
    }


    public static class Builder extends AbsNavigationBar.Builder {

        DefaultNavigationParams P;

        public Builder(Context context, ViewGroup parent) {
            super(context, parent);
            P = new DefaultNavigationParams(context, parent);
        }

        public Builder(Context context) {
            super(context, null);
            P = new DefaultNavigationParams(context, null);
        }


        @Override
        public NavigationBar builder() {
            NavigationBar navigationBar = new NavigationBar(P);
            return navigationBar;
        }

        // 1.设置效果
        //设置中间的title
        public NavigationBar.Builder setTitle(String centerTitle) {
            P.mCenterTitle = centerTitle;
            return this;
        }

        public NavigationBar.Builder setStatusColor(int color) {
            P.mStatusBarColor = color;
            return this;
        }

        //设置右边的文字
        public NavigationBar.Builder setRightText(String rightText) {
            P.mRightText = rightText;
            return this;
        }

        //设置左边的文字
        public NavigationBar.Builder setLefText(String leftText) {
            P.mLeftText = leftText;
            return this;
        }

        //设置右边的图片
        public NavigationBar.Builder setLeftIcon(int leftRes) {
            P.mLeftIcon = leftRes;
            return this;
        }

        //设置右边的点击事件
        public NavigationBar.Builder setRightClickListener(View.OnClickListener rightListener) {
            P.mRightClickListener = rightListener;
            return this;
        }

        public NavigationBar.Builder setLeftClickListener(View.OnClickListener leftClickListener) {
            P.mLeftClickListener = leftClickListener;
            return this;
        }

        public static class DefaultNavigationParams extends AbsNavigationParams {
            //2.所有效果
            public String mRightText;
            public int mLeftIcon;
            public String mCenterTitle;
            public View.OnClickListener mRightClickListener;
            public String mLeftText;
            public int mStatusBarColor;
            public View.OnClickListener mLeftClickListener = (v1) -> ((Activity) (mContext)).finish();

            public DefaultNavigationParams(Context context, ViewGroup parent) {
                super(context, parent);
            }
        }
    }
}
