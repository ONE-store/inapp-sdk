package com.onestore.iap.unity;

import com.skplanet.dodo.ReceiptVerificationTask;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by layn01 on 9/12/16. <br><br>
 *
 * verifyRecipt에 대해서, IapPlugin에서 받은 결과를 Unity에 전달한다<br>
 *
 */
class ReceiptVerificationCallbackAdapter extends AbsCallbackAdapter implements ReceiptVerificationTask.RequestCallback {

    static private final Set<ReceiptVerificationCallbackAdapter> callbackRefHolder = new HashSet<>();

    ReceiptVerificationCallbackAdapter(String className, String successCallbackMethodName, String failCallbackMethodName) {
        super(className, successCallbackMethodName, failCallbackMethodName);
        callbackRefHolder.add(this);
    }

    @Override
    public void onError(int code) {
        // TODO 로그 삭제
        //Log.d("only1", "[ReceiptVerificationCallbackAdapter] onError called");
        //Log.d("only1", "code >>> " + code);
        //
        dispatchFailCallback(MessageMaker.toReceiptVerificationErrorMessage(String.valueOf(code)));
        callbackRefHolder.remove(this);
    }

    @Override
    public void onResponse(String result) {
        // TODO 로그 삭제 및 결과 길이제한 체크
        //Log.d("only1", "[PaymentCallbackAdapter] onResponse called");
        //Log.d("only1", ">>> " + result);
        //
        // 응답의 경우, Json형태이므로 그대로 보내자
        dispatchSuccessCallback(result);
        callbackRefHolder.remove(this);
    }
}
