package com.onestore.iap.unity;

import android.content.Context;

import com.skplanet.dodo.IapPlugin;
import com.skplanet.dodo.ProcessType;

/**
 * Created by layn01 on 9/12/16.
 *
 * Unity에서 함수를 호출하여, IapPluin 실제적인 요청을 한다
 *
 */
public class RequestAdapter {

    private final IapPlugin iapPlugin;
    private final String callbackClassName;

    // 쿼리(Command) 결과에 대해서, Unity쪽 호출 될 메서드의 이름
    private final String CB_METHOD_NAME_COMMAND_RESPONSE = "CommandResponse";
    private final String CB_METHOD_NAME_COMMAND_ERROR = "CommandError";

    // 결제(Payment) 결과에 대해서, Unity쪽 호출 될 메서드의 이름
    private final String CB_METHOD_NAME_PYAMENT_RESPONSE = "PaymentResponse";
    private final String CB_METHOD_NAME_PAYMENT_ERROR = "PaymentError";

    // 전자 영수즘 검증 대해서, Unity쪽 호출 될 메서드의 이름
    private final String CB_METHOD_NAME_RECEIPT_VERIFICATION_RESPONSE = "ReceiptVerificationResponse";
    private final String CB_METHOD_NAME_RECEIPT_VERIFICATION_ERROR = "ReceiptVerificationError";


    public RequestAdapter(String callbackClassName, Context context, boolean isDebug) {
        this.callbackClassName = callbackClassName;
        iapPlugin = IapPlugin.getPlugin(context, (isDebug?IapPlugin.DEVELOPMENT_MODE:IapPlugin.RELEASE_MODE));
    }

    public void requestPayment(String appId, String productId, String productName, String tId, String bpInfo) {
        iapPlugin.sendPaymentRequest(appId, productId, productName, tId, bpInfo,
                new RequestCallbackAdapter(callbackClassName, CB_METHOD_NAME_PYAMENT_RESPONSE, CB_METHOD_NAME_PAYMENT_ERROR));
    }

    public void verifyReceipt(String appId, String txId, String signData) {
        iapPlugin.sendReceiptVerificationRequest(appId, txId, signData,
                new ReceiptVerificationCallbackAdapter(callbackClassName, CB_METHOD_NAME_RECEIPT_VERIFICATION_RESPONSE, CB_METHOD_NAME_RECEIPT_VERIFICATION_ERROR));
    }

    public void requestPurchaseHistory(boolean backgroundOnly, String appId, String[] productIds) {
        iapPlugin.sendCommandPurchaseHistory(new RequestCallbackAdapter(callbackClassName, CB_METHOD_NAME_COMMAND_RESPONSE, CB_METHOD_NAME_COMMAND_ERROR),
                (backgroundOnly?ProcessType.BACKGROUND_ONLY:ProcessType.FOREGROUND_IF_NEEDED), appId, productIds);
    }

    public void requestProductInfo(boolean backgroundOnly, String appId) {
        iapPlugin.sendCommandProductInfo(new RequestCallbackAdapter(callbackClassName, CB_METHOD_NAME_COMMAND_RESPONSE, CB_METHOD_NAME_COMMAND_ERROR),
                (backgroundOnly?ProcessType.BACKGROUND_ONLY:ProcessType.FOREGROUND_IF_NEEDED), appId);
    }

    public void requestCheckPurchasability(boolean backgroundOnly, String appId, String[] productIds) {
        iapPlugin.sendCommandCheckPurchasability(new RequestCallbackAdapter(callbackClassName, CB_METHOD_NAME_COMMAND_RESPONSE, CB_METHOD_NAME_COMMAND_ERROR),
                (backgroundOnly?ProcessType.BACKGROUND_ONLY:ProcessType.FOREGROUND_IF_NEEDED), appId, productIds);
    }

    public void requestChangeProductProperties(boolean backgroundOnly, String action, String appId, String[] productIds) {
        iapPlugin.sendCommandChangeProductProperties(new RequestCallbackAdapter(callbackClassName, CB_METHOD_NAME_COMMAND_RESPONSE, CB_METHOD_NAME_COMMAND_ERROR),
                (backgroundOnly?ProcessType.BACKGROUND_ONLY:ProcessType.FOREGROUND_IF_NEEDED), appId, action, productIds);
    }

    public void exit() {
        iapPlugin.exit();
    }
}
