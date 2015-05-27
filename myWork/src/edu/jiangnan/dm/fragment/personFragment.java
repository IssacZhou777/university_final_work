package edu.jiangnan.dm.fragment;



import java.io.ByteArrayOutputStream;

import android.support.v4.app.Fragment;
import com.example.lib_float.FloatingActionButton;
import com.example.lib_float.FloatingActionsMenu;
import edu.jiangnan.dm.Constants;
import edu.jiangnan.dm.R;
import edu.jiangnan.dm.activity.AddInfo;
import edu.jiangnan.dm.activity.ChooseTableActivity;
import edu.jiangnan.dm.activity.MyCardActivty;
import edu.jiangnan.dm.activity.SendMyInfoActivity;
import edu.jiangnan.dm.db.MySqlHelper;
import edu.jiangnan.dm.db.TUserInfoColumn;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Bitmap.CompressFormat;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import edu.jiangnan.dm.tablewebviews.TableAmerica;
import edu.jiangnan.dm.util.DialogUtils;
import edu.jiangnan.dm.util.MyPreference;

/**
 * 
 * @author Jelly Zhou  2015-1-10
 *
 */
public class personFragment extends Fragment {
	
	private ImageView headimage;
	private TextView Name,Sex,Nation,Nationality,Birthday,Tel,Email,Family_Address,Current_Address,mJob,mOrganization;
	private View root;
	private final int CODE = 1;
	private final int CODE_ADDED = 2;
	private MySqlHelper mySqlHelper;
	private SQLiteDatabase db;
	private final String TableName="userInfo";
	private FloatingActionsMenu mMenu;

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		//return super.onCreateView(inflater, container, savedInstanceState);
		root = inflater.inflate(R.layout.fragment_person,container,false);
		headimage=(ImageView)root.findViewById(R.id.img_head);
		if( headimage.getResources() == null ){
			headimage.setImageResource(R.drawable.image);
		}
		headimage.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				//selectImage();
				chooseImage();
			}
		});
		initView();
		mySqlHelper = new MySqlHelper(getActivity(),
				"user_info.db", null, 1);
		updateData();
		initFloatMenu();
		return root;
	}

	@Override
	public void onStart() {
		super.onStart();
	}
	
	private void chooseImage() {
		// 选择相册
		Intent intent = new Intent(Intent.ACTION_PICK,
				android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
		startActivityForResult(intent, CODE);
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		if (requestCode == CODE) {
			if (resultCode == Activity.RESULT_OK && null != data) {
				Uri selectedImagePath = data.getData();
				Cursor cursor = getActivity().getContentResolver().query(selectedImagePath,
						null, null, null, null);

				if (cursor != null) {
					cursor.moveToFirst();
					String img = cursor.getString(1);
					// System.out.println("img:" + img);
					//
					// student.setImgPath(img);
					cursor.close();
					Bitmap bitmap = BitmapFactory.decodeFile(img);
					headimage.setImageBitmap(bitmap);
					db = mySqlHelper.getReadableDatabase();
					ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
					bitmap.compress(CompressFormat.PNG, 100, byteOut);
					ContentValues values = new ContentValues();
					values.put(TUserInfoColumn.USER_IMAGE, byteOut.toByteArray());
					try{
						db.update(TableName, values, null, null);
					}catch(Exception e){
						e.printStackTrace();
					}
					sendBroad();
				}
			}
		}
		if(requestCode == CODE_ADDED){
			updateData();
			sendBroad();
		}
	}

	private void initView(){
		
		Name = (TextView) root.findViewById(R.id.name);
		Sex = (TextView) root.findViewById(R.id.sex);
		Nation = (TextView) root.findViewById(R.id.nation);
		Nationality = (TextView) root.findViewById(R.id.country);
		Birthday = (TextView) root.findViewById(R.id.birthday);
		Tel = (TextView) root.findViewById(R.id.tel);
		Email = (TextView) root.findViewById(R.id.email_content);
		Family_Address = (TextView) root.findViewById(R.id.family_address_content);
		Current_Address = (TextView) root.findViewById(R.id.current_address_content);
		mJob = (TextView) root.findViewById(R.id.job);
		mOrganization = (TextView) root.findViewById(R.id.organization);
		mMenu = (FloatingActionsMenu) root.findViewById(R.id.menu);
}


	@Override
	public void startActivityForResult(Intent intent, int requestCode) {
		super.startActivityForResult(intent, requestCode);
	}

	private void updateData(){
		db = mySqlHelper.getReadableDatabase();
		Cursor cursor=null;
		try{
			cursor=db.rawQuery("select * from "+TableName, null);
			if(cursor == null || cursor.getCount()<1){
				return;
			}
			cursor.moveToFirst();
			Name.setText(cursor.getString(cursor.getColumnIndex(TUserInfoColumn.USER_NAME)));
			Sex.setText(cursor.getString(cursor.getColumnIndex(TUserInfoColumn.USER_SEX)));
			Nation.setText(cursor.getString(cursor.getColumnIndex(TUserInfoColumn.USER_NATION)));
			Nationality.setText(cursor.getString(cursor.getColumnIndex(TUserInfoColumn.USER_NATIONALITY)));
			Birthday.setText(cursor.getString(cursor.getColumnIndex(TUserInfoColumn.USER_BIRTHDAY))+"");
			Tel.setText(cursor.getString(cursor.getColumnIndex(TUserInfoColumn.USER_TELEPHONE)));
			Email.setText(cursor.getString(cursor.getColumnIndex(TUserInfoColumn.USER_EMAIL)));
			Family_Address.setText(cursor.getString(cursor.getColumnIndex(TUserInfoColumn.USER_FAMILY_ADDRESS)));
			Current_Address.setText(cursor.getString(cursor.getColumnIndex(TUserInfoColumn.USER_CURRENT_ADDRESS)));
			mJob.setText(cursor.getString(cursor.getColumnIndex(TUserInfoColumn.USER_JOB)));
			mOrganization.setText(cursor.getString(cursor.getColumnIndex(TUserInfoColumn.USER_ORGANIZATION)));

			
			byte[] imageBytes = cursor.getBlob(cursor.getColumnIndex(TUserInfoColumn.USER_IMAGE));
			Bitmap bitmap = BitmapFactory.decodeByteArray(imageBytes, 0,
					imageBytes.length);
			headimage.setImageBitmap(bitmap);
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(cursor != null){
				cursor.close();
			}
		}
	}

	private void sendBroad() {
		Intent intent = new Intent();
		intent.setAction(Constants.ACTION_ICON_CHANGED);
		getActivity().sendBroadcast(intent);
	}

	private void initFloatMenu () {
		FloatingActionButton fabAdd = (FloatingActionButton) root.findViewById(R.id.btn_add);
		FloatingActionButton fabGotoTable = (FloatingActionButton) root.findViewById(R.id.btn_goto_table);
		FloatingActionButton fabGotoMyCard = (FloatingActionButton) root.findViewById(R.id.btn_goto_mycard);
		FloatingActionButton fabCards = (FloatingActionButton) root.findViewById(R.id.btn_goto_send);
		fabAdd.setOnClickListener(floatMenuClicked);
		fabGotoTable.setOnClickListener(floatMenuClicked);
		fabGotoMyCard.setOnClickListener(floatMenuClicked);
		fabCards.setOnClickListener(floatMenuClicked);
	}

	private OnClickListener floatMenuClicked = new OnClickListener() {
		@Override
		public void onClick(View v) {
			switch ( v.getId() ) {
				case R.id.btn_add:
					Intent intentAddInfo = new Intent(getActivity(),AddInfo.class);
					startActivityForResult(intentAddInfo, CODE_ADDED);
					mMenu.collapse();
					break;
				case R.id.btn_goto_table:
					if( !MyPreference.getInstance().getInfoAdded() ) {
						showAddInfomationTip();
						return;
					}
					Intent intentGotoTable = new Intent(getActivity(),ChooseTableActivity.class);
					startActivity(intentGotoTable);
					mMenu.collapse();
					break;
				case R.id.btn_goto_mycard:
					if( !MyPreference.getInstance().getInfoAdded() ) {
						showAddInfomationTip();
						return;
					}
					Intent intentMyCard = new Intent(getActivity(),MyCardActivty.class);
					startActivity(intentMyCard);
					mMenu.collapse();
					break;
				case R.id.btn_goto_send:
					Intent intentSend = new Intent(getActivity(),SendMyInfoActivity.class);
					startActivity(intentSend);
//					Intent intent = new Intent();
////					intent.setAction(Constants.ACTION_GOTO_CARD);
//					getActivity().sendBroadcast(intent);
					mMenu.collapse();
					break;
				default:
					break;
			}
		}
	};

	private void showAddInfomationTip() {
		DialogUtils.showDialog(getActivity(), getString(R.string.no_info_tip), null, new DialogUtils.OnClickCallBack() {
			@Override
			public void confirm() {
				startActivity(new Intent(getActivity(), AddInfo.class));
			}

			@Override
			public void cancel() {

			}
		});
	}
}
