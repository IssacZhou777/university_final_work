package edu.jiangnan.dm.activity;

import java.util.Random;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import com.nineoldandroids.view.ViewHelper;

import edu.jiangnan.dm.Constants;
import edu.jiangnan.dm.Model.infoModel;
import edu.jiangnan.dm.R;
import edu.jiangnan.dm.util.DialogUtils;
import edu.jiangnan.dm.util.GetDataUtils;
import edu.jiangnan.dm.util.Util;
import edu.jiangnan.dm.view.CustomImageView;
import edu.jiangnan.dm.view.DragLayout;
import edu.jiangnan.dm.view.DragLayout.DragListener;
import edu.jiangnan.dm.fragment.AboutFragment;
import edu.jiangnan.dm.fragment.LinkManFragment;
import edu.jiangnan.dm.fragment.personFragment;

public class MainActivity extends FragmentActivity {

	private DragLayout dl;
	private ListView lv;
	private CustomImageView iv_icon, iv_bottom;
	private TextView title;
	private personFragment person_fragment;
	private LinkManFragment link_fragment;
	private AboutFragment about_fragment;
	public static Context context;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		context = getApplicationContext();
		Util.initImageLoader(this);
		initDragLayout();
		initView();
		initIcon();
		registerBroadCast();
	}

	private void initDragLayout() {
		dl = (DragLayout) findViewById(R.id.dl);
		dl.setDragListener(new DragListener() {
			@Override
			public void onOpen() {
				lv.smoothScrollToPosition(new Random().nextInt(30));
			}

			@Override
			public void onClose() {
				//shake();
			}

			@Override
			public void onDrag(float percent) {
				ViewHelper.setAlpha(iv_icon, 1 - percent);
			}
		});
	}

	private void initView() {
		iv_icon = (CustomImageView) findViewById(R.id.iv_icon);
		iv_bottom = (CustomImageView) findViewById(R.id.iv_bottom);
		lv = (ListView) findViewById(R.id.lv);
		title=(TextView)findViewById(R.id.title);
		person_fragment = new personFragment();
		link_fragment = new LinkManFragment();
		about_fragment = new AboutFragment();
		getSupportFragmentManager().beginTransaction().replace(R.id.container, person_fragment).commit();
		title.setText(getString(R.string.main_home_center));
		lv.setAdapter(new ArrayAdapter<String>(MainActivity.this,
				R.layout.item_text, new String[] { getString(R.string.main_home_center), getString(R.string.main_card_collector),
						getString(R.string.main_about_us), "" }));
		lv.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				switch(position){
				case 0:
					title.setText(getString(R.string.main_home_center));
					getSupportFragmentManager().beginTransaction().replace(R.id.container, person_fragment).commit();
					dl.close();
					shake();
					break;
				case 1:
					title.setText(getString(R.string.main_card_collector));
					getSupportFragmentManager().beginTransaction().replace(R.id.container, link_fragment).commit();
					dl.close();
					shake();
					break;
				case 2:
					title.setText(getString(R.string.main_about_us));
					getSupportFragmentManager().beginTransaction().replace(R.id.container, about_fragment).commit();
					dl.close();
					shake();
					break;
				default:
						break;
				}
			}
		});
		iv_icon.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				dl.open();
			}
		});
	}

	@Override
	protected void onResume() {
		super.onResume();
		//loadImage();
	}



	private void shake() {
		iv_icon.startAnimation(AnimationUtils.loadAnimation(this, R.anim.shake));
		iv_bottom.startAnimation(AnimationUtils.loadAnimation(this, R.anim.shake));
	}

	public static Context getContext() {
		return context;
	}

	private void initIcon() {
		infoModel model;
		model = GetDataUtils.getDataFromDB(context);
		if ( model == null || model.getImgBytes() == null || model.getImgBytes().length <= 0
				|| model.getName().equals("")) {
			return;
		}
		String mName = model.getName();
		byte[] mHeadByte = model.getImgBytes();
		Bitmap bitmap = BitmapFactory.decodeByteArray(mHeadByte, 0,
				mHeadByte.length);
		iv_icon.setmSrc(bitmap);
		iv_bottom.setmSrc(bitmap);
		shake();
		TextView userName = (TextView)findViewById(R.id.username);
		userName.setText(mName);
	}

	private BroadcastReceiver myIconChangeReciever = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			String action = intent.getAction();
			if( action.equals(Constants.ACTION_ICON_CHANGED) ) {
				initIcon();
			}
			if( action.equals(Constants.ACTION_GOTO_CARD) ) {
				title.setText(getString(R.string.main_card_collector));
				getSupportFragmentManager().beginTransaction().replace(R.id.container, link_fragment).commit();
				dl.close();
				shake();
			}
		}
	};

	private void  registerBroadCast() {
		IntentFilter intentFilter = new IntentFilter();
		intentFilter.addAction(Constants.ACTION_ICON_CHANGED);
		intentFilter.addAction(Constants.ACTION_GOTO_CARD);
		registerReceiver(myIconChangeReciever, intentFilter);
	}

	@Override
	protected void onDestroy() {
		unregisterReceiver(myIconChangeReciever);
		super.onDestroy();
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if( keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN ){
			DialogUtils.showDialog(this, getString(R.string.tips_exit), "", new DialogUtils.OnClickCallBack() {
				@Override
				public void confirm() {
					finish();
				}
				@Override
				public void cancel() {
				}
			});
		}
		return true;
	}
}
