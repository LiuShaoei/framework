package com.framework.app.base;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.framework.app.R;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import xst.app.com.mylibrary.utils.StatusBar;

/**
 * Created by admin on 2017/12/20.
 */

public abstract class BaseFragment<V, T extends BasePresenter<V>> extends Fragment {

    private Unbinder mUnbinder;
    protected T mPresenter;
    private CompositeDisposable mDisposable;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(getLayoutId(), container, false);
        mUnbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (getLinearLayout() != null) {
            StatusBar.init(getActivity(), getLinearLayout());
        }
        if (mDisposable == null) {
            mDisposable = new CompositeDisposable();
        }

        mPresenter = createPresenter();
        if (mPresenter != null) {
            mPresenter.attachView((V) this);
        }
        initData();
    }

    protected abstract T createPresenter();

    public BaseView getBaseView() {
        return (BaseView) mPresenter.mViewRf.get();
    }

    protected abstract LinearLayout getLinearLayout();


    protected abstract void initData();

    public abstract int getLayoutId();

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mUnbinder != null) {
            mUnbinder.unbind();
        }
        if (mPresenter != null) {
            mPresenter.detachView();
        }
        mPresenter = null;
    }

    /**
     * 跳转到新的页面
     *
     * @param cls
     */
    public void startActivity(Class<? extends Activity> cls) {
        startActivity(new Intent(getActivity(), cls));
    }

    public void addDisposed(Disposable disposable) {
        mDisposable.add(disposable);
    }

    @Override
    public void startActivity(Intent intent) {
        super.startActivity(intent);
        enterActivityAnimation();
    }

    public void enterActivityAnimation() {
        getActivity().overridePendingTransition(R.anim.activity_slide_from_right, R.anim.activity_slide_to_left);
    }
}
