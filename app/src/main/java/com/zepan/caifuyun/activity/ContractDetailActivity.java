package com.zepan.caifuyun.activity;


import com.zepan.android.widget.AlphaTextView;
import com.zepan.caifuyun.R;
import com.zepan.caifuyun.R.drawable;
import com.zepan.caifuyun.R.id;
import com.zepan.caifuyun.R.layout;
import com.zepan.caifuyun.R.string;
import com.zepan.caifuyun.base.BaseActivity;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
/**
 * 合同详情
 * @author duanjie
 *
 */
public class ContractDetailActivity extends BaseActivity implements OnClickListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_contract_detail);
		setHeaderFields(0, R.string.title_activity_contract_detail, 
				0,R.drawable.ic_action_back, 0,0);
		initView();
	}

	private void initView() {
		((AlphaTextView)findViewById(R.id.tv_right)).setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case 1:

			break;

		default:
			break;
		}

	}
}
