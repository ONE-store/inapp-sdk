package com.onestore.dev.guide.iapsample;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.Menu;
import android.widget.TabHost;

public class ExplicitActivity extends FragmentActivity {

	private TabHost mTabHost;
	private static final String TAB_LOGIN_VIEW = "commandInBgView";
	private static final String TAB_COMMAND_VIEW 	= "commandView";
	private static final String TAB_PAYMENT_VIEW = "paymentView";
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_detail);
		
		mTabHost = (TabHost) findViewById(android.R.id.tabhost);
		mTabHost.setup();

		TabHost.OnTabChangeListener tabChangeListener = new TabHost.OnTabChangeListener() {

			@Override
			public void onTabChanged(String id) {
				FragmentManager m = getSupportFragmentManager();
				ExplicitCommandInBackgroundFragment commandInBg = (ExplicitCommandInBackgroundFragment) m.findFragmentByTag(TAB_LOGIN_VIEW);
				ExplicitCommandFragment command = (ExplicitCommandFragment) m.findFragmentByTag(TAB_COMMAND_VIEW);
				ExplicitPaymentFragment payment = (ExplicitPaymentFragment) m.findFragmentByTag(TAB_PAYMENT_VIEW);

				FragmentTransaction transction = m.beginTransaction();

				if (commandInBg != null) {
					transction.detach(commandInBg);
				}

				if (command != null) {
					transction.detach(command);
				}
				if (payment != null) {
					transction.detach(payment);
				}

				if (id.equals(TAB_LOGIN_VIEW)) {
					if (commandInBg == null) {
						transction.add(R.id.container, new ExplicitCommandInBackgroundFragment(), TAB_LOGIN_VIEW);
					} else {
						transction.attach(commandInBg);
					}

				} else if (id.equals(TAB_COMMAND_VIEW)) {
					
					if (command == null) {
						transction.add(R.id.container, new ExplicitCommandFragment(), TAB_COMMAND_VIEW);
					} else {
						transction.attach(command);
					}

				} else if (id.equals(TAB_PAYMENT_VIEW)){
					if (payment == null) {
						transction.add(R.id.container, new ExplicitPaymentFragment(), TAB_PAYMENT_VIEW);
					} else {
						transction.attach(payment);
					}
				}
				transction.commit();
			}
		};

		mTabHost.setOnTabChangedListener(tabChangeListener);

		TabHost.TabSpec loginSpec = mTabHost.newTabSpec(TAB_LOGIN_VIEW);
		loginSpec.setIndicator(getString(R.string.title_command_background));
		loginSpec.setContent(new StubTabContent(getBaseContext()));
		mTabHost.addTab(loginSpec);

		TabHost.TabSpec commandSpec = mTabHost.newTabSpec(TAB_COMMAND_VIEW);
		commandSpec.setIndicator(getString(R.string.title_command));
		commandSpec.setContent(new StubTabContent(getBaseContext()));
		mTabHost.addTab(commandSpec);

		TabHost.TabSpec paymentSpec = mTabHost.newTabSpec(TAB_PAYMENT_VIEW);
		paymentSpec.setIndicator(getString(R.string.title_payment));
		paymentSpec.setContent(new StubTabContent(getBaseContext()));
		mTabHost.addTab(paymentSpec);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		return true;
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
	}

}
