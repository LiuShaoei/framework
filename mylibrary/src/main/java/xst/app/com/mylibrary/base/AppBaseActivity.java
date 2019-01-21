package xst.app.com.mylibrary.base;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;


/**
 * time   : 2018/10/18
 * desc   : Activity基类
 */
public abstract class AppBaseActivity extends android.support.v7.app.AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getLayoutId() > 0) {
            setContentView(getLayoutId());
        }
        init();
    }

    public void init() {
        initView();
        initData();
        setTitleBar();
    }

    //引入布局
    protected abstract int getLayoutId();

    //设置头部
    protected abstract void setTitleBar();

    //初始化控件
    protected abstract void initView();

    //初始化数据
    protected abstract void initData();

    @Override
    public void finish() {
        // 隐藏软键盘，避免软键盘引发的内存泄露
        // KeyboardUtils.hideKeyboard(getCurrentFocus());
        super.finish();
    }

    /**
     * 跳转到其他Activity
     *
     * @param cls 目标Activity的Class
     */
    public void startActivity(Class<? extends Activity> cls) {
        startActivity(cls, null);
    }

    public void startActivity(Class<? extends Activity> cls, Bundle bundle) {
        Intent intent = new Intent(this, cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

    public void startActivityForResult(Class<? extends Activity> cls, int requestCode) {
        startActivityForResult(cls, null, requestCode);
    }

    public void startActivityForResult(Class<? extends Activity> cls, Bundle bundle, int requestCode) {
        Intent intent = new Intent(this, cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivityForResult(intent, requestCode);
    }

    /**
     * 延迟执行某个任务
     *
     * @param action Runnable对象
     */
    public boolean post(Runnable action) {
        return getWindow().getDecorView().post(action);
    }

    /**
     * 延迟某个时间执行某个任务
     *
     * @param action      Runnable对象
     * @param delayMillis 延迟的时间
     */
    public boolean postDelayed(Runnable action, long delayMillis) {
        return getWindow().getDecorView().postDelayed(action, delayMillis);
    }

    /**
     * 删除某个延迟任务
     *
     * @param action Runnable对象
     */
    public boolean removeCallbacks(Runnable action) {
        if (getWindow().getDecorView() != null) {
            return getWindow().getDecorView().removeCallbacks(action);
        } else {
            return true;
        }
    }
}