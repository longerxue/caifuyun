package com.zepan.caifuyun.activity;

import android.os.Bundle;

import com.zepan.caifuyun.R;
import com.zepan.caifuyun.base.BaseActivity;

//会员管理的（未报备）详细资料页面
public class VipManagerDetailsUnreport extends BaseActivity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_vipmanager_details_unreport);
		initView();
	}

	private void initView() {
		// TODO Auto-generated method stub
		 setHeaderFields(0,R.string.message_details,0,R.drawable.ic_action_back,0,0);
	}
}
