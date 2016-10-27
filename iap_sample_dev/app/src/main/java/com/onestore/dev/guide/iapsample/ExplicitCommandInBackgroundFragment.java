
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
import com.skplanet.dodo.ProcessType;
import com.skplanet.dodo.pdu.Command;
import com.skplanet.dodo.pdu.Response;

import java.util.Locale;
import java.util.regex.Pattern;

public class ExplicitCommandInBackgroundFragment extends Fragment implements View.OnClickListener {
    private Spinner mMethodSpinner;
    private Spinner mActionSpinner;

    private EditText mAid;
    private EditText mPid;
    private TextView mLog;

    private Button mExecute;
    private LinearLayout mActionLayout;

    private String mMethodParam;
    private String mActionParam;
    private String mAppidParam;
    private String[] mProductIdsParam;

    private String mRequestId;

    private IapPlugin mPlugin;
    private IapPlugin.AbsRequestCallback mAbsRequestCallback = new IapPlugin.AbsRequestCallback() {
        @Override
        protected void onResponse(Response response) {
            final StringBuffer sb = new StringBuffer("onResponse() \n");
            sb.append("To:" + response.toString());
            mLog.setText(sb.toString());
        }

        @Override
        public void onError(final String reqid, final String errcode, final String errmsg) {
            mLog.setText("onError() identifier:" + reqid + " code:" + errcode + " msg:" + errmsg);
        }
    };


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        // 디버그용
        //mPlugin = IapPlugin.getPlugin(activity, IapPlugin.DEVELOPMENT_MODE);
        // 상용
        mPlugin = IapPlugin.getPlugin(activity, IapPlugin.RELEASE_MODE);
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

        try {
            mRequestId = requestCommand();
        } catch (IllegalArgumentException e) {
            Toast.makeText(getActivity(), "Request Fail (Valid Param)", Toast.LENGTH_SHORT).show();
            return;
        }

        if (mRequestId == null) {
            Toast.makeText(getActivity(), "Request Fail", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getActivity(), "Request Success : " + mRequestId, Toast.LENGTH_SHORT).show();
        }
    }


    private String requestCommand() {
        if (Command.request_product_info.method().equals(mMethodParam)) {
            return mPlugin.sendCommandProductInfo(mAbsRequestCallback, ProcessType.BACKGROUND_ONLY, mAppidParam);
        } else if (Command.request_purchase_history.method().equals(mMethodParam)) {
            return mPlugin.sendCommandPurchaseHistory(mAbsRequestCallback, ProcessType.BACKGROUND_ONLY, mAppidParam, mProductIdsParam);
        } else if (Command.change_product_properties.method().equals(mMethodParam)) {
            return mPlugin.sendCommandChangeProductProperties(mAbsRequestCallback, ProcessType.BACKGROUND_ONLY, mAppidParam, mActionParam, mProductIdsParam);
        } else if (Command.check_purchasability.method().equals(mMethodParam)) {
            return mPlugin.sendCommandCheckPurchasability(mAbsRequestCallback, ProcessType.BACKGROUND_ONLY, mAppidParam, mProductIdsParam);
        }
        return null;
    }

    private boolean checkValidation() {
        String app = mAid.getText().toString();
        String ids = mPid.getText().toString();

        if (!mMethodParam.equalsIgnoreCase(Command.whole_auth_item.method()) && !mMethodParam.equalsIgnoreCase(Command.request_product_info.method())
                && !mMethodParam.equalsIgnoreCase(Command.request_purchase_history.method()) && TextUtils.isEmpty(ids)) {
            return false;
        }

        if (TextUtils.isEmpty(ids)) {
            mProductIdsParam = null;
        } else {
            Pattern p = Pattern.compile("[,]+");
            mProductIdsParam = p.split(ids);
        }

        mAppidParam = app.toUpperCase(Locale.getDefault());
        return true;
    }
}
