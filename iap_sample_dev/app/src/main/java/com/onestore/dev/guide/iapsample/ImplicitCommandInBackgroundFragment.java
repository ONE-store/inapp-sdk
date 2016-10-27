
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
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.skplanet.dodo.IapPlugin;
import com.skplanet.dodo.IapResponse;
import com.skplanet.dodo.ProcessType;
import com.skplanet.dodo.helper.CommandBuilder;
import com.skplanet.dodo.helper.ConverterFactory;
import com.skplanet.dodo.pdu.Command;
import com.skplanet.dodo.pdu.Response;

import java.util.Locale;
import java.util.regex.Pattern;

public class ImplicitCommandInBackgroundFragment extends Fragment implements View.OnClickListener {
    private Spinner mMethodSpinner;
    private Spinner mActionSpinner;

    private EditText mAid;
    private EditText mPid;
    private TextView mLog;

    private Button mExecute;
    private LinearLayout mActionLayout;

    private String mMethodParam;
    private String mActionParam;
    private String mAppid;
    private String[] mProductIds;

    private CommandBuilder mBuilder = new CommandBuilder();
    private IapPlugin mPlugin;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        // 디버그용
        //mPlugin = IapPlugin.getPlugin(activity, IapPlugin.DEVELOPMENT_MODE);
        mPlugin = IapPlugin.getPlugin(activity, IapPlugin.RELEASE_MODE);
        // 상용
        Log.d("IapPlugin", "PlugIn Object: " + mPlugin);
    }
    
    @Override
    public void onDetach() {
        super.onDetach();
        Toast.makeText(getActivity(), "onDetach()", Toast.LENGTH_SHORT).show();
        mPlugin.exit();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View v = inflater.inflate(R.layout.query, container, false);

        mLog = (TextView) v.findViewById(R.id.logview);

        mMethodSpinner = (Spinner) v.findViewById(R.id.spinner_method);
        mActionSpinner = (Spinner) v.findViewById(R.id.spinner_action);

        mActionLayout = (LinearLayout) v.findViewById(R.id.action_layout);
        mPid = (EditText) v.findViewById(R.id.edit_productids);
        mExecute = (Button) v.findViewById(R.id.btn_request);
        mAid = (EditText) v.findViewById(R.id.edit_appid);

        mPid.setFilters(new InputFilter[] {filterAlphaNum});
        mAid.setFilters(new InputFilter[] {filterAlphaNum});

        mExecute.setOnClickListener(this);

        // 메서드 스피너
        mMethodSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                String item = parent.getSelectedItem().toString();
                if (!TextUtils.isEmpty(item) && item.equalsIgnoreCase("change_product_properties")) {
                    mActionLayout.setVisibility(View.VISIBLE);
                } else {
                    mActionLayout.setVisibility(View.GONE);
                }
                mMethodParam = item;
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {}
        });

        // 액션 스피너
        mActionSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                String item = parent.getSelectedItem().toString();
                mActionParam = item;
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {}
        });

        return v;
    }

    private InputFilter filterAlphaNum = new InputFilter() {
        public CharSequence filter(CharSequence source, int start, int end,
                Spanned dest, int dstart, int dend) {
            Pattern ps = Pattern.compile("^[a-zA-Z0-9,]+$");
            if (!ps.matcher(source).matches()) {
                return "";
            }
            return null;
        }
    };

    @Override
    public void onClick(View v) {
        if (!checkValidation()) {
            return;
        }

        if (requestCommand()) {
            Toast.makeText(getActivity(), "Request Success", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getActivity(), "Request Failure", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean requestCommand() {
        String json = makeRequest();

        Bundle req = mPlugin.sendCommandRequest(json,
                new IapPlugin.RequestCallback() {

                    @Override
                    public void onResponse(IapResponse data) {
                        if (data == null || data.getContentLength() == 0) {
                            // TODO Unusual error
                            return;
                        }

                        // TODO convert json string to object
                        Response response = ConverterFactory.getConverter().fromJson(data.getContentToString());

                        StringBuffer sb = new StringBuffer("onResponse() \n");
                        sb.append("From:" + data.getContentToString()).append("\n").append("To:" + response.toString());
                        mLog.setText(sb.toString());
                    }

                    @Override
                    public void onError(String reqid, String errcode, String errmsg) {
                        // TODO Error occurred
                        String msg = "onError() identifier:" + reqid + " code:" + errcode + " msg:" + errmsg;
                        mLog.setText(msg);
                    }
                }, ProcessType.BACKGROUND_ONLY);

        if (req == null) {
            // TODO request failure
            return false;
        }

        String id = req.getString(IapPlugin.EXTRA_REQUEST_ID);
        if (id == null || id.length() == 0) {
            // TODO request failure
            return false;
        }

        return true;
    }

    private String makeRequest() {
//        return "{\"method\":\"request_product_info\",\"param\":{\"appid\":\"OA00367358\",\"product_id\":[\"0901220941\",\"0901215964\"]}}";
        
        if (Command.request_product_info.method().equals(mMethodParam)) {
            return mBuilder.requestProductInfo(mAppid);
        }
        if (Command.request_purchase_history.method().equals(mMethodParam)) {
            return mBuilder.requestPurchaseHistory(mAppid, mProductIds);
        }
        if (Command.change_product_properties.method().equals(mMethodParam)) {
            return mBuilder.changeProductProperties(mAppid, mActionParam,
                    mProductIds);
        }
        if (Command.check_purchasability.method().equals(mMethodParam)) {
            return mBuilder.checkPurchasability(mAppid, mProductIds);
        }
        if (Command.auth_item.method().equals(mMethodParam)) {
            return mBuilder.authItem(mAppid, mProductIds);
        }
        if (Command.whole_auth_item.method().equals(mMethodParam)) {
            return mBuilder.wholeAuthItem(mAppid);
        }
        if (Command.item_use.method().equals(mMethodParam)) {
            return mBuilder.itemUse(mAppid, mProductIds);
        }
        if (Command.monthly_withdraw.method().equals(mMethodParam)) {
            return mBuilder.monthlyWithdraw(mAppid, mProductIds);
        }
        return null;
    }

    private boolean checkValidation() {
        String app = mAid.getText().toString();
        String ids = mPid.getText().toString();

        if (!mMethodParam.equalsIgnoreCase(Command.whole_auth_item.method())
                && !mMethodParam.equalsIgnoreCase(Command.request_product_info.method())
                && !mMethodParam.equalsIgnoreCase(Command.request_purchase_history.method()) && TextUtils.isEmpty(ids)) {
            return false;
        }

        if (TextUtils.isEmpty(ids)) {
            mProductIds = null;
        } else {
            Pattern p = Pattern.compile("[,]+");
            mProductIds = p.split(ids);
        }
    
        mAppid = app.toUpperCase(Locale.getDefault());
        return true;
    }
}
