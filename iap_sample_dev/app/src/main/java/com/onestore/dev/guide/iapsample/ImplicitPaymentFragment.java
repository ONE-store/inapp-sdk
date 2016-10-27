
package com.onestore.dev.guide.iapsample;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.skplanet.dodo.IapPlugin;
import com.skplanet.dodo.IapResponse;
import com.skplanet.dodo.helper.ConverterFactory;
import com.skplanet.dodo.pdu.Response;
import com.skplanet.dodo.pdu.VerifyReceipt;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Locale;
import java.util.regex.Pattern;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;

public class ImplicitPaymentFragment extends Fragment implements View.OnClickListener {
    private EditText mAid;
    private EditText mPid;
    private EditText mPname;
    private Button mExecute;
    private TextView mLog;

    private String mRequestId;

    private IapPlugin mPlugin;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View v = inflater.inflate(R.layout.payment, container, false);

        mAid = (EditText) v.findViewById(R.id.edit_appid);
        mPid = (EditText) v.findViewById(R.id.edit_product);
        mPname = (EditText) v.findViewById(R.id.edit_productname);
        mExecute = (Button) v.findViewById(R.id.btn_payment_request);
        mLog = (TextView) v.findViewById(R.id.logview);

        mExecute.setOnClickListener(this);

        mAid.setFilters(new InputFilter[] {filterAlphaNum});
        mPid.setFilters(new InputFilter[] {filterAlphaNum});

        return v;
    }

    @Override
    public void onClick(View v) {
        if (!checkValidation()) {
            return;
        }
        
        if (requestPayment()) {
            Toast.makeText(getActivity(), "Request Success", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getActivity(), "Request Failure", Toast.LENGTH_SHORT).show();
        }

    }
    
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        Log.d("VOC","PaymentFragment onAttach()");
        // 디버그용
        //mPlugin = IapPlugin.getPlugin(activity, IapPlugin.DEVELOPMENT_MODE);
        // 상용
        mPlugin = IapPlugin.getPlugin(activity, IapPlugin.RELEASE_MODE);
    }
    
    @Override
    public void onDetach() {
        super.onDetach();
        Log.d("VOC","PaymentFragment onDetach()");
        mPlugin.exit();
    }

    private boolean checkValidation() {
        final String aid = mAid.getText().toString();
        final String pid = mPid.getText().toString();
        if (TextUtils.isEmpty(aid) || TextUtils.isEmpty(pid)) {
            return false;
        }

        return true;
    }

    private boolean requestPayment() {

        final String appId = mAid.getText().toString().trim().toUpperCase(Locale.getDefault());
        final String pId = mPid.getText().toString().trim();
        final String productName = mPname.getText().toString();

        mLog.setText("");

        Bundle req = mPlugin.sendPaymentRequest(appId, pId, productName, null, null,
                new IapPlugin.RequestCallback() {

                    @Override
                    public void onResponse(IapResponse data) {
                        if (data == null || data.getContentLength() <= 0) {
                            // TODO Unusual error
                            mLog.setText("onResponse() response data is null");
                            return;
                        }

                        // 1. JSON 데이터를 통한 객체 변환 사용 예
                        Response response = ConverterFactory.getConverter().fromJson(data.getContentToString());

                        if (response == null) {
                            // TODO invalid response data
                            mLog.setText("onResponse() invalid response data");
                            return;
                        }

                        // TODO for logging
                        StringBuffer sb = new StringBuffer("onResponse() \n");
                        sb.append("From:" + data.getContentToString()).append("\n").append("To:" + response.toString());
                        mLog.setText(sb.toString());

                        // TODO 구매 완료 후 처리 예 - 성공

                        // C1. 결과 코드 체크
                        // response.result.code
                        if (!response.result.code.equals("0000")) {
                            Toast.makeText(getActivity(), "Failed to request to purchase a item", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        // S1. 구매 완료의 경우 발급된 전자 영수증을 통한 구매 확인 체크 (강력 권고)
                        // response.result.txid
                        // response.result.receipt
                        new ReceiptConfirmTask(response.result.txid, mAid.getText().toString().toUpperCase(), response.result.receipt).execute();
                    }

                    @Override
                    public void onError(String reqid, String errcode, String errmsg) {
                        // TODO Error occurred
                        mLog.setText("onError() identifier:" + reqid + " code:" + errcode + " msg:" + errmsg);
                    }
                });

        if (req == null) {
            // TODO request failure
            return false;
        }

        mRequestId = req.getString(IapPlugin.EXTRA_REQUEST_ID);
        if (mRequestId == null || mRequestId.length() == 0) {
            // TODO request failure
            return false;
        }

        return true;
    }

    private InputFilter filterAlphaNum = new InputFilter() {
        public CharSequence filter(CharSequence source, int start, int end,
                Spanned dest, int dstart, int dend) {
            Pattern ps = Pattern.compile("^[a-zA-Z0-9]+$");
            if (!ps.matcher(source).matches()) {
                return "";
            }
            return null;
        }
    };


    /**
     *
     * 영수증 검증 관련 예시 코드
     *
     */
    private class ReceiptConfirmTask extends AsyncTask<Void, Void, String> {
        private final String param;
        private final String receiptUrl;

        public ReceiptConfirmTask(String txId, String appId, String signData) {
            // 디버그용
            receiptUrl = "https://iapdev.tstore.co.kr/digitalsignconfirm.iap";
            // 상용
            //receiptUrl = "https://iap.tstore.co.kr/digitalsignconfirm.iap";

            JSONObject json = new JSONObject();
            try {
                json.put("appid", appId.toUpperCase());
                json.put("txid", txId);
                json.put("signdata", signData);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            param = json.toString();
        }

        @Override
        protected void onPostExecute(String result) {
            if (!TextUtils.isEmpty(result)) {
                VerifyReceipt verifiedReceipt = ConverterFactory.getConverter().fromJson2VerifyReceipt(result);
                mLog.append(verifiedReceipt.toString());
            }
        }

        @Override
        protected String doInBackground(Void... params) {
            if (TextUtils.isEmpty(param)) {
                return null;
            }

            try {
                URL url = new URL(receiptUrl);

                HttpsURLConnection httpsURLConnection = (HttpsURLConnection) url.openConnection();
                SSLContext sslContext = SSLContext.getInstance("TLS");
                sslContext.init(null, null, new SecureRandom());

                httpsURLConnection.setSSLSocketFactory(sslContext.getSocketFactory());
                httpsURLConnection.setConnectTimeout(10000);
                httpsURLConnection.setReadTimeout(10000);
                httpsURLConnection.setRequestMethod("POST");
                httpsURLConnection.setRequestProperty("Content-Type", "application/json");
                httpsURLConnection.setDoOutput(true);
                httpsURLConnection.setDoInput(true);
                httpsURLConnection.connect();

                BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(httpsURLConnection.getOutputStream());
                bufferedOutputStream.write(param.getBytes("UTF-8"));
                bufferedOutputStream.flush();
                bufferedOutputStream.close();

                int responseCode = httpsURLConnection.getResponseCode();

                if (responseCode == HttpURLConnection.HTTP_OK) {
                    BufferedInputStream bufferedInputStream = new BufferedInputStream(httpsURLConnection.getInputStream());
                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

                    byte[] byteBuffer = new byte[2048];
                    int readLength = 0;

                    while ((readLength = bufferedInputStream.read(byteBuffer, 0, byteBuffer.length)) != -1) {
                        byteArrayOutputStream.write(byteBuffer, 0, readLength);
                    }

                    String result = new String(byteArrayOutputStream.toByteArray());

                    byteArrayOutputStream.close();
                    bufferedInputStream.close();

                    return result;
                }

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            } catch (KeyManagementException e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}
