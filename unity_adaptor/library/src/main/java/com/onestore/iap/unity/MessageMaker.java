package com.onestore.iap.unity;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by layn01 on 9/28/16.
 *
 * Unity 레이어로 다수의 parameter를 가진 함수를 호출 하는 것이 어려움<br>
 * Json형태의 String 메시지를 만들어서 전달하자<br>
 *
 */

abstract class MessageMaker {
    private MessageMaker() {}

    private static final String KEY_REQUEST_ID = "requestId";
    private static final String KEY_ERROR_CODE = "errorCode";
    private static final String KEY_ERROR_MESSAGE = "errorMessage";

    // Command 에러메시지
    static public String toCommandErrorMessage(String rid, String errcode, String errmsg) {
        return toErrorMessage(rid, errcode, errmsg);
    }

    // Payment 에러메시지
    static public String toPaymentErrorMessage(String rid, String errcode, String errmsg) {
        return toErrorMessage(rid, errcode, errmsg);
    }

    // 전자영수증검증 에러메시지
    static public String toReceiptVerificationErrorMessage(String errcode) {
        try {
            JSONObject json = new JSONObject();
            json.put(KEY_ERROR_CODE, errcode);
            return json.toString();
        } catch (JSONException e) {
            // 작업없음
            // 메시지 생성에 실패하더라고, 빈메시지를 전달한다
        }
        return "";
    }


    static private String toErrorMessage(String rid, String errcode, String errmsg) {
        try {
            JSONObject json = new JSONObject();
            json.put(KEY_REQUEST_ID, rid);
            json.put(KEY_ERROR_CODE, errcode);
            json.put(KEY_ERROR_MESSAGE, errmsg);
            return json.toString();
        } catch (JSONException e) {
            // 작업없음
            // 메시지 생성에 실패하더라고, 빈메시지를 전달한다
        }
        return "";
    }
}
