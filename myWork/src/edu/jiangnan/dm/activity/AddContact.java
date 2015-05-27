package edu.jiangnan.dm.activity;

import android.app.Activity;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import edu.jiangnan.dm.Constants;
import edu.jiangnan.dm.Model.ContactModel;
import edu.jiangnan.dm.R;
import edu.jiangnan.dm.adapter.myViewPagerAdapter;
import edu.jiangnan.dm.db.MySqlHelper;
import edu.jiangnan.dm.db.TContactColumn;
import edu.jiangnan.dm.listener.OnSwipeTouchListener;
import edu.jiangnan.dm.util.DialogUtils;
import edu.jiangnan.dm.util.GetDataUtils;
import edu.jiangnan.dm.util.MyPreference;
import net.qiujuer.genius.widget.GeniusEditText;
import net.qiujuer.genius.widget.GeniusTextView;

import java.util.ArrayList;

/**
 * Created by Jelly Zhou on 2015/3/25.
 */
public class AddContact extends Activity {

    private ContactModel mContactModel;
    private GeniusTextView btnCancel,btnSave;
    private GeniusEditText gevName,gevJob,gevTelephone,gevEmial,gevOrganization,gevAddress;
    private ViewPager viewPager;
    private ArrayList<View> mPageViews;
    private myViewPagerAdapter viewPagerAdapter;
    private ArrayList<View> dots;
    private String[] cardIDs= {Constants.CARD_ONE, Constants.CARD_TWO, Constants.CARD_THREE, Constants.CARD_FOUR};
    private boolean isPageScrolling = false;
    private int[] mBackgrounds = {
            R.drawable.card1_module,
            R.drawable.card2_module,
            R.drawable.card3_module,
            R.drawable.card4_module
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);
        initView();
        initViewPage();
    }

    private void initView() {
        //init TextView
        btnSave = (GeniusTextView)findViewById(R.id.gsave);
        btnSave.setOnClickListener(btnOnClicked);
        btnCancel = (GeniusTextView)findViewById(R.id.gcancel);
        btnCancel.setOnClickListener(btnOnClicked);
        //init EditView

        gevName = (GeniusEditText)findViewById(R.id.gname);
        gevJob = (GeniusEditText)findViewById(R.id.gjob);
        gevTelephone = (GeniusEditText)findViewById(R.id.gtelephone);
        gevEmial = (GeniusEditText)findViewById(R.id.gemail);
        gevOrganization = (GeniusEditText)findViewById(R.id.gorganization);
        gevAddress = (GeniusEditText)findViewById(R.id.gaddress);

        //init ViewPage
        viewPager = (ViewPager)findViewById(R.id.card_viewpage);
        //init dot
        int[] dotId = {R.id.dot0, R.id.dot1, R.id.dot2, R.id.dot3};
        dots = new ArrayList<View>();
        for( int i = 0; i < dotId.length; i++) {
            View dot = findViewById(dotId[i]);
            dots.add(dot);
        }


    }

    private View.OnClickListener btnOnClicked = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.gsave:
                    saveData();
                    break;
                case R.id.gcancel:
                    finish();
                    break;
                default:break;
            }
        }
    };

    private void saveData() {
        ContactModel contactModel = new ContactModel();
        contactModel = getViewData();
        int state = GetDataUtils.checkContactModel(contactModel);
        if ( GetDataUtils.isContactExist(this, contactModel.getTelephone())) {
            state = Constants.STATE_HAD_EXISTED;
        }
        if(state == Constants.STATE_OK) {
            putData2DB(contactModel);
        }else {
            DialogUtils.showTipsByState(this, state);
        }

    }

    private ContactModel getViewData() {
        ContactModel contactModel = new ContactModel();
        contactModel.setName(gevName.getText().toString());
        contactModel.setJob(gevJob.getText().toString());
        contactModel.setTelephone(gevTelephone.getText().toString());
        contactModel.setEmail(gevEmial.getText().toString());
        contactModel.setOrganization(gevOrganization.getText().toString());
        contactModel.setAddress(gevAddress.getText().toString());
        contactModel.setCardId(MyPreference.getInstance().getAddCardID());
        return contactModel;
    }

    private void putData2DB(ContactModel model) {
       MySqlHelper mySqlHelper = new MySqlHelper(AddContact.this,
                "user_info.db", null, 1);
        SQLiteDatabase db = mySqlHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(TContactColumn.NAME, model.getName());
        values.put(TContactColumn.JOB, model.getJob());
        values.put(TContactColumn.TELEPHONE, model.getTelephone());
        values.put(TContactColumn.EMAIL, model.getEmail());
        values.put(TContactColumn.ORGANIZATION, model.getOrganization());
        values.put(TContactColumn.ADDRESS, model.getAddress());
        values.put(TContactColumn.CARDID, model.getCardId());

        try{
            db.insert(Constants.CONTACT_TABLE_NAME, null, values);
        }catch (Exception e) {
            e.printStackTrace();
        }finally {
            db.close();
            if(!isFinishing()) {
                this.finish();
            }
        }
    }

    private void initViewPage() {
        mPageViews = new ArrayList<View>();
        LayoutInflater inflater = getLayoutInflater();
        for (int i = 0; i < mBackgrounds.length; i++) {
            View view = inflater.inflate(R.layout.item_viewpage_1, null);
            view.setBackgroundResource(mBackgrounds[i]);
            mPageViews.add(view);
        }
        viewPagerAdapter = new myViewPagerAdapter(mPageViews);
        viewPager.setAdapter(viewPagerAdapter);
        MyPreference.getInstance().storeAddCardID(Constants.CARD_ONE);
        updateDotsState(0);
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float offset, int i1) {
                    isPageScrolling = true;
            }

            @Override
            public void onPageSelected(int position) {
                MyPreference.getInstance().storeAddCardID(cardIDs[position]);
                updateDotsState(position);
            }

            @Override
            public void onPageScrollStateChanged(int position) {
                isPageScrolling = false;
            }
        });
        viewPager.setCurrentItem(0);
    }

    private void updateDotsState( int position ) {
        for (int i = 0; i < dots.size(); i++ ) {
            dots.get(i).setSelected(false);
        }
        dots.get(position).setSelected(true);
    }
    private OnSwipeTouchListener swipeTouchListener = new OnSwipeTouchListener() {
        @Override
        public void onSwipeRight() {
            super.onSwipeRight();
            if( !isPageScrolling || viewPager.getCurrentItem() == 0 ) {
                finish();
            }
        }
    };

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        swipeTouchListener.onTouch(ev);
        return super.dispatchTouchEvent(ev);
    }
}
