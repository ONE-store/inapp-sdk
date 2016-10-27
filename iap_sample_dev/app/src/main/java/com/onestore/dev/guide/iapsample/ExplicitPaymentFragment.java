
package com.onestore.dev.guide.iapsample;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.skplanet.dodo.IapPlugin;
import com.skplanet.dodo.ReceiptVerificationTask;
import com.skplanet.dodo.helper.PaymentParams;
import com.skplanet.dodo.pdu.Response;

import java.util.regex.Pattern;

public class ExplicitPaymentFragment extends Fragment implements View.OnClickListener {
    private EditText mAid;
    private EditText mPid;
    private EditText mPname;

    private Button mExecute;
    private TextView mLog;

    private String mRequestId;

    private IapPlugin mPlugin;
    private IapPlugin.AbsRequestCallback mAbsRequestCallback = new IapPlugin.AbsRequestCallback() {
        @Override
        protected void onResponse(Response response) {
            final StringBuffer sb = new StringBuffer("onResponse() \n");
            sb.append("To:" + response.toString());
            mLog.setText(sb.toString());
            mPlugin.sendReceiptVerificationRequest(mAid.getText().toString(), response.result.txid, response.result.receipt, mRvRequestCallback);
        }

        @Override
        public void onError(String reqid, String errcode, String errmsg) {
            mLog.setText("onError() identifier:" + reqid + " code:" + errcode + " msg:" + errmsg);
        }
    };

    private ReceiptVerificationTask.RequestCallback mRvRequestCallback = new ReceiptVerificationTask.RequestCallback() {
        @Override
        public void onError(int code) {
            Toast.makeText(getActivity(), "VerifyReceiptRequest Fail : " + code, Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onResponse(String result) {
            mLog.append(result);
        }
    };


//    private ReceiptVerificationTask.AbsRequestCallback mRvRequestCallback = new ReceiptVerificationTask.AbsRequestCallback() {
//        @Override
//        public void onError(int code) {
//            Toast.makeText(getActivity(), "VerifyReceiptRequest Fail : " + code, Toast.LENGTH_SHORT).show();
//        }
//
//        @Override
//        protected void onResponse(VerifyReceipt response) {
//            mLog.append(response.toString());
//        }
//    };


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

        mRequestId = requestPayment();
        if (mRequestId == null) {
            Toast.makeText(getActivity(), "Request Fail", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getActivity(), "Request Success : " + mRequestId, Toast.LENGTH_SHORT).show();
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

    private String requestPayment() {
        String tmp = mPname.getText().toString();
        if (TextUtils.isEmpty(tmp)) {
            PaymentParams params = new PaymentParams.Builder(mAid.getText().toString(), mPid.getText().toString())
                    .addTid("AA12345")
                    .addBpInfo("Test한글")
                    .build();
            return mPlugin.sendPaymentRequest(mAbsRequestCallback, params);
        } else {
            PaymentParams params = new PaymentParams.Builder(mAid.getText().toString(), mPid.getText().toString())
                    .addProductName(tmp)
                    .addTid("AA12345")
                    .addBpInfo("Test한글")
                    .build();
            return mPlugin.sendPaymentRequest(mAbsRequestCallback, params);
        }
    }

    private InputFilter filterAlphaNum = new InputFilter() {
        public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
            Pattern ps = Pattern.compile("^[a-zA-Z0-9]+$");
            if (!ps.matcher(source).matches()) {
                return "";
            }
            return null;
        }
    };

    private boolean checkValidation() {
        final String aid = mAid.getText().toString();
        final String pid = mPid.getText().toString();

        if (TextUtils.isEmpty(aid) || TextUtils.isEmpty(pid)) {
            return false;
        }
        return true;
    }
}
