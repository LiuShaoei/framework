package com.framework.app.keyboard;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.GradientDrawable;
import android.util.AttributeSet;
import android.view.View;

import com.framework.app.R;


/**
 * 密码输入框及密码
 */
public class PasswordView extends View {

    private int passwordCount;

    private int strokeColor;

    private Paint mCirclePaint;

    private Paint mPaint;
    private Paint rectPaint;

    private int symbolColor;

    private float mRadius;

    private float inputBoxStroke;

    private StringBuffer mText;

    public PasswordView(Context context) {
        super(context);
    }

    public PasswordView(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.inputBox);
        //支持某些属性设置,比如密码位数,边框颜色、宽度,"●"的颜色、大小
        passwordCount = ta.getInteger(R.styleable.inputBox_passwordCount, 6);
        strokeColor = ta.getColor(R.styleable.inputBox_stokeColor, Color.parseColor("#979797"));
        symbolColor = ta.getColor(R.styleable.inputBox_symbolColor, Color.BLACK);
        mRadius = ta.getDimension(R.styleable.inputBox_symbolRadius, 12);
        inputBoxStroke = ta.getDimension(R.styleable.inputBox_inputBoxStroke, 1f);
        //设置输入框圆角边框
        /*GradientDrawable gd = new GradientDrawable();
        gd.setColor(Color.WHITE);
        gd.setStroke((int) inputBoxStroke, strokeColor);
        gd.setCornerRadius(8);
        setBackgroundDrawable(gd);*/
        ta.recycle();
        if (mPaint == null) {
            mPaint = new Paint();
            mPaint.setColor(strokeColor);
            mPaint.setStrokeWidth(inputBoxStroke);
        }
        if (rectPaint == null) {
            rectPaint = new Paint();
            rectPaint.setColor(strokeColor);
            rectPaint.setStyle(Paint.Style.STROKE);
            rectPaint.setAntiAlias(true);
            rectPaint.setStrokeWidth(inputBoxStroke);
        }
        if (mCirclePaint == null) {
            mCirclePaint = new Paint();
            mCirclePaint.setColor(symbolColor);
            mCirclePaint.setStyle(Paint.Style.FILL);
        }
    }

    public PasswordView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int singleWidth = (getMeasuredWidth() - 50) / 6;
        int height = getMeasuredHeight();
        //绘制每个"●"之间的分割线
        for (int i = 0; i < passwordCount; i++) {
            if (i == 0) {
                canvas.drawRect(0, 0, singleWidth, height, rectPaint);
            } else {
                canvas.drawRect(i * singleWidth + i * 10, 0, i * singleWidth + i * 10 + singleWidth, height, rectPaint);
            }
            //canvas.drawRect(i*singleWidth,0,i*singleWidth+singleWidth+10,height,rectPaint);
            //canvas.drawLine(singleWidth * i, 0, singleWidth * i, height, mPaint);
        }
        if (mText != null) {
            //绘制"●"
            int textSize = mText.length() > passwordCount ? passwordCount : mText.length();
            for (int i = 1; i <= textSize; i++) {
                canvas.drawCircle((singleWidth * i + (i - 1) * 10) - singleWidth / 2, height / 2, mRadius, mCirclePaint);
            }
        }
    }

    public int getPasswordCount() {
        return passwordCount;
    }

    //支持密码位数设置
    public void setPasswordCount(int passwordCount) {
        this.passwordCount = passwordCount;
    }

    //密码改变,重新绘制
    public void setPassword(CharSequence text) {
        mText = (StringBuffer) text;
        if (text.length() > passwordCount) {
            mText.delete(mText.length() - 1, mText.length());
            return;
        }
        postInvalidate();
    }

    public void clearPassword() {
        if (mText != null) {
            mText.delete(0, mText.length());
        }
    }

    public CharSequence getPassword() {
        return mText;
    }
}
