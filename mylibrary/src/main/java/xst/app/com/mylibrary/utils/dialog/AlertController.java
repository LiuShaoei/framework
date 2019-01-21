package xst.app.com.mylibrary.utils.dialog;

import android.content.Context;
import android.content.DialogInterface;
import android.util.SparseArray;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

/**
 * Created by LiuZhaowei on 2018/12/7 0007.
 */
class AlertController {

    private AlertDialog mDialog;
    private Window mWindow;
    private DialogViewHelper mViewHelper;

    public AlertController(AlertDialog mDialog, Window mWindow) {
        this.mDialog = mDialog;
        this.mWindow = mWindow;
    }

    public void setViewHelper(DialogViewHelper viewHelper) {
        this.mViewHelper = viewHelper;
    }

    /**
     * 设置文本
     *
     * @param viewId
     * @param text
     */
    public void setText(int viewId, CharSequence text) {
        mViewHelper.setText(viewId, text);
    }

    /**
     * 设置图片
     *
     * @param viewId
     * @param loader
     */
    private void setImage(int viewId, DialogViewHelper.ImageLoader loader) {
        mViewHelper.setImage(viewId, loader);
    }


    public <T extends View> T getView(int viewId) {
        return mViewHelper.getView(viewId);
    }

    /**
     * 设置点击事件
     *
     * @param viewId
     * @param listener
     */
    public void setOnClickListener(int viewId, View.OnClickListener listener) {
        mViewHelper.setOnClickListener(viewId, listener);

    }


    public AlertDialog getDialog() {
        return mDialog;
    }

    public Window getWindow() {
        return mWindow;
    }

    public static class AlertParams {
        public int mThemeResId;
        public Context mContext;
        public boolean mCancelable = true;//默认点击空白阴影可以取消
        public DialogInterface.OnCancelListener mOnCancelListener;
        public DialogInterface.OnDismissListener mOnDismissListener;
        //按键
        public DialogInterface.OnKeyListener mOnKeyListener;
        //布局view
        public View mView;
        //布局layoutId
        public int mViewLayoutResId;
        //宽度
        public int mWidth = ViewGroup.LayoutParams.WRAP_CONTENT;
        //动画
        public int mAnimations = 0;
        //位置
        public int mGravity = Gravity.CENTER;
        //高度
        public int mHeight = ViewGroup.LayoutParams.WRAP_CONTENT;

        //存放图片的
        SparseArray<DialogViewHelper.ImageLoader> mImageArray = new SparseArray<>();
        //存放字体的修改
        SparseArray<CharSequence> mTextArray = new SparseArray<>();
        //存放点击事件
        SparseArray<View.OnClickListener> mClickArray = new SparseArray<>();

        public AlertParams(Context context, int themeResId) {
            this.mContext = context;
            this.mThemeResId = themeResId;
        }

        /**
         * 绑定和设置参数
         *
         * @param mAlert
         */
        public void apply(AlertController mAlert) {
            //1.设置布局 DialogViewHelper
            DialogViewHelper viewHelper = null;
            if (mViewLayoutResId != 0) {
                viewHelper = new DialogViewHelper(mContext, mViewLayoutResId);
            }
            if (mView != null) {
                viewHelper = new DialogViewHelper();
                viewHelper.setContentView(mView);
            }
            if (viewHelper == null) {
                throw new IllegalArgumentException("请设置布局setContentView()");
            }
            //1.1给dialog设置布局
            mAlert.getDialog().setContentView(viewHelper.getContentView());

            //设置Controller的辅助类
            mAlert.setViewHelper(viewHelper);
            //设置图片
            int imageArraySize = mImageArray.size();
            for (int i = 0; i < imageArraySize; i++) {
                mAlert.setImage(mImageArray.keyAt(i), mImageArray.valueAt(i));
            }
            //2.设置文本
            int textArraySize = mTextArray.size();
            for (int i = 0; i < textArraySize; i++) {
                mAlert.setText(mTextArray.keyAt(i), mTextArray.valueAt(i));
            }
            //3.设置点击
            int clickArraySize = mClickArray.size();
            for (int j = 0; j < clickArraySize; j++) {
                mAlert.setOnClickListener(mClickArray.keyAt(j), mClickArray.valueAt(j));
            }


            //4.配置自定义的效果,全屏,从底部弹出,默认动画

            Window window = mAlert.getWindow();
            //设置位置
            window.setGravity(mGravity);
            //设置动画
            if (mAnimations != 0) {
                window.setWindowAnimations(mAnimations);
            }
            //设置宽高
            WindowManager.LayoutParams params = window.getAttributes();
            params.width = mWidth;
            params.height = mHeight;
            window.setAttributes(params);

        }
    }


}

