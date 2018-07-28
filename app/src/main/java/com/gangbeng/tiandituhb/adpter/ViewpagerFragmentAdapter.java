package com.gangbeng.tiandituhb.adpter;

import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.gangbeng.tiandituhb.adpter.viewholder.FragmentHolder;

import java.util.List;



/**
 * Description：ViewPager 嵌套 Fragment 适配器
 * Created on 2017/3/1
 * Author : 萧
 */
public class ViewpagerFragmentAdapter extends FragmentStatePagerAdapter {

    private static final String TAG = "ViewpagerFragmentAdapter";

    private List<Fragment> frList;

    private List<String> titles;

    private List<FragmentHolder> holders;

//    public ViewpagerFragmentAdapter(FragmentManager fm) {
//        super(fm);
//    }

    public ViewpagerFragmentAdapter(FragmentManager fm, List<FragmentHolder> holders) {
        super(fm);
        this.holders = holders;
    }

    public void setHolders(List<FragmentHolder> holders) {
        this.holders = holders;
    }

    public void setFrList(List<Fragment> frList) {
        this.frList = frList;
    }

    public void setTitles(List<String> titles) {
        this.titles = titles;
    }

    @Override
    public Fragment getItem(int position) {
        return holders == null ? null : holders.get(position).getFragment();
    }

    @Override
    public int getCount() {
//        int count1 = frList == null ? 0 : frList.size();
//        int count2 = titles == null ? 0 : titles.size();
//        return Math.min(count1, count2);
        return holders == null ? 0 : holders.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
//        return titles == null ? "" : titles.get(position);
        return holders == null ? "" : holders.get(position).getTitle();
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        return super.instantiateItem(container, position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        try {
            Glide.clear(container);

        } catch (Exception e) {
            e.printStackTrace();
        }
        super.destroyItem(container, position, object);
    }

    @Override
    public Parcelable saveState() {
        return super.saveState();
    }

    @Override
    public void restoreState(Parcelable state, ClassLoader loader) {
        super.restoreState(state, loader);
    }
}
