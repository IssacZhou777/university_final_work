package edu.jiangnan.dm.activity;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.PopupWindow;
import android.widget.TextView;
import edu.jiangnan.dm.Constants;
import edu.jiangnan.dm.Model.ContactModel;
import edu.jiangnan.dm.Model.infoModel;
import edu.jiangnan.dm.R;
import edu.jiangnan.dm.adapter.myViewPagerAdapter;
import edu.jiangnan.dm.util.GetDataUtils;
import edu.jiangnan.dm.util.QRCodeUtils;

import java.util.ArrayList;

/**
 * Created by Jelly Zhou on 2015/4/9.
 */
public class MyCardActivty extends Activity {

    private ViewPager mViewPage;
    private ArrayList<View> mPageViews;
    private myViewPagerAdapter viewPagerAdapter;
    private ArrayList<View> dots;
    private String[] cardIDs= {Constants.CARD_ONE, Constants.CARD_TWO, Constants.CARD_THREE, Constants.CARD_FOUR};
    private LayoutInflater mInflater;
    private infoModel model;
    private Context mContext;
    private String currentID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_card);
        mContext = this;
        initView();
    }

    private void initView() {
        mViewPage = (ViewPager) findViewById(R.id.my_card_views);
        mInflater = getLayoutInflater();
        mPageViews = new ArrayList<View>();
        model = GetDataUtils.getDataFromDB(this);
        initSubView();

        //init dots
        int[] dotId = {R.id.dot0, R.id.dot1, R.id.dot2, R.id.dot3};
        dots = new ArrayList<View>();
        for( int i = 0; i < dotId.length; i++) {
            View dot = findViewById(dotId[i]);
            dots.add(dot);
        }
        updateDotsState(0);
    }

    private void initSubView() {
        mPageViews.add(mInflater.inflate( R.layout.mycard1_layout,null ));
        mPageViews.add( mInflater.inflate( R.layout.mycard2_layout,null ));
        mPageViews.add(mInflater.inflate( R.layout.mycard3_layout,null ));
        mPageViews.add( mInflater.inflate( R.layout.mycard4_layout,null ));
        if( model == null ) {
            return;
        }

        for(int i = 0 ; i < mPageViews.size(); i++) {
            bindView(mPageViews.get(i), i);
            mPageViews.get(i).setOnClickListener(onClickListener);
        }

        viewPagerAdapter = new myViewPagerAdapter(mPageViews);
        mViewPage.setAdapter(viewPagerAdapter);
        mViewPage.setCurrentItem(0);
        currentID = cardIDs[0];
        mViewPage.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int position) {
                updateDotsState( position );
                currentID = cardIDs[position];
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });

    }

    private void bindView(View view, int position) {
        TextView mName = (TextView)view.findViewById(R.id.name);
        TextView mJob = (TextView)view.findViewById(R.id.job);
        TextView mTelephone = (TextView)view.findViewById(R.id.telephone);
        TextView mEmail = (TextView)view.findViewById(R.id.email);
        if (position != 2) {
            TextView mOrgan = (TextView)view.findViewById(R.id.organization);
            mOrgan.setText(model.getOrganization());
        }
        TextView mAddress = (TextView)view.findViewById(R.id.address);
        mName.setText(model.getName());
        mJob.setText(model.getJob());
        mTelephone.setText(model.getTelephone());
        mEmail.setText(model.getEmail());
        mAddress.setText(model.getCurrentAddress());

        return;
    }

    private void updateDotsState( int position ) {
        for (int i = 0; i < dots.size(); i++ ) {
            dots.get(i).setSelected(false);
        }
        dots.get(position).setSelected(true);
    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            ContactModel contactModel = GetDataUtils.infoModel2ContactModel(model);
            QRCodeUtils.showQRcode(mContext, contactModel, currentID);
        }
    };

}
