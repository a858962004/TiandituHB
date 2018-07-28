package com.gangbeng.tiandituhb.adpter.viewholder;

import android.support.v4.app.Fragment;

/**
 * Description：
 * Created on 2017/3/2
 */
public class FragmentHolder {

    private Fragment fragment;

    private String title;

    public FragmentHolder() {
    }

    public FragmentHolder(Fragment fragment, String title) {
        this.fragment = fragment;
        this.title = title;
    }

    public Fragment getFragment() {
        return fragment;
    }

    public void setFragment(Fragment fragment) {
        this.fragment = fragment;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "FragmentHolder{" +
                "fragment=" + fragment +
                ", title='" + title + '\'' +
                '}';
    }
}
