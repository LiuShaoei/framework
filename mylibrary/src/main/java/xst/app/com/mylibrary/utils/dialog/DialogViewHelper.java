package xst.app.com.mylibrary.utils.dialog;

import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.lang.ref.WeakReference;

/**
 * Created by LiuZhaowei on 2018/12/7 0007.
 * Dialog View的辅助处理类,
 */
public class DialogViewHelper {

    private View mContentView = null;
    private SparseArray<WeakReference<View>> mViews;

    public DialogViewHelper(Context context, int layoutResId) {
        this();
        this.mContentView = LayoutInflater.from(context).inflate(layoutResId, null);
    }

    public DialogViewHelper() {
        mViews = new SparseArray<>();
    }

    /**
     * 设置布局
     */
    public void setContentView(View contentView) {
        this.mContentView = contentView;
    }

    /**
     * 设置文本
     *
     * @param viewId
     * @param text
     */
    public void setText(int viewId, CharSequence text) {
        //每次都findViewById ,减少次数
        TextView tv = getView(viewId);
        if (tv != null) {
            tv.setText(text);
        }
    }

    /**
     * 设置图片
     *
     * @param viewId
     * @param
     * @return
     */
    public void setImage(int viewId, ImageLoader loader) {
        ImageView imageView = getView(viewId);
        if (imageView != null) {
            loader.loadImage(imageView, loader.getPath());
        }
    }

    //实现无关第三方
    public abstract static class ImageLoader {
        private String mPath;

        public ImageLoader(String path) {
            this.mPath = path;
        }

        public String getPath() {
            return mPath;
        }

        /**
         * 需要复写这个加载图片
         *
         * @param imageView
         * @param path
         */
        public abstract void loadImage(ImageView imageView, String path);
    }

    public <T extends View> T getView(int viewId) {
        if (mViews == null) {
            mViews = new SparseArray<>();
        }
        WeakReference<View> viewWeakReference = mViews.get(viewId);
        View view = null;
        if (viewWeakReference != null) {
            view = viewWeakReference.get();
        }
        if (view == null) {
            view = mContentView.findViewById(viewId);
            if (view != null) {
                mViews.put(viewId, new WeakReference<>(view));
            }
        }
        return (T) view;
    }

    /**
     * 设置点击事件
     *
     * @param viewId
     * @param listener
     */
    public void setOnClickListener(int viewId, View.OnClickListener listener) {
        View view = getView(viewId);
        if (view != null) {
            view.setOnClickListener(listener);
        }

    }

    public View getContentView() {
        return mContentView;
    }
}
