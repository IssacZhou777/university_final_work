package edu.jiangnan.dm.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * Created by Jelly Zhou on 2015/4/6.
 */
public class myViewPagerAdapter extends PagerAdapter {

    ArrayList<View> mPageViews = new ArrayList<View>();

    public myViewPagerAdapter(ArrayList<View> pages) {
        this.mPageViews = pages;
    }
    @Override
    public int getCount() {
        return mPageViews.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object o) {
        return view == o;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        container.addView(mPageViews.get(position));
        return mPageViews.get(position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(mPageViews.get(position));
    }
}
