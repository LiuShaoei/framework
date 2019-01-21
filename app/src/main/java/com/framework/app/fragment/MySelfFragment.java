package com.framework.app.fragment;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.framework.app.R;
import com.framework.app.activity.LoginActivity;
import com.framework.app.activity.RegisterActivity;
import com.framework.app.activity.WebViewActivity;
import com.framework.app.base.BaseFragment;
import com.framework.app.base.BasePresenter;
import com.framework.app.base.BaseView;

import butterknife.BindView;
import butterknife.OnClick;
import xst.app.com.mylibrary.utils.dialog.AlertDialog;

/**
 * Created by admin on 2017/12/18.
 */

public class MySelfFragment extends BaseFragment<BaseView, BasePresenter<BaseView>> {

    @BindView(R.id.login)
    Button login;
    @BindView(R.id.register)
    Button register;
    @BindView(R.id.test)
    Button test;
    private AlertDialog mAlertDialog;

    public static MySelfFragment getInStance() {
        MySelfFragment fragment = new MySelfFragment();
        return fragment;
    }

    @Override
    protected BasePresenter<BaseView> createPresenter() {
        return null;
    }

    @Override
    protected LinearLayout getLinearLayout() {
        return null;
    }


    @Override
    protected void initData() {

    }

    @Override
    public int getLayoutId() {
        return R.layout.myself_fragment;
    }


    @OnClick({R.id.login, R.id.register, R.id.test, R.id.web_view})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.web_view:
                startActivity(WebViewActivity.class);
                break;
            case R.id.login:
                startActivity(LoginActivity.class);
                break;
            case R.id.register:
                startActivity(RegisterActivity.class);
                break;
            case R.id.test:
                mAlertDialog = new AlertDialog.Builder(getActivity()).
                        setContentView(R.layout.dialog_custom).
                        setCancelable(false).
                        setWithAndHeight(0.8f).
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

                break;
        }
    }
}
