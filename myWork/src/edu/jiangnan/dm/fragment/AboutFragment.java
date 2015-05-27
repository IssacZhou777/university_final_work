package edu.jiangnan.dm.fragment;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.widget.TextView;
import edu.jiangnan.dm.R;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import edu.jiangnan.dm.activity.DeveloperInfoActivity;

public class AboutFragment extends Fragment {

	private View root;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		root = inflater.inflate(R.layout.fragment_about, container, false);
		initView();
		return root;
	}

	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
	}

	private void initView() {
		TextView student = (TextView) root.findViewById(R.id.student);
		TextView tutor = (TextView) root.findViewById(R.id.tutor);
		student.setOnClickListener(onClickListener);
		tutor.setOnClickListener(onClickListener);
	}

	private View.OnClickListener onClickListener = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			switch ( v.getId() ) {
				case R.id.student :
					showDeveloperInfo(DeveloperInfoActivity.STUDENT);
					break;
				case R.id.tutor :
					showDeveloperInfo(DeveloperInfoActivity.TUTOR);
					break;
				default:break;
			}

		}
	};

	private void showDeveloperInfo( String id ) {
			Intent intent = new Intent(getActivity(), DeveloperInfoActivity.class);
			intent.putExtra("id", id);
			startActivity(intent);
	}
	

}
