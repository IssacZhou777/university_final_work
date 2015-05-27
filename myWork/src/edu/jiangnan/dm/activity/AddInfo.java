package edu.jiangnan.dm.activity;



import java.io.ByteArrayOutputStream;
import java.util.Calendar;

import android.app.AlertDialog;
import android.view.MotionEvent;
import edu.jiangnan.dm.R;
import edu.jiangnan.dm.Model.infoModel;
import edu.jiangnan.dm.db.MySqlHelper;
import edu.jiangnan.dm.db.TUserInfoColumn;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Spinner;
import edu.jiangnan.dm.listener.OnSwipeTouchListener;
import edu.jiangnan.dm.util.GetDataUtils;
import edu.jiangnan.dm.util.MyPreference;
import net.qiujuer.genius.GeniusUI;
import net.qiujuer.genius.widget.GeniusCheckBox;
import net.qiujuer.genius.widget.GeniusEditText;

/**
 * 
 * @author Jelly Zhou 2015-1-29
 *
 */
public class AddInfo extends Activity {
	
	private ImageView uHeadImg;
	private GeniusEditText uName,uNationality, uBirthday, uIDcardNum, uTel, uEmail, uFamily_address, uCurrent_address,
			uJob,  uNation, uOrganization, uOrganizationAddress, uPoliticalStatue;
//	private Spinner uNation;
	private Button Save,Cancel,btnChoose;
	private GeniusCheckBox male,female;
	private final int CODE = 1;
	private MySqlHelper mySqlHelper;
	private SQLiteDatabase db;
	private final String USERINFO_TABLE_NAME="userInfo";
	private Calendar calendar;
	private DatePickerDialog datePickerDialog;
	private infoModel mInfoModel;
	private boolean isMale = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_addinfo);
		mySqlHelper = new MySqlHelper(AddInfo.this,
				"user_info.db", null, 1);
		db = mySqlHelper.getWritableDatabase();
		initView();

		mInfoModel = GetDataUtils.getDataFromDB(this);
		if( MyPreference.getInstance().getInfoAdded() ) {
			initViewData(mInfoModel);
		}

	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}
	
	private void initView(){
		mInfoModel=new infoModel();
		uHeadImg=(ImageView) findViewById(R.id.head);
		uHeadImg.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				chooseImage();
				
			}
		});
		uName = (GeniusEditText)findViewById(R.id.name);
		uNationality = (GeniusEditText)findViewById(R.id.nationality);
		uBirthday = (GeniusEditText)findViewById(R.id.birthday);
		uIDcardNum = (GeniusEditText)findViewById(R.id.id_num);
		uTel = (GeniusEditText)findViewById(R.id.tel);
		uEmail = (GeniusEditText)findViewById(R.id.email);
		uFamily_address = (GeniusEditText)findViewById(R.id.family_address);
		uCurrent_address = (GeniusEditText)findViewById(R.id.current_address);
		uNation = (GeniusEditText)findViewById(R.id.nation);
		uJob = (GeniusEditText) findViewById(R.id.job);
		uOrganization = (GeniusEditText) findViewById(R.id.organization);
		uOrganizationAddress = (GeniusEditText) findViewById(R.id.organization_address);
		uPoliticalStatue = (GeniusEditText) findViewById(R.id.political_statue);

		male = (GeniusCheckBox) findViewById(R.id.male);
		male.setOnClickListener(checkBoxClicked);
		female = (GeniusCheckBox) findViewById(R.id.female);
		female.setOnClickListener(checkBoxClicked);
		
		
		Save=(Button) findViewById(R.id.save);
		Save.setOnClickListener(myButtonClicked);
		Cancel=(Button) findViewById(R.id.cancel);
		Cancel.setOnClickListener(myButtonClicked);
		
		calendar = Calendar.getInstance();
		int year = calendar.get(calendar.YEAR);
		int month = calendar.get(calendar.MONTH);
		int day = calendar.get(calendar.DAY_OF_MONTH);
		datePickerDialog = new DatePickerDialog(AddInfo.this,
				AlertDialog.THEME_HOLO_LIGHT,listener, year, month, day);
		datePickerDialog.setVolumeControlStream(2);
		
		btnChoose=(Button)findViewById(R.id.btnChooseDate);
		btnChoose.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				datePickerDialog.show();
			}
		});
		
	}
	
	OnClickListener myButtonClicked=new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.save:
				getData();
				parseData();
				break;
			case R.id.cancel:
				finish();
			default:
				break;
			}
			
		}
	};
	
	/**
	 * get data from view
	 * 	 */
	private void getData(){
		mInfoModel.setName(uName.getText().toString());
		mInfoModel.setNationality(uNationality.getText().toString());
		mInfoModel.setBirthday(uBirthday.getText().toString());
		mInfoModel.setIDCardNum(uIDcardNum.getText().toString());
		mInfoModel.setTelephone(uTel.getText().toString());
		mInfoModel.setEmail(uEmail.getText().toString());
		mInfoModel.setFamilyAddress(uFamily_address.getText().toString());
		mInfoModel.setCurrentAddress(uCurrent_address.getText().toString());
		mInfoModel.setJob(uJob.getText().toString());
		mInfoModel.setOrganization(uOrganization.getText().toString());
		mInfoModel.setNation(uNation.getText().toString());
		mInfoModel.setSex(isMale ? "男" : "女" );
		mInfoModel.setOrganAddress(uOrganizationAddress.getText().toString());
		mInfoModel.setPoliticalStatue(uPoliticalStatue.getText().toString());
		
	}

	/**
	 * get the head image from
	 */
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
				Cursor cursor = getContentResolver().query(selectedImagePath,
						null, null, null, null);

				if (cursor == null) {
					
				} else {
					cursor.moveToFirst();
					String img = cursor.getString(1);
					// System.out.println("img:" + img);
					//
					// student.setImgPath(img);
					cursor.close();
					Bitmap bitmap = BitmapFactory.decodeFile(img);

					uHeadImg.setImageBitmap(bitmap);
				}
			}
		}
	}
	
	/**
	 * put data into DB
	 */
	private void parseData(){
		if(mInfoModel.getName().equals("") || mInfoModel.getTelephone().equals("") ){
			//TODO show dialog
		}else{
		
		Bitmap bitmap = ((BitmapDrawable)uHeadImg.getDrawable()).getBitmap();
		ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
		// 压缩bitmap到ByteArrayOutputStream
		bitmap.compress(CompressFormat.PNG, 100, byteOut);
		mInfoModel.setImgBytes(byteOut.toByteArray());
		
		ContentValues values = new ContentValues();
		values.put(TUserInfoColumn.USER_NAME, mInfoModel.getName());
		values.put(TUserInfoColumn.USER_SEX, mInfoModel.getSex());
		values.put(TUserInfoColumn.USER_NATION, mInfoModel.getNation());
		values.put(TUserInfoColumn.USER_NATIONALITY, mInfoModel.getNationality());
		values.put(TUserInfoColumn.USER_BIRTHDAY, mInfoModel.getBirthday());
		values.put(TUserInfoColumn.USER_IDCARD_NUM, mInfoModel.getIDCardNum());
		values.put(TUserInfoColumn.USER_TELEPHONE, mInfoModel.getTelephone());
		values.put(TUserInfoColumn.USER_EMAIL, mInfoModel.getEmail());
		values.put(TUserInfoColumn.USER_FAMILY_ADDRESS, mInfoModel.getFamilyAddress());
		values.put(TUserInfoColumn.USER_CURRENT_ADDRESS, mInfoModel.getCurrentAddress());
		values.put(TUserInfoColumn.USER_IMAGE, mInfoModel.getImgBytes());
		values.put(TUserInfoColumn.USER_JOB, mInfoModel.getJob());
		values.put(TUserInfoColumn.USER_ORGANIZATION, mInfoModel.getOrganization());
		values.put(TUserInfoColumn.USER_ORGAN_ADDRESS, mInfoModel.getOrganAddress());
		values.put(TUserInfoColumn.USER_POLITICAL_STATUE, mInfoModel.getPoliticalStatue());
		
		try{
			Cursor mCursor=db.rawQuery("select * from "+USERINFO_TABLE_NAME, null);
			int resultCount=mCursor.getCount();
			if(resultCount <= 0){
				db.insert(USERINFO_TABLE_NAME, null, values);
				MyPreference.getInstance().storeInfoAdded(true);
			}else{
				db.update(USERINFO_TABLE_NAME, values, null, null);
				MyPreference.getInstance().storeInfoAdded(true);
			}

			
			if(mCursor!=null){
				mCursor.close();
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		
		this.finish();
		
	}
	}
	
	private DatePickerDialog.OnDateSetListener listener = new DatePickerDialog.OnDateSetListener() {

		@Override
		public void onDateSet(DatePicker view, int year, int monthOfYear,
				int dayOfMonth) {
			uBirthday.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
		}
	};
	
	/**
	 * init view data 
	 */
	private void initViewData(infoModel model ){
		if(mInfoModel == null) {
			return;
		}

		male.setChecked(false);
		female.setChecked(false);

		if ( model.getSex().equals("男") ) {
			male.setChecked(true);
			isMale = true;
		} else {
			female.setChecked(true);
			isMale = false;
		}
		uName.setText(model.getName());
		uIDcardNum.setText(model.getIDCardNum());
		uNationality.setText(model.getNationality());
		uBirthday.setText(model.getBirthday() + "");
		uTel.setText(model.getTelephone());
		uEmail.setText(model.getEmail());
		uFamily_address.setText(model.getFamilyAddress());
		uCurrent_address.setText(model.getCurrentAddress());
		uNation.setText(model.getNation());
		byte[] imageBytes = model.getImgBytes();
		if(imageBytes != null) {
			Bitmap bitmap = BitmapFactory.decodeByteArray(imageBytes, 0,
					imageBytes.length);
			uHeadImg.setImageBitmap(bitmap);
		}
		uJob.setText(model.getJob());
		uOrganization.setText(model.getOrganization());
		uOrganizationAddress.setText(model.getOrganAddress());
		uPoliticalStatue.setText(model.getPoliticalStatue());
	}

	private OnSwipeTouchListener swipeTouchListener = new OnSwipeTouchListener() {
		@Override
		public void onSwipeRight() {
			super.onSwipeRight();
			finish();
		}
	};

	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		swipeTouchListener.onTouch(ev);
		return super.dispatchTouchEvent(ev);
	}

	private OnClickListener checkBoxClicked = new OnClickListener() {
		@Override
		public void onClick(View v) {

			male.setChecked(false);
			female.setChecked(false);

			switch (v.getId()) {
				case R.id.male :
					isMale = true;
					male.setChecked(true);
					break;
				case R.id.female :
					female.setChecked(true);
					isMale = false;
					break;
				default:break;
			}
		}
	};
}
