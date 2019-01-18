package com.framework.app.utils;

import android.widget.Toast;

import com.framework.app.MyApp;


/**
 * Created by admin on 2017/12/20.
 */

public class ToastUtils {
    /**
     * 显示一个吐司文本
     *
     * @param message
     */
    public static void show(String message) {
        Toast toast = null;
        if (message == null || message.equals("")) return;
        if (toast == null) {
            toast = Toast.makeText(MyApp.getInstance(), message, Toast.LENGTH_SHORT);
        } else {
            toast.setText(message);
        }
        // 如果显示的文字超过了10个就显示长吐司，否则显示短吐司
        if (message.length() > 20) {
            toast.setDuration(Toast.LENGTH_LONG);
        } else {
            toast.setDuration(Toast.LENGTH_SHORT);
        }
        toast.show();
    }

    /**
     * 显示一个对象的吐司
     *
     * @param object 对象
     */
    public static void show(Object object) {
        show(object != null ? object.toString() : "null");
    }
}
