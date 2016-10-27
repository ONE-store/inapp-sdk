package com.onestore.dev.guide.iapsample;

import android.content.Context;
import android.view.View;
import android.widget.TabHost.TabContentFactory;

public class StubTabContent implements TabContentFactory{
	private Context mContext;
	
	public StubTabContent(Context context){
		mContext = context;
	}
			

	@Override
	public View createTabContent(String tag) {
		View v = new View(mContext);
		return v;
	}
	

}
