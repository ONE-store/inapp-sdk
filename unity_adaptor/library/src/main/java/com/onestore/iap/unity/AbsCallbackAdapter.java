package com.onestore.iap.unity;

import android.os.Handler;
import android.os.Looper;

import com.unity3d.player.UnityPlayer;

/**
 * Created by layn01 on 9/12/16.<br><br>
 *
 * IapPlugin에서 받은 결과를 Unity에 전달한다
 *
 */
abstract class AbsCallbackAdapter {
    private final Handler handler;
    private final String className;
    private final String successCallbackMethodName;
    private final String failCallbackMethodName;

    AbsCallbackAdapter(String className, String successCallbackMethodName, String failCallbackMethodName) {
        handler = new Handler(Looper.getMainLooper());
        this.className = className;
        this.successCallbackMethodName = successCallbackMethodName;
        this.failCallbackMethodName = failCallbackMethodName;
    }

    private void dispatchCallback(final String className, final String methodName, final String message) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                UnityPlayer.UnitySendMessage(className, methodName, message);
            }
        });
    }

    protected void dispatchSuccessCallback(final String message) {
        dispatchCallback(className, successCallbackMethodName, message);
    }

    protected void dispatchFailCallback(final String message) {
        dispatchCallback(className, failCallbackMethodName, message);
    }
}
