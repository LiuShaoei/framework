package com.framework.app.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.framework.app.MyApp;
import com.framework.app.R;
import com.framework.app.net.NetChangeObserver;
import com.framework.app.net.NetStateReceiver;
import com.framework.app.net.NetUtils;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import xst.app.com.mylibrary.utils.dialog.AlertDialog;

/**
 * Created by admin on 2017/12/19.
 */

public abstract class BaseActivity<V, T extends BasePresenter<V>> extends UiActivity {

    protected T mPresenter;
    private AlertDialog mAlertDialog;
    /**
     * 网络观察者
     */
    protected NetChangeObserver mNetChangeObserver = null;
    private CompositeDisposable mCompositeDisposable;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setBackgroundDrawable(null);
        MyApp.getInstance().activityList.add(this);

//        if (getTopView() == null) {
//            QMUIStatusBarHelper.translucent(this);
//            QMUIStatusBarHelper.setStatusBarLightMode(this);
//        }
        if (mCompositeDisposable == null) {
            mCompositeDisposable = new CompositeDisposable();
        }
        // 网络改变的一个回掉类
        mNetChangeObserver = new NetChangeObserver() {
            @Override
            public void onNetConnected(NetUtils.NetType type) {
                onNetworkConnected(type);
            }

            @Override
            public void onNetDisConnect() {
                onNetworkDisConnected();
            }
        };
    }

    @Override
    public void init() {
        mPresenter = createPresenter();
        if (mPresenter != null) {
            mPresenter.attachView((V) this);
        }
        super.init();
    }

    public abstract T createPresenter();

    @Override
    protected void onResume() {
        super.onResume();
        //开启广播去监听 网络 改变事件
        NetStateReceiver.registerObserver(mNetChangeObserver);
    }

    @Override
    protected void onPause() {
        super.onPause();
        NetStateReceiver.removeRegisterObserver(mNetChangeObserver);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (mPresenter != null) {
            mPresenter.detachView();
        }
        mPresenter = null;
        MyApp.getInstance().activityList.remove(this);
    }

    /**
     * 网络连接状态
     *
     * @param type 网络状态
     */
    protected void onNetworkConnected(NetUtils.NetType type) {
        if (mAlertDialog != null) {
            mAlertDialog.dismiss();
        }

    }

    /**
     * 网络断开的时候调用
     */
    protected void onNetworkDisConnected() {
        mAlertDialog = new AlertDialog.Builder(this).
                setContentView(R.layout.dialog_custom).
                setCancelable(false).
                setText(R.id.title, "网络提醒").
                setText(R.id.message, "网络连接失败,请检查网络").
                setText(R.id.negative, "取消").
                setText(R.id.positive, "确认").
                show();
        mAlertDialog.setOnClickListener(R.id.negative, (v -> {
            mAlertDialog.dismiss();
        }));
        mAlertDialog.setOnClickListener(R.id.positive, (v -> {
            //设置网络
            Intent intent = new Intent("android.settings.WIRELESS_SETTINGS");
            startActivity(intent);
        }));

    }

    public void addDisposed(Disposable disposable) {
        if (mCompositeDisposable != null) {
            mCompositeDisposable.add(disposable);
        }
    }

    @Override
    public void startActivity(Intent intent) {
        super.startActivity(intent);
        enterActivityAnimation();
    }

    @Override
    public void finish() {
        super.finish();
        exitActivityAnimation();
    }

    public void enterActivityAnimation() {
        overridePendingTransition(R.anim.activity_slide_from_right, R.anim.activity_slide_to_left);
    }

    public void exitActivityAnimation() {
        overridePendingTransition(R.anim.activity_slide_from_left, R.anim.activity_slide_to_right);
    }

}
