package com.gangbeng.tiandituhb.constant;

import com.gangbeng.tiandituhb.gaodenaviutil.Gps;

/**
 * @author zhanghao
 * @date 2018-08-02
 */

public class Contant {
    private static  Contant mIns;
    private Gps startGps;
    private Gps endGps;
    private boolean localState=false;
    private boolean isnewest=true;
    private boolean isNormalQuit;
    private String updateUrl;
    private int maplevel=-1;
    private int newmaplevel=-1;

    public int getNewmaplevel() {
        return newmaplevel;
    }

    public void setNewmaplevel(int newmaplevel) {
        this.newmaplevel = newmaplevel;
    }

    public int getMaplevel() {
        return maplevel;
    }

    public void setMaplevel(int maplevel) {
        this.maplevel = maplevel;
    }

    public String getUpdateUrl() {
        return updateUrl;
    }

    public void setUpdateUrl(String updateUrl) {
        this.updateUrl = updateUrl;
    }

    public boolean isNormalQuit() {
        return isNormalQuit;
    }

    public void setNormalQuit(boolean normalQuit) {
        isNormalQuit = normalQuit;
    }

    public boolean isnewest() {
        return isnewest;
    }

    public void setIsnewest(boolean isnewest) {
        this.isnewest = isnewest;
    }

    public boolean isLocalState() {
        return localState;
    }

    public void setLocalState(boolean localState) {
        this.localState = localState;
    }

    public Gps getStartGps() {
        return startGps;
    }

    public void setStartGps(Gps startGps) {
        this.startGps = startGps;
    }

    public Gps getEndGps() {
        return endGps;
    }

    public void setEndGps(Gps endGps) {
        this.endGps = endGps;
    }

    public static Contant ins(){
        if (mIns == null) {
            mIns = new Contant();
        }
        return mIns;
    }
}
