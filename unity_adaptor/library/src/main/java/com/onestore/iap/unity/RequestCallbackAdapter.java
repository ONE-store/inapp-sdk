package com.onestore.iap.unity;

import com.skplanet.dodo.IapPlugin;
import com.skplanet.dodo.IapResponse;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by layn01 on 9/12/16. <br><br>
 *
 * Payment(결제)와 Command(쿼리)에 대해서, IapPlugin에서 받은 결과를 Unity에 전달한다<br>
 *
 */
class RequestCallbackAdapter extends AbsCallbackAdapter implements IapPlugin.RequestCallback {

    static private final Set<RequestCallbackAdapter> callbackRefHolder = new HashSet<>();

    RequestCallbackAdapter(String className, String successCallbackMethodName, String failCallbackMethodName) {
        super(className, successCallbackMethodName, failCallbackMethodName);
        callbackRefHolder.add(this);
    }

    @Override
    public void onError(String rid, String errcode, String errmsg) {
        // TODO 로그 삭제
        //Log.d("only1", "[PaymentCallbackAdapter] onError called");
        //Log.d("only1", "rid : " + rid);
        //Log.d("only1", "errcode : " + errcode);
        //Log.d("only1", "errmsg : " + errmsg);
        //
        dispatchFailCallback(MessageMaker.toPaymentErrorMessage(rid, errcode, errmsg));
        callbackRefHolder.remove(this);
    }

    @Override
    public void onResponse(IapResponse iapResponse) {
        // TODO 로그 삭제 및 결과 길이제한 체크
        //Log.d("only1", "[PaymentCallbackAdapter] onResponse called");
        //Log.d("only1", ">>> " + iapResponse.getContentToString());
        //
        // 응답의 경우, Json형태이므로 그대로 보내자
        dispatchSuccessCallback(iapResponse.getContentToString());
        callbackRefHolder.remove(this);
    }
}
