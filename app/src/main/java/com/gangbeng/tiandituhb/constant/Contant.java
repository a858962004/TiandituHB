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
