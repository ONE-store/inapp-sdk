package com.onestore.iap.unreal.sampleapp;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.epicgames.ue4.OnestoreIapHelper;

public class MainActivity extends Activity {
    private static final String LOG_TAG = "IapAdaptor_Sample";
    private OnestoreIapHelper mOnestoreIapHelper;

    // for test
    private String appId = "OA00679020";;
    private String pId = "0910024112";;
    private String pName = "TEST";
    private String tId = "123456";
    private String bpInfo = "bpInfomation";
    private String txId = "TSTORE0004_20160930172121928803137279998";
    private String receiptSignData = "MIIIRAYJKoZIhvcNAQcCoIIINTCCCDECAQExDzANBglghkgBZQMEAgEFADCBogYJKoZIhvcNAQcBoIGUBIGRMjAxNjA5MzAxNzIxMzN8VFNUT1JFMDAwNF8yMDE2MDkzMDE3MjEyMTkyODgwMzEzNzI3OTk5OHwwMTAyMDk2NzA4MXxPQTAwNjc5MDIwfDA5MTAwMjQxMTJ8MjAwfDEyMzQ1NnxicEluZm9tYXRpb258SUFQX77GwMzF2yC80rjqvLogsMe05yC788ewXzIwMKCCBe4wggXqMIIE0qADAgECAgQBA2CEMA0GCSqGSIb3DQEBCwUAME8xCzAJBgNVBAYTAktSMRIwEAYDVQQKDAlDcm9zc0NlcnQxFTATBgNVBAsMDEFjY3JlZGl0ZWRDQTEVMBMGA1UEAwwMQ3Jvc3NDZXJ0Q0EyMB4XDTE1MTIxNjA1MjMwMFoXDTE2MTIyMTE0NTk1OVowgYwxCzAJBgNVBAYTAktSMRIwEAYDVQQKDAlDcm9zc0NlcnQxFTATBgNVBAsMDEFjY3JlZGl0ZWRDQTEbMBkGA1UECwwS7ZWc6rWt7KCE7J6Q7J247KadMQ8wDQYDVQQLDAbshJzrsoQxJDAiBgNVBAMMG+yXkOyKpOy8gOydtCDtlIzrnpjri5so7KO8KTCCASIwDQYJKoZIhvcNAQEBBQADggEPADCCAQoCggEBANC3a+Wso3ykIqMOtTaOhj019zO6KI/iKGdfHjfig0ZOwiYyz/q4OqVGY2tGw4cCMYy4HlKOaISQJ3Qm0cGqV0YH3oGzuQfPXSAYcuasR6ZyD4PKUoMpfWHZgR9DopxCS3rL+T9I4nix+hXqEYUkXnCWaUF0d17WDAdqZbQeP8gOjQjfeOsrWxFOWkcy2GbanGerm7ZGZxlut2S10fYHNJYRUtO4esi2aeuIeFsw/Cor0xQv3CFn8ufWv4WSRRQZbu5cN9GkmRqphEHtksdRf3Rs9xNATm549CKp65eW6AXDFB6ykFv7jp6/LQoNRoeH5ftN3VoqqwZ/TZZ2vtsowIUCAwEAAaOCAo4wggKKMIGPBgNVHSMEgYcwgYSAFLZ0qZuSPMdRsSKkT7y3PP4iM9d2oWikZjBkMQswCQYDVQQGEwJLUjENMAsGA1UECgwES0lTQTEuMCwGA1UECwwlS29yZWEgQ2VydGlmaWNhdGlvbiBBdXRob3JpdHkgQ2VudHJhbDEWMBQGA1UEAwwNS0lTQSBSb290Q0EgNIICEAQwHQYDVR0OBBYEFNPMzSfwBiSEt0e2E4Vj5lVe1rRqMA4GA1UdDwEB/wQEAwIGwDCBgwYDVR0gAQH/BHkwdzB1BgoqgxqMmkQFBAEDMGcwLQYIKwYBBQUHAgEWIWh0dHA6Ly9nY2EuY3Jvc3NjZXJ0LmNvbS9jcHMuaHRtbDA2BggrBgEFBQcCAjAqHii8+AAgx3jJncEcx1gAIMcg1qiuMKwEx0AAIAAxsUQAIMeFssiy5AAuMHoGA1UdEQRzMHGgbwYJKoMajJpECgEBoGIwYAwb7JeQ7Iqk7LyA7J20IO2UjOuemOuLmyjso7wpMEEwPwYKKoMajJpECgEBATAxMAsGCWCGSAFlAwQCAaAiBCDtJ3TJXIgvIuK1002mylXS7s+Wktz2//YLA3nIl6hrlTB9BgNVHR8EdjB0MHKgcKBuhmxsZGFwOi8vZGlyLmNyb3NzY2VydC5jb206Mzg5L2NuPXMxZHA5cDc0LG91PWNybGRwLG91PUFjY3JlZGl0ZWRDQSxvPUNyb3NzQ2VydCxjPUtSP2NlcnRpZmljYXRlUmV2b2NhdGlvbkxpc3QwRgYIKwYBBQUHAQEEOjA4MDYGCCsGAQUFBzABhipodHRwOi8vb2NzcC5jcm9zc2NlcnQuY29tOjE0MjAzL09DU1BTZXJ2ZXIwDQYJKoZIhvcNAQELBQADggEBAJcdytowfYnXn73RqJiRbV8XIOmtogf8nTnD9RtOLkzUV9nXUaU+S0t/HVJZq3osHqVswQErAwehT57dtU/RV0p/TrssdBRpWgLnJbgx42fnEwm17cLrRk3qcttwEd/3ewVHlE661dW+/Eli4UsckrFwq1ZVfL2bFScd5pw73MhHiRu8IBM5OGUvWFM2Dtz61lH4CD2PgF7ibPLT399ztF5Q/DjPAF4sNHBiYZ5XW79HtK1fbs5TUSl7xX9QR0klBZtl348cZMbr9EkkbTm0tMOJPdX3xQQ7kHO+CAxBkM48AhKwms3q9b+bWE6BcUim6vFjv8voSuBWBQqydJ1Nq2gxggGCMIIBfgIBATBXME8xCzAJBgNVBAYTAktSMRIwEAYDVQQKDAlDcm9zc0NlcnQxFTATBgNVBAsMDEFjY3JlZGl0ZWRDQTEVMBMGA1UEAwwMQ3Jvc3NDZXJ0Q0EyAgQBA2CEMA0GCWCGSAFlAwQCAQUAMA0GCSqGSIb3DQEBCwUABIIBADIxVGV4pAIz4vRQuUIJJPqeux7Rrd7Ggoi+b1JdMcTaFxLgdBFyk01gSBt184gTdPlV4D+YMestOo3dIDC4bb9eU6oy4/neAGePRk0HBf/YOtgsBsUplLIBnQqxduP/l7QDEuUxIUdwsqESEPLQRYvmKbnlYLJY0YvycJyK4lKl/YW9rStaTy6E3UN2zVQNcMPqODOu3poTzWVAFvzah9fL6nJJ00rwoAObyVdIjZXZWwsB5dpwgDoTuW+QBiuZrnLHOZWwtXeh/4hkq3qYOeHBLf/QF7NKQC3LGl9thUxdn6uK1qiTm8/cWeALdcxtklsNINxJVxi4IUGrsd7bd2s=";
    ///////////////////////////////////////////

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.btn_request_purchase).setOnClickListener(mOnClickListener);
        findViewById(R.id.btn_cmd_purchase_history).setOnClickListener(mOnClickListener);
        findViewById(R.id.btn_cmd_product_info).setOnClickListener(mOnClickListener);
        findViewById(R.id.btn_cmd_check_purchasability).setOnClickListener(mOnClickListener);
        findViewById(R.id.btn_cmd_change_product_withdraw_subscription).setOnClickListener(mOnClickListener);
        findViewById(R.id.btn_cmd_change_product_descent_points).setOnClickListener(mOnClickListener);
        findViewById(R.id.btn_cmd_receipt_verify).setOnClickListener(mOnClickListener);

        findViewById(R.id.btn_cmd_purchase_history_bg).setOnClickListener(mOnClickListener);
        findViewById(R.id.btn_cmd_product_info_bg).setOnClickListener(mOnClickListener);
        findViewById(R.id.btn_cmd_check_purchasability_bg).setOnClickListener(mOnClickListener);
        findViewById(R.id.btn_cmd_change_product_withdraw_subscription_bg).setOnClickListener(mOnClickListener);
        findViewById(R.id.btn_cmd_change_product_descent_points_bg).setOnClickListener(mOnClickListener);

        initIap();
    }

    private void initIap(){
        if(mOnestoreIapHelper == null){
            mOnestoreIapHelper = new OnestoreIapHelper(MainActivity.this, false);
        }
    }

    private void doRequestPurchase(){
        if(mOnestoreIapHelper == null){
            Log.d(LOG_TAG, "[JAVA] - OnestoreIapHelper is invalid");
            return;
        }

        String requestId = mOnestoreIapHelper.requestPurchase(appId, pId, pName, tId, bpInfo);
        Log.d(LOG_TAG, "[JAVA] - requestId : " + requestId);
    }

    private void doCmdPurchaseHistory(){
        if(mOnestoreIapHelper == null){
            Log.d(LOG_TAG, "[JAVA] - OnestoreIapHelper is invalid");
            return;
        }

        String requestId = mOnestoreIapHelper.doCmdPurchaseHistory(appId, pId);
        Log.d(LOG_TAG, "[JAVA] - requestId : " + requestId);
    }

    private void doCmdPurchaseHistory_bg(){
        if(mOnestoreIapHelper == null){
            Log.d(LOG_TAG, "[JAVA] - OnestoreIapHelper is invalid");
            return;
        }

        String requestId = mOnestoreIapHelper.doCmdPurchaseHistory_bg(appId, pId);
        Log.d(LOG_TAG, "[JAVA] - requestId : " + requestId);
    }

    private void doCmdProductInfo(){
        if(mOnestoreIapHelper == null){
            Log.d(LOG_TAG, "[JAVA] - OnestoreIapHelper is invalid");
            return;
        }

        String requestId = mOnestoreIapHelper.doCmdProductInfo(appId);
        Log.d(LOG_TAG, "[JAVA] - requestId : " + requestId);
    }

    private void doCmdProductInfo_bg(){
        if(mOnestoreIapHelper == null){
            Log.d(LOG_TAG, "[JAVA] - OnestoreIapHelper is invalid");
            return;
        }

        String requestId = mOnestoreIapHelper.doCmdProductInfo_bg(appId);
        Log.d(LOG_TAG, "[JAVA] - requestId : " + requestId);
    }

    private void doCmdCheckPurchasability(){
        if(mOnestoreIapHelper == null){
            Log.d(LOG_TAG, "[JAVA] - OnestoreIapHelper is invalid");
            return;
        }

        String requestId = mOnestoreIapHelper.doCmdCheckPurchasability(appId, pId);
        Log.d(LOG_TAG, "[JAVA] - requestId : " + requestId);
    }

    private void doCmdCheckPurchasability_bg(){
        if(mOnestoreIapHelper == null){
            Log.d(LOG_TAG, "[JAVA] - OnestoreIapHelper is invalid");
            return;
        }

        String requestId = mOnestoreIapHelper.doCmdCheckPurchasability_bg(appId, pId);
        Log.d(LOG_TAG, "[JAVA] - requestId : " + requestId);
    }

    private void doCmdChangeProduct_withDrawSubscription(){
        if(mOnestoreIapHelper == null){
            Log.d(LOG_TAG, "[JAVA] - OnestoreIapHelper is invalid");
            return;
        }

        String requestId = mOnestoreIapHelper.doCmdChangeProduct_withDrawSubscription(appId, pId);
        Log.d(LOG_TAG, "[JAVA] - requestId : " + requestId);
    }

    private void doCmdChangeProduct_withDrawSubscription_bg(){
        if(mOnestoreIapHelper == null){
            Log.d(LOG_TAG, "[JAVA] - OnestoreIapHelper is invalid");
            return;
        }

        String requestId = mOnestoreIapHelper.doCmdChangeProduct_withDrawSubscription_bg(appId, pId);
        Log.d(LOG_TAG, "[JAVA] - requestId : " + requestId);
    }

    private void doCmdChangeProduct_descentPoints(){
        if(mOnestoreIapHelper == null){
            Log.d(LOG_TAG, "[JAVA] - OnestoreIapHelper is invalid");
            return;
        }

        String requestId = mOnestoreIapHelper.doCmdChangeProduct_descentPoints(appId, pId);
        Log.d(LOG_TAG, "[JAVA] - requestId : " + requestId);
    }

    private void doCmdChangeProduct_descentPoints_bg(){
        if(mOnestoreIapHelper == null){
            Log.d(LOG_TAG, "[JAVA] - OnestoreIapHelper is invalid");
            return;
        }

        String requestId = mOnestoreIapHelper.doCmdChangeProduct_descentPoints_bg(appId, pId);
        Log.d(LOG_TAG, "[JAVA] - requestId : " + requestId);
    }

    private void doCmdReceiptVerify(){
        if(mOnestoreIapHelper == null){
            Log.d(LOG_TAG, "[JAVA] - OnestoreIapHelper is invalid");
            return;
        }

        mOnestoreIapHelper.doReceiptVerification(appId, txId, receiptSignData);
    }

    private View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.btn_request_purchase:
                    doRequestPurchase();
                    break;

                case R.id.btn_cmd_purchase_history:
                    doCmdPurchaseHistory();
                    break;

                case R.id.btn_cmd_product_info:
                    doCmdProductInfo();
                    break;

                case R.id.btn_cmd_check_purchasability:
                    doCmdCheckPurchasability();
                    break;

                case R.id.btn_cmd_change_product_withdraw_subscription:
                    doCmdChangeProduct_withDrawSubscription();
                    break;

                case R.id.btn_cmd_change_product_descent_points:
                    doCmdChangeProduct_descentPoints();
                    break;

                case R.id.btn_cmd_receipt_verify:
                    doCmdReceiptVerify();
                    break;

                case R.id.btn_cmd_purchase_history_bg:
                    doCmdPurchaseHistory_bg();
                    break;

                case R.id.btn_cmd_product_info_bg:
                    doCmdProductInfo_bg();
                    break;

                case R.id.btn_cmd_check_purchasability_bg:
                    doCmdCheckPurchasability_bg();
                    break;

                case R.id.btn_cmd_change_product_withdraw_subscription_bg:
                    doCmdChangeProduct_withDrawSubscription_bg();
                    break;

                case R.id.btn_cmd_change_product_descent_points_bg:
                    doCmdChangeProduct_descentPoints_bg();
                    break;

                default:
                    break;
            }
        }
    };
}
