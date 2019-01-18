package com.framework.app.activity;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.framework.app.R;

import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import xst.app.com.mylibrary.utils.dialog.AlertDialog;

public class TestActivity extends AppCompatActivity {
    private Unbinder mUnbinder;
    private AlertDialog mDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_activity);
        mUnbinder = ButterKnife.bind(this);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mUnbinder != null) {
            mUnbinder.unbind();
        }
    }

    @OnClick({R.id.dialog_test, R.id.letter_test})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.dialog_test:
                mDialog = new AlertDialog.Builder(this).setContentView(R.layout.test_dialog).
                        setCancelable(false).setText(R.id.test_tv, "测试").show();
                mDialog.setOnClickListener(R.id.test_tv, (v) ->
                    mDialog.dismiss()
                );
                break;
            case R.id.letter_test:
                break;
        }
    }
}
