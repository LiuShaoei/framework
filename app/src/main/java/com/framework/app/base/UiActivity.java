package com.framework.app.base;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import xst.app.com.mylibrary.base.AppBaseActivity;

/**
 * Created by LiuZhaowei on 2019/1/20 0020.
 */
public abstract class UiActivity extends AppBaseActivity {
    private Unbinder unbinder;

    @Override
    public void init() {
        unbinder = ButterKnife.bind(this);
        super.init();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (unbinder != null) {
            unbinder.unbind();
        }
    }
}
