package xst.app.com.mylibrary.utils.navigationbar;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by LiuZhaowei on 2018/12/7 0007.
 * 头部的基类
 */
public class AbsNavigationBar<P extends AbsNavigationBar.Builder.AbsNavigationParams> implements INavigationBar {

    private P mParams;
    private View mNavigationView;

    public AbsNavigationBar(P mParams) {
        this.mParams = mParams;
        createAndBingView();
    }

    public P getParams() {
        return mParams;
    }

    public void setVisible(int viewId, int visible) {
        View view = findViewById(viewId);
        view.setVisibility(visible);
    }

    public void setText(int viewId, String text) {
        TextView tv = findViewById(viewId);
        if (!TextUtils.isEmpty(text)) {
            tv.setVisibility(View.VISIBLE);
            tv.setText(text);
        } else {
            tv.setVisibility(View.GONE);
        }
    }

    public void setStatusBar(int viewId, int color) {
        LinearLayout linearLayout = findViewById(viewId);
        setBarColor((Activity) mParams.mContext, linearLayout, color);
    }

    public void setLeftIcon(int viewId, int leftRes) {
        ImageView img = findViewById(viewId);
        if (leftRes != 0) {
            img.setImageResource(leftRes);
        }
    }

    public void setOnClickListener(int viewId, View.OnClickListener listener) {

        View view = findViewById(viewId);
        if (view != null) {
            view.setOnClickListener(listener);
        }

    }

    public <T extends View> T findViewById(int viewId) {
        return (T) mNavigationView.findViewById(viewId);
    }


    /**
     * \
     * 绑定和创建view
     */
    private void createAndBingView() {
        // 1.创建view
        if (mParams.mParent == null) {
            //获取activity的跟布局
            ViewGroup activityRoot = (ViewGroup) ((Activity) (mParams.mContext)).getWindow().getDecorView();
            mParams.mParent = (ViewGroup) activityRoot.getChildAt(0);
        }
        if (mParams.mParent == null) {
            return;
        }
        mNavigationView = LayoutInflater.from(mParams.mContext).inflate(bindLayoutId(), mParams.mParent, false);//传false
        //2.添加
        mParams.mParent.addView(mNavigationView, 0);
        applyView();
    }


    @Override
    public int bindLayoutId() {
        return 0;
    }

    @Override
    public void applyView() {

    }

    /**
     * 设置状态栏的颜色
     *
     * @param activity
     * @param bar
     * @param color
     */
    private void setBarColor(Activity activity, LinearLayout bar, int color) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //获取手机状态栏高度
            int height = getBarHeight(activity);
            //动态的设置隐藏布局的高度
            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) bar.getLayoutParams();
            try {
                bar.setBackgroundColor(getParams().mContext.getResources().getColor(color));
            } catch (Exception e) {
                bar.setBackgroundColor(color);
            } finally {
                params.height = height;
                bar.setLayoutParams(params);
            }
        }
    }

    private int getBarHeight(Activity activity) {
        int result = 0;
        //获取状态栏高度的资源id
        int resourceId = activity.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = activity.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    public static abstract class Builder {

        public Builder(Context context, ViewGroup parent) {
        }

        public abstract AbsNavigationBar builder();

        public static class AbsNavigationParams {
            public Context mContext;
            public ViewGroup mParent;

            public AbsNavigationParams(Context context, ViewGroup parent) {
                this.mContext = context;
                this.mParent = parent;
            }

        }
    }


}
