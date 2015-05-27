package edu.jiangnan.dm.activity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import edu.jiangnan.dm.Model.infoModel;
import edu.jiangnan.dm.R;
import edu.jiangnan.dm.listener.OnSwipeTouchListener;
import edu.jiangnan.dm.util.GetDataUtils;
import net.qiujuer.genius.widget.GeniusCheckBox;

import java.util.ArrayList;

public class SendMyInfoActivity extends Activity{

	private int checkBoxIds[];
	private ArrayList<GeniusCheckBox> checkBoxs;
	private GeniusCheckBox selectAll;
	private boolean isAll = false;
	private String infos[];
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activiity_send_my_info);
		checkBoxs = new ArrayList<GeniusCheckBox>();
		checkBoxIds = new int[]{R.id.cname, R.id.csex, R.id.cnation, R.id.cnationality, R.id.cbirthday,
				R.id.cidnum, R.id.ctel, R.id.cemail, R.id.cfamily_address, R.id.ccurrent_address,
				R.id.cjob, R.id.corganization, R.id.corgan_address, R.id.cpolitical_statue};
		infoModel model = new infoModel();
		model = GetDataUtils.getDataFromDB(this);
		getData(model);
		initView();
	}

	private void initView() {
		int length = checkBoxIds.length;
		for (int i = 0; i < length; i++) {
			GeniusCheckBox view = (GeniusCheckBox) findViewById(checkBoxIds[i]);
			checkBoxs.add(view);
		}
		selectAll = (GeniusCheckBox) findViewById(R.id.select_all);
		selectAll.setChecked(false);
		selectAll.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				isAll = !isAll;
				selectAll(isAll);
			}
		});
		Button btnSend = (Button) findViewById(R.id.send);
		btnSend.setOnClickListener(btnSendClicked);

	}
	private void selectAll(boolean isAll) {
			int length = checkBoxs.size();
			for ( int i = 0; i < length; i++) {
				checkBoxs.get(i).setChecked(isAll);
			}
	}

	private View.OnClickListener btnSendClicked = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			Uri uri = Uri.parse("smsto:");
			Intent intent = new Intent(Intent.ACTION_SENDTO, uri);
			intent.putExtra("sms_body", generateMsgStr());
			startActivity(intent);
		}
	};

	private void getData(infoModel model) {
		infos = new String[]{model.getName(), model.getSex(), model.getNation(), model.getNationality(),
				model.getBirthday(), model.getIDCardNum(), model.getTelephone(), model.getEmail(),
				model.getFamilyAddress(), model.getCurrentAddress(), model.getJob(), model.getOrganization(),
				model.getOrganAddress(), model.getPoliticalStatue()};
	}

	private String generateMsgStr() {
		String keywords[] = {getString(R.string.keyword_name), getString(R.string.keyword_sex),
				getString(R.string.keyword_nation), getString(R.string.keyword_nationality), getString(R.string.keyword_birthday),
				getString(R.string.keyword_id_num), getString(R.string.keyword_telephone), getString(R.string.keywords_email),
				getString(R.string.keyword_family_address), getString(R.string.keyword_current_address), getString(R.string.keyword_job),
				getString(R.string.keyword_organization), getString(R.string.keyword_organ_address), getString(R.string.keyword_politcal_status),};

		String msg = "";
		int length = checkBoxs.size();
		for(int i= 0; i < length; i++) {
			if(checkBoxs.get(i).isChecked()) {
				msg = msg + keywords[i] + "：" + infos[i] + "\n";
			}
		}
		return  msg;
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
}