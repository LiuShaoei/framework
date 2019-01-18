package com.framework.app.activity;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.framework.app.R;
import com.framework.app.base.BaseActivity;
import com.framework.app.base.BaseView;
import com.framework.app.contract.RegisterContract;
import com.framework.app.presenter.RegisterPresenter;

import butterknife.BindView;
import butterknife.OnClick;
import xst.app.com.mylibrary.utils.CountdownView;
import com.framework.app.utils.ToastUtils;
import xst.app.com.mylibrary.utils.text.EditTextInputHelper;

/**
 * time   : 2018/10/18
 * desc   : 注册界面
 */
public class RegisterActivity extends BaseActivity<RegisterContract, RegisterPresenter>
        implements RegisterContract {

    @BindView(R.id.cv_register_countdown)
    CountdownView mCountdownView;
    @BindView(R.id.et_register_phone)
    EditText mPhoneView;
    @BindView(R.id.et_register_code)
    EditText mCodeView;
    @BindView(R.id.et_register_password1)
    EditText mPasswordView1;
    @BindView(R.id.et_register_password2)
    EditText mPasswordView2;
    @BindView(R.id.btn_register_commit)
    Button mCommitView;
    private EditTextInputHelper mEditTextInputHelper;
    private Context mContext;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_register;
    }

    @Override
    protected void setTitleBar() {

    }

    @Override
    protected void initView() {

        mEditTextInputHelper = new EditTextInputHelper(mCommitView, false);
        mEditTextInputHelper.addViews(mPhoneView, mCodeView, mPasswordView1, mPasswordView2);
    }

    @Override
    protected void initData() {
        mContext = RegisterActivity.this;
    }

    @Override
    public RegisterPresenter createPresenter() {
        return null;
    }

    @Override
    protected void onDestroy() {
        mEditTextInputHelper.removeViews();
        mContext = null;
        super.onDestroy();
    }

    @Override
    public BaseView getBaseView() {
        return null;
    }


    @OnClick({R.id.cv_register_countdown, R.id.btn_register_commit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.cv_register_countdown:
                //获取验证码
                if (mPhoneView.getText().toString().length() != 11) {
                    // 重置验证码倒计时控件
                    mCountdownView.resetState();
                    ToastUtils.show(getResources().getString(R.string.phone_input_error));
                    return;
                }

                ToastUtils.show(getResources().getString(R.string.countdown_code_send_succeed));
                break;
            case R.id.btn_register_commit:
                if (mPhoneView.getText().toString().length() != 11) {
                    ToastUtils.show(getResources().getString(R.string.phone_input_error));
                    return;
                }

                if (!mPasswordView1.getText().toString().equals(mPasswordView2.getText().toString())) {
                    ToastUtils.show(getResources().getString(R.string.two_password_input_error));
                    return;
                }
                break;
        }
    }
}