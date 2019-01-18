package com.framework.app.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.framework.app.MyApp;
import com.framework.app.R;
import com.framework.app.net.NetChangeObserver;
import com.framework.app.net.NetStateReceiver;
import com.framework.app.net.NetUtils;
import com.framework.app.utils.DialogUtils;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import xst.app.com.mylibrary.base.AppBaseActivity;

/**
 * Created by admin on 2017/12/19.
 */

public abstract class BaseActivity<V, T extends BasePresenter<V>> extends AppBaseActivity {
    private Unbinder unbinder;
    protected T mPresenter;
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
        unbinder = ButterKnife.bind(this);
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
        if (unbinder != null) {
            unbinder.unbind();
        }
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

    }

    /**
     * 网络断开的时候调用
     */
    protected void onNetworkDisConnected() {

        DialogUtils.getInstance(new DialogUtils.Builder().setTitle("网络提醒")
                .setMessage("网络连接失败,请检查网络")
                .setonClickButtonListener(new DialogUtils.onClickButtonListener() {
                    @Override
                    public void clickNegative() {

                    }

                    @Override
                    public void clickPositive() {
                        //设置网络
                        Intent intent = new Intent("android.settings.WIRELESS_SETTINGS");
                        startActivity(intent);
                    }
                })).showDialog(getFragmentManager());
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
        overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
    }

    public void exitActivityAnimation() {
        overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
    }

}
