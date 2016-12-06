package com.epicgames.ue4;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import com.onestore.client.exception.InvalidMetadataException;
import com.skplanet.dodo.IapPlugin;
import com.skplanet.dodo.IapResponse;
import com.skplanet.dodo.ProcessType;
import com.skplanet.dodo.ReceiptVerificationTask;
import com.skplanet.dodo.pdu.Action;

public class OnestoreIapHelper {
    private static final String LOG_TAG = OnestoreIapHelper.class.getSimpleName();
    private final Activity mActivity;
    private final boolean mIsDebugMode;
    private IapPlugin mIapPlugin;

    public OnestoreIapHelper(Activity gameActivity, boolean debugMode) throws InvalidMetadataException, IllegalArgumentException {
        if(gameActivity == null){
            throw new IllegalArgumentException("activity param is null");
        }

        this.mActivity = gameActivity;
        this.mIsDebugMode = debugMode;

        iapInit();
    }

    private void iapInit(){
        try{
            if (mIsDebugMode) {
                mIapPlugin = IapPlugin.getPlugin(mActivity, IapPlugin.DEVELOPMENT_MODE);
            } else {
                mIapPlugin = IapPlugin.getPlugin(mActivity, IapPlugin.RELEASE_MODE);
            }
        }catch(InvalidMetadataException e){
            throw new InvalidMetadataException("metadata is invalid. check iap:api_version value. we support iap:api_version 4 +");
        }
    }

    public String requestPurchase(String appId, String pId, String pName, String tId, String bpInfo) {
        Log.d(LOG_TAG, "********************************************");
        Log.d(LOG_TAG, "doRequestPurchase()");
        Log.d(LOG_TAG, "appId : " + appId);
        Log.d(LOG_TAG, "pId : " + pId);
        Log.d(LOG_TAG, "pName : " + pName);
        Log.d(LOG_TAG, "tId : " + tId);
        Log.d(LOG_TAG, "bpInfo : " + bpInfo);
        Log.d(LOG_TAG, "********************************************");

        Bundle res = mIapPlugin.sendPaymentRequest(appId, pId, pName, tId, bpInfo, new IapPlugin.RequestCallback() {
            @Override
            public void onResponse(IapResponse iapResponse) {
                Log.d(LOG_TAG, "onResponse : " + iapResponse.getContentToString());
                nativeOnPaymentComplete(iapResponse.getContentToString());
            }

            @Override
            public void onError(String reqId, String errCode, String errMsg) {
                Log.d(LOG_TAG, "onError. reqId : " + reqId + ", errCode : " + errCode + ", errMsg : " + errMsg);
                nativeOnPaymentError(reqId, errCode, errMsg);
            }
        });

        String res_requestId = "";

        if(res != null){
            res_requestId = res.getString(IapPlugin.EXTRA_REQUEST_ID);
        }

        return res_requestId;
    }

    public String doCmdPurchaseHistory(String appId, String ... pIds)throws IllegalArgumentException {
        return mIapPlugin.sendCommandPurchaseHistory(new IapPlugin.RequestCallback() {
            @Override
            public void onResponse(IapResponse iapResponse) {
                Log.d(LOG_TAG, "onResponse : " + iapResponse.getContentToString());
                nativeOnCommandComplete(iapResponse.getContentToString());
            }

            @Override
            public void onError(String reqId, String errCode, String errMsg) {
                Log.d(LOG_TAG, "onError. reqId : " + reqId + ", errCode : " + errCode + ", errMsg : " + errMsg);
                nativeOnCommandError(reqId, errCode, errMsg);
            }
        }, ProcessType.FOREGROUND_IF_NEEDED, appId, pIds);
    }

    public String doCmdPurchaseHistory_bg(String appId, String ... pIds)throws IllegalArgumentException {
        return mIapPlugin.sendCommandPurchaseHistory(new IapPlugin.RequestCallback() {
            @Override
            public void onResponse(IapResponse iapResponse) {
                Log.d(LOG_TAG, "onResponse : " + iapResponse.getContentToString());
                nativeOnCommandComplete(iapResponse.getContentToString());
            }

            @Override
            public void onError(String reqId, String errCode, String errMsg) {
                Log.d(LOG_TAG, "onError. reqId : " + reqId + ", errCode : " + errCode + ", errMsg : " + errMsg);
                nativeOnCommandError(reqId, errCode, errMsg);
            }
        }, ProcessType.BACKGROUND_ONLY, appId, pIds);
    }

    public String doCmdProductInfo(String appId)throws IllegalArgumentException {
        return mIapPlugin.sendCommandProductInfo(new IapPlugin.RequestCallback() {
            @Override
            public void onResponse(IapResponse iapResponse) {
                Log.d(LOG_TAG, "onResponse : " + iapResponse.getContentToString());
                nativeOnCommandComplete(iapResponse.getContentToString());
            }

            @Override
            public void onError(String reqId, String errCode, String errMsg) {
                Log.d(LOG_TAG, "onError. reqId : " + reqId + ", errCode : " + errCode + ", errMsg : " + errMsg);
                nativeOnCommandError(reqId, errCode, errMsg);
            }
        }, ProcessType.FOREGROUND_IF_NEEDED, appId);
    }

    public String doCmdProductInfo_bg(String appId)throws IllegalArgumentException {
        return mIapPlugin.sendCommandProductInfo(new IapPlugin.RequestCallback() {
            @Override
            public void onResponse(IapResponse iapResponse) {
                Log.d(LOG_TAG, "onResponse : " + iapResponse.getContentToString());
                nativeOnCommandComplete(iapResponse.getContentToString());
            }

            @Override
            public void onError(String reqId, String errCode, String errMsg) {
                Log.d(LOG_TAG, "onError. reqId : " + reqId + ", errCode : " + errCode + ", errMsg : " + errMsg);
                nativeOnCommandError(reqId, errCode, errMsg);
            }
        }, ProcessType.BACKGROUND_ONLY, appId);
    }

    public String doCmdCheckPurchasability(String appId, String ... pIds)throws IllegalArgumentException {
        return mIapPlugin.sendCommandCheckPurchasability(new IapPlugin.RequestCallback() {
            @Override
            public void onResponse(IapResponse iapResponse) {
                Log.d(LOG_TAG, "onResponse : " + iapResponse.getContentToString());
                nativeOnCommandComplete(iapResponse.getContentToString());
            }

            @Override
            public void onError(String reqId, String errCode, String errMsg) {
                Log.d(LOG_TAG, "onError. reqId : " + reqId + ", errCode : " + errCode + ", errMsg : " + errMsg);
                nativeOnCommandError(reqId, errCode, errMsg);
            }
        }, ProcessType.FOREGROUND_IF_NEEDED, appId, pIds);
    }

    public String doCmdCheckPurchasability_bg(String appId, String ... pIds)throws IllegalArgumentException {
        return mIapPlugin.sendCommandCheckPurchasability(new IapPlugin.RequestCallback() {
            @Override
            public void onResponse(IapResponse iapResponse) {
                Log.d(LOG_TAG, "onResponse : " + iapResponse.getContentToString());
                nativeOnCommandComplete(iapResponse.getContentToString());
            }

            @Override
            public void onError(String reqId, String errCode, String errMsg) {
                Log.d(LOG_TAG, "onError. reqId : " + reqId + ", errCode : " + errCode + ", errMsg : " + errMsg);
                nativeOnCommandError(reqId, errCode, errMsg);
            }
        }, ProcessType.BACKGROUND_ONLY, appId, pIds);
    }

    public String doCmdChangeProduct_withDrawSubscription(String appId, String... pIds) throws IllegalArgumentException {
        return mIapPlugin.sendCommandChangeProductProperties(new IapPlugin.RequestCallback() {
            @Override
            public void onResponse(IapResponse iapResponse) {
                Log.d(LOG_TAG, "onResponse : " + iapResponse.getContentToString());
                nativeOnCommandComplete(iapResponse.getContentToString());
            }

            @Override
            public void onError(String reqId, String errCode, String errMsg) {
                Log.d(LOG_TAG, "onError. reqId : " + reqId + ", errCode : " + errCode + ", errMsg : " + errMsg);
                nativeOnCommandError(reqId, errCode, errMsg);
            }
        }, ProcessType.FOREGROUND_IF_NEEDED, appId, Action.cancel_subscription.action(), pIds);
    }

    public String doCmdChangeProduct_withDrawSubscription_bg(String appId, String... pIds) throws IllegalArgumentException {
        return mIapPlugin.sendCommandChangeProductProperties(new IapPlugin.RequestCallback() {
            @Override
            public void onResponse(IapResponse iapResponse) {
                Log.d(LOG_TAG, "onResponse : " + iapResponse.getContentToString());
                nativeOnCommandComplete(iapResponse.getContentToString());
            }

            @Override
            public void onError(String reqId, String errCode, String errMsg) {
                Log.d(LOG_TAG, "onError. reqId : " + reqId + ", errCode : " + errCode + ", errMsg : " + errMsg);
                nativeOnCommandError(reqId, errCode, errMsg);
            }
        }, ProcessType.BACKGROUND_ONLY, appId, Action.cancel_subscription.action(), pIds);
    }

    public String doCmdChangeProduct_descentPoints(String appId, String... pIds) throws IllegalArgumentException {
        return mIapPlugin.sendCommandChangeProductProperties(new IapPlugin.RequestCallback() {
            @Override
            public void onResponse(IapResponse iapResponse) {
                Log.d(LOG_TAG, "onResponse : " + iapResponse.getContentToString());
                nativeOnCommandComplete(iapResponse.getContentToString());
            }

            @Override
            public void onError(String reqId, String errCode, String errMsg) {
                Log.d(LOG_TAG, "onError. reqId : " + reqId + ", errCode : " + errCode + ", errMsg : " + errMsg);
                nativeOnCommandError(reqId, errCode, errMsg);
            }
        }, ProcessType.FOREGROUND_IF_NEEDED, appId, Action.subtract_points.action(), pIds);
    }

    public String doCmdChangeProduct_descentPoints_bg(String appId, String... pIds) throws IllegalArgumentException {
        return mIapPlugin.sendCommandChangeProductProperties(new IapPlugin.RequestCallback() {
            @Override
            public void onResponse(IapResponse iapResponse) {
                Log.d(LOG_TAG, "onResponse : " + iapResponse.getContentToString());
                nativeOnCommandComplete(iapResponse.getContentToString());
            }

            @Override
            public void onError(String reqId, String errCode, String errMsg) {
                Log.d(LOG_TAG, "onError. reqId : " + reqId + ", errCode : " + errCode + ", errMsg : " + errMsg);
                nativeOnCommandError(reqId, errCode, errMsg);
            }
        }, ProcessType.BACKGROUND_ONLY, appId, Action.subtract_points.action(), pIds);
    }

    public void doReceiptVerification(String appId, String txId, String receiptSignData)throws IllegalArgumentException {
        mIapPlugin.sendReceiptVerificationRequest(appId, txId, receiptSignData, receiptCallback);
    }

    private ReceiptVerificationTask.RequestCallback receiptCallback = new ReceiptVerificationTask.RequestCallback() {
        @Override
        public void onResponse(String result) {
            Log.d(LOG_TAG, "onResponse : " + result);
            nativeOnVerifyReceiptComplete(result);
        }

        @Override
        public void onError(int errCode) {
            Log.d(LOG_TAG, "onError : " + errCode);
            nativeOnVerifyReceiptError(String.valueOf(errCode));
        }
    };

    /**
     * When the purchase complete, the boolean value and result string is passed by following native method.
     * You have to implement following native methods.
     */
//    public native void nativeOnPaymentComplete(String resultString);
//    public native void nativeOnPaymentError(String reqId, String errCode, String errMsg);
//
//    public native void nativeOnCommandComplete(String resultString);
//    public native void nativeOnCommandError(String reqId, String errCode, String errMsg);
//
//    public native void nativeOnVerifyReceiptComplete(String resultString);
//    public native void nativeOnVerifyReceiptError(String errCode);

//    # For Test #
    private void nativeOnPaymentComplete(String resultString){}
    private void nativeOnPaymentError(String reqId, String errCode, String errMsg){}

    private void nativeOnCommandComplete(String resultString){}
    private void nativeOnCommandError(String reqId, String errCode, String errMsg){}

    private void nativeOnVerifyReceiptComplete(String resultString){}
    private void nativeOnVerifyReceiptError(String errCode){}
}