package com.example.joe.wei.model.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ArrayAdapter;

import com.example.joe.wei.R;
import com.example.joe.wei.utils.XListAdapter;
import com.example.joe.wei.utils.XListUtil;
import com.example.joe.wei.widgt.XListView;

import java.util.ArrayList;
import java.util.List;


public class XListViewActivity extends Activity implements XListView.IXListViewListener {
	private XListView mListView;
	private XListAdapter mAdapter;
	private List<XListUtil> items = new ArrayList<XListUtil>();
	private Handler mHandler;
	private int start = 0;
	private static int refreshCnt = 0;
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_xlist);
		geneItems();
		mListView = (XListView) findViewById(R.id.xListView);
		mListView.setPullLoadEnable(true);
		mAdapter = new XListAdapter(this, R.layout.shop_list_item, items);
		mListView.setAdapter(mAdapter);
//		mListView.setPullLoadEnable(false);
//		mListView.setPullRefreshEnable(false);
		mListView.setXListViewListener(this);
		mHandler = new Handler();
	}

	private void geneItems() {
		XListUtil xListUtil = new XListUtil(R.mipmap.shop_list_mix,"小米mix",
				"全面屏 陶瓷机身 超声波距离传感器","￥3999");
		for (int i = 0; i != 20; ++i) {
			items.add(xListUtil);
		}
	}

	private void onLoad() {
		mListView.stopRefresh();
		mListView.stopLoadMore();
		mListView.setRefreshTime("刚刚");
	}
	
	@Override
	public void onRefresh() {
		mHandler.postDelayed(new Runnable() {
			@Override
			public void run() {
				start = ++refreshCnt;
				items.clear();
				geneItems();
				// mAdapter.notifyDataSetChanged();
				mAdapter = new XListAdapter(XListViewActivity.this, R.layout.shop_list_item, items);
				mListView.setAdapter(mAdapter);
				onLoad();
			}
		}, 2000);
	}

	@Override
	public void onLoadMore() {
		mHandler.postDelayed(new Runnable() {
			@Override
			public void run() {
				geneItems();
				mAdapter.notifyDataSetChanged();
				onLoad();
			}
		}, 2000);
	}

}