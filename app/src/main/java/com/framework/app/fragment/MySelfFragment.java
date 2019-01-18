package com.framework.app.fragment;

import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.framework.app.R;
import com.framework.app.activity.LoginActivity;
import com.framework.app.activity.RegisterActivity;
import com.framework.app.activity.TestActivity;
import com.framework.app.base.BaseFragment;
import com.framework.app.base.BasePresenter;
import com.framework.app.base.BaseView;

import butterknife.BindView;
import butterknife.OnClick;

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


    @OnClick({R.id.login, R.id.register, R.id.test})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.login:
                startActivity(LoginActivity.class);
                break;
            case R.id.register:
                startActivity(RegisterActivity.class);
                break;
            case R.id.test:
                startActivity(TestActivity.class);
                break;
        }
    }
}
