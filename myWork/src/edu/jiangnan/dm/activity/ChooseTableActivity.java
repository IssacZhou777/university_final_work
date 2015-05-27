package edu.jiangnan.dm.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import edu.jiangnan.dm.R;
import edu.jiangnan.dm.adapter.myViewPagerAdapter;
import edu.jiangnan.dm.listener.OnSwipeTouchListener;
import edu.jiangnan.dm.tablewebviews.TableAmerica;
import edu.jiangnan.dm.tablewebviews.TableChina;
import edu.jiangnan.dm.tablewebviews.TableModelActivity;
import net.qiujuer.genius.widget.GeniusTextView;
import java.util.ArrayList;

/**
 * Created by Jelly Joe on 2015/4/24.
 */
public class ChooseTableActivity extends Activity {

    private ViewPager mViewpage;
    private myViewPagerAdapter mAdapter;
    private GeniusTextView mTableName;
    private int mBackgroudGroup[] = {
            R.drawable.america,
            R.drawable.register,
            R.drawable.chinagoout
    };
    private int[] dotsIds = {R.id.dot0, R.id.dot1, R.id.dot2};
    private ArrayList<View> dots = new ArrayList<View>();
    private String[] mTableNames ;
    private int nowPosition = 0;
    private boolean isPageScrolling = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_table);
        mTableNames = new String[] {getString(R.string.america), getString(R.string.register), getString(R.string.chinese_aboard)};
        initView();

    }

    private void initView() {
        mViewpage = (ViewPager) findViewById( R.id.table_viewpage);
        mTableName = (GeniusTextView) findViewById(R.id.table_name);
        mTableName.setText(mTableNames[0]);
        Button btnChoose = (Button)findViewById(R.id.choose_table);
        btnChoose.setOnClickListener(btnChooseClicked);
        ArrayList<View> pageViews = new ArrayList<View>();

        int length = mBackgroudGroup.length;
        for (int i = 0; i < length; i++ ) {
            View view = getLayoutInflater().inflate(R.layout.viewpage_item, null);
            view.setBackgroundResource(mBackgroudGroup[i]);
            pageViews.add(view);

            View dot = findViewById(dotsIds[i]);
            dots.add(dot);
        }

        mAdapter = new myViewPagerAdapter(pageViews);
        mViewpage.setAdapter(mAdapter);
        mViewpage.setCurrentItem(0);
        updateDotsState(0);
        mViewpage.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int font, float offset, int latter) {
                isPageScrolling = true;
            }

            @Override
            public void onPageSelected(int position) {
                updateDotsState(position);
                mTableName.setText(mTableNames[position]);
                nowPosition = position;
                isPageScrolling = false;
            }

            @Override
            public void onPageScrollStateChanged(int postion) {
                isPageScrolling = false;
            }
        });
    }

    private void updateDotsState( int position ) {
        int length = dots.size();
        for (int i = 0; i < length; i++ ) {
            dots.get(i).setSelected(false);
        }
        dots.get(position).setSelected(true);
    }

    private View.OnClickListener btnChooseClicked = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            if( nowPosition == 0 ) {
                startActivity(new Intent(ChooseTableActivity.this, TableAmerica.class));
                if ( !isFinishing() ) {
                    finish();
                }
            }

            if( nowPosition == 1 ) {
                startActivity(new Intent(ChooseTableActivity.this, TableModelActivity.class));
                if ( !isFinishing() ) {
                    finish();
                }
            }

            if( nowPosition == 2 ) {
                startActivity(new Intent(ChooseTableActivity.this, TableChina.class));
                if ( !isFinishing() ) {
                    finish();
                }
            }

        }
    };

    private OnSwipeTouchListener swipeTouchListener = new OnSwipeTouchListener() {
        @Override
        public void onSwipeRight() {
            super.onSwipeRight();
            if( !isPageScrolling || mViewpage.getCurrentItem() == 0 ) {
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
