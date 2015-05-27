package edu.jiangnan.dm.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.*;
import android.view.inputmethod.InputMethodManager;
import android.widget.*;
import com.example.lib_float.FloatingActionButton;
import com.example.lib_float.FloatingActionsMenu;
import edu.jiangnan.dm.Constants;
import edu.jiangnan.dm.Model.ContactModel;
import edu.jiangnan.dm.R;
import android.os.Bundle;
import edu.jiangnan.dm.activity.AddContact;
import edu.jiangnan.dm.adapter.ContactCardAdapter;
import edu.jiangnan.dm.util.GetDataUtils;
import edu.jiangnan.dm.util.QRCodeUtils;
import edu.jiangnan.dm.view.PullToRefreshListView;
import edu.jiangnan.dm.zxing.activity.CaptureActivity;
import net.qiujuer.genius.widget.GeniusEditText;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class LinkManFragment extends Fragment {

	View root;
	PullToRefreshListView pullToRefreshListView;
	private Context mContext;
	private ContactCardAdapter mAdapter;
	private PopupWindow popupWindow;
	private WindowManager windowManager;
	private List<ContactModel> contactModels;
	private int nowPosition;
	private FloatingActionsMenu mMenu;
	private GeniusEditText mSearchText;
	private Button btnSearch;
	private View rawSearch;
	private boolean isVisible = false;
	private InputMethodManager mInputMethodManager;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		root = inflater.inflate(R.layout.fragment_linkman, container, false);
		mContext = getActivity();
		contactModels = new ArrayList<ContactModel>();
		initView();
		initFloatMenu();
		new GetDataTask().execute();
		mInputMethodManager = (InputMethodManager)mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
		return root;
	}

	private void initView() {
		pullToRefreshListView = (PullToRefreshListView)root.findViewById(R.id.list);
		pullToRefreshListView.setScrollbarFadingEnabled(false);
		pullToRefreshListView.setOnRefreshListener(new PullToRefreshListView.OnRefreshListener() {
			@Override
			public void onRefresh() {
				new GetDataTask().execute();
			}
		});
		initPopWindow();
		pullToRefreshListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				nowPosition = position - 1;
				Log.e("POSITION=============","=========="+ nowPosition);
				showPop(view);
			}
		});

		rawSearch = root.findViewById( R.id.search_raw);
		mSearchText = (GeniusEditText)root.findViewById(R.id.search);
		btnSearch = (Button) root.findViewById(R.id.btn_search);
		btnSearch.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (mSearchText != null )
				new SearchTask().execute();
			}
		});
	}

	private class GetDataTask extends AsyncTask<Void, Void, Void> {

		@Override
		protected Void doInBackground(Void... params) {
			initListData();
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			pullToRefreshListView.onRefreshComplete();
			pullToRefreshListView.setAdapter(mAdapter);
			super.onPostExecute(result);
		}
	}

	private class SearchTask extends AsyncTask<Void, Void, Void> {

		@Override
		protected Void doInBackground(Void... params) {
			contactModels = new ArrayList<ContactModel>();
			contactModels = GetDataUtils.getContactDataBySearch(mContext, mSearchText.getText().toString());
//			if(contactModels != null && contactModels.size() > 0) {
				mAdapter.swapModels(contactModels);
//			}
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			pullToRefreshListView.onRefreshComplete();
			pullToRefreshListView.setAdapter(mAdapter);
			int count = contactModels.size();
			if (count > 0 ) {
				Toast.makeText(mContext, mContext.getString(R.string.toast_search_resulet1) + count
						+ mContext.getString(R.string.toast_search_result2), Toast.LENGTH_SHORT).show();
			} else {
				Toast.makeText(mContext, mContext.getString(R.string.toast_search_no_result), Toast.LENGTH_SHORT).show();
			}
			super.onPostExecute(result);
			rawSearch.setVisibility(View.GONE);
			isVisible = false;
			mInputMethodManager.hideSoftInputFromWindow(rawSearch.getWindowToken(), 0);
		}
	}

	private void initListData() {
		contactModels = GetDataUtils.getContactDataFromDB(mContext);
		mAdapter = new ContactCardAdapter(mContext, contactModels);
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if( requestCode == Constants.CODE_ADDED ) {
			new GetDataTask().execute();
		}
		if( requestCode == Constants.CODE_SCAN_TO_ADD && resultCode == CaptureActivity.RESULT_OK) {
			Bundle bundle = data.getExtras();
			String scanResult = bundle.getString("result");
			Toast.makeText(mContext, scanResult, Toast.LENGTH_LONG).show();
			new GetDataTask().execute();
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	private void initPopWindow() {
		LayoutInflater inflater = LayoutInflater.from(mContext);
		View popView = inflater.inflate(R.layout.card_foot_layout, null);
		windowManager = (WindowManager)mContext.getSystemService(Context.WINDOW_SERVICE);
		popupWindow = new PopupWindow(popView, windowManager.getDefaultDisplay().getWidth(),80);
		popupWindow.setTouchable(true);
		popupWindow.setBackgroundDrawable(new ColorDrawable());
		popupWindow.setFocusable(true);
		View btnCall = popView.findViewById(R.id.call);
		btnCall.setOnClickListener(popViewItemClicked);
		View btnMsg = popView.findViewById(R.id.message);
		btnMsg.setOnClickListener(popViewItemClicked);
		View btnDelete = popView.findViewById(R.id.delete);
		btnDelete.setOnClickListener(popViewItemClicked);
		View btnShare = popView.findViewById(R.id.share);
		btnShare.setOnClickListener(popViewItemClicked);

	}
	private void showPop(View view) {
		if(popupWindow == null ) {
			return;
		}
		if( popupWindow.isShowing() ) {
			popupWindow.dismiss();
		}
		popupWindow.showAsDropDown(view, 16, -view.getHeight()+16);

	}

	private View.OnClickListener popViewItemClicked = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			String telephoneNum = contactModels.get(nowPosition).getTelephone();
			switch ( v.getId() ) {
				case R.id.call:
					gotoCall( telephoneNum );
					break;
				case R.id.message:
					gotoMessage( telephoneNum );
					break;
				case R.id.delete:
					deleteItem( telephoneNum );
					break;
				case R.id.share:
					QRCodeUtils.showQRcode(mContext, contactModels.get(nowPosition));
					break;
				default:break;
			}
		}
	};

	private void gotoCall(String tel) {
		if( tel != null && !tel.equals("") ) {
			Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + tel));
			startActivity(intent);
		}
	}

	private void gotoMessage(String tel ) {
		if( tel != null && !tel.equals("") ) {
			Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.parse("smsto:" + tel));
			startActivity(intent);
		}
	}

	private void deleteItem( String tel ) {
			popupWindow.dismiss();

		GetDataUtils.deleteContactData(mContext, tel);
		new GetDataTask().execute();
	}

	private void initFloatMenu() {
		mMenu = (FloatingActionsMenu) root.findViewById(R.id.menu);
		FloatingActionButton fabInput = (FloatingActionButton) root.findViewById(R.id.btn_input);
		FloatingActionButton fabScan = (FloatingActionButton) root.findViewById(R.id.btn_scan);
		FloatingActionButton fabSearch = (FloatingActionButton) root.findViewById(R.id.menu_search);
		fabInput.setOnClickListener(floatActionBtnOnClicked);
		fabScan.setOnClickListener(floatActionBtnOnClicked);
		fabSearch.setOnClickListener(floatActionBtnOnClicked);

	}

	private View.OnClickListener floatActionBtnOnClicked = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			switch ( v.getId() ) {
				case R.id.btn_input :
					Intent intentAddInfo = new Intent(getActivity(),AddContact.class);
					startActivityForResult(intentAddInfo, Constants.CODE_ADDED);
					mMenu.collapse();
					break;
				case R.id.btn_scan :
					Intent intentScan = new Intent(getActivity(),CaptureActivity.class);
					startActivityForResult(intentScan, Constants.CODE_SCAN_TO_ADD);
					mMenu.collapse();
					break;
				case R.id.menu_search:
					if(!isVisible) {
						rawSearch.setVisibility(View.VISIBLE);
					} else {
						rawSearch.setVisibility(View.GONE);
					}
					isVisible = !isVisible;
					break;
				default:break;
			}
		}
	};

}
