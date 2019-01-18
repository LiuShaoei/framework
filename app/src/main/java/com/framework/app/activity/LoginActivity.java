package com.framework.app.activity;

import android.content.Intent;
import android.view.View;
import android.widget.Button;

import com.framework.app.R;
import com.framework.app.base.BaseActivity;
import com.framework.app.base.BasePresenter;
import com.framework.app.base.BaseView;
import com.framework.app.contract.LoginContract;
import com.framework.app.utils.ToastUtils;

import butterknife.BindView;
import butterknife.OnClick;
import xst.app.com.mylibrary.utils.navigationbar.NavigationBar;
import xst.app.com.mylibrary.utils.text.ClearEditText;
import xst.app.com.mylibrary.utils.text.EditTextInputHelper;


public class LoginActivity extends BaseActivity<LoginContract, BasePresenter<LoginContract>>
        implements LoginContract {

    @BindView(R.id.et_login_phone)
    ClearEditText mPhoneView;
    @BindView(R.id.et_login_password)
    ClearEditText mPasswordView;
    @BindView(R.id.btn_login_commit)
    Button mCommitView;


    private EditTextInputHelper mEditTextInputHelper;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void setTitleBar() {
        new NavigationBar.Builder(this).setTitle("登录").builder();
    }


    @Override
    protected void initView() {
        mEditTextInputHelper = new EditTextInputHelper(mCommitView, false);
        mEditTextInputHelper.addViews(mPhoneView, mPasswordView);
    }

    @Override
    protected void initData() {

    }


    @Override
    public BasePresenter<LoginContract> createPresenter() {
        return null;
    }

    @Override
    protected void onDestroy() {
        mEditTextInputHelper.removeViews();
        super.onDestroy();
    }

    @OnClick({R.id.tv_login_forget, R.id.tv_to_register, R.id.btn_login_commit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_login_forget:
                ToastUtils.show("忘记密码");
                break;
            case R.id.tv_to_register:
                startActivity(RegisterActivity.class);
                break;
            case R.id.btn_login_commit:
                Intent intent = new Intent(LoginActivity.this, MainActivity.class).
                        addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                break;
        }
    }

    @Override
    public BaseView getBaseView() {
        return null;
    }
}
