package com.gangbeng.tiandituhb.tiandituMap;

import com.gangbeng.tiandituhb.utils.MyLogUtil;

import java.util.Random;

/**
 * Created by admin on 2016/6/22.
 */
public class TianDiTuUrl {

    private TianDiTuTiledMapServiceType tiandituMapServiceType;
    private int level;
    private int col;
    private int row;

    public TianDiTuUrl(TianDiTuTiledMapServiceType tiandituMapServiceType, int level, int col, int row) {
        this.tiandituMapServiceType = tiandituMapServiceType;
        this.level = level;
        this.col = col;
        this.row = row;

    }

    public String generatUrl(){
        /**
         * 天地图矢量、影像
         * */
        StringBuilder url=new StringBuilder("http://t");
        Random random=new Random();
        int subdomain = (random.nextInt(6) + 1);
        url.append(subdomain);
//        String url = "";
        switch(this.tiandituMapServiceType){
            case VEC_C:
//                url = "http://www.bjmap.gov.cn/services/ogc/wmts/12?SERVICE=WMTS&REQUEST=GetTile&VERSION=1.0.0&LAYER=12&STYLE=default&TILEMATRIXSET=CustomCRS4326ScaleBJMap&TILEMATRIX=" + level + "&TILEROW=" + row + "&TILECOL=" + col + "&FORMAT=image/png";
                url.append(".tianditu.com/DataServer?T=vec_c&X=").append(this.col).append("&Y=").append(this.row).append("&L=").append(this.level);

                break;
            case CVA_C:
//                url = "http://www.bjmap.gov.cn/services/ogc/wmts/13?SERVICE=WMTS&REQUEST=GetTile&VERSION=1.0.0&LAYER=13&STYLE=default&TILEMATRIXSET=CustomCRS4326ScaleBJMapAnno&TILEMATRIX=" + level + "&TILEROW=" + row + "&TILECOL=" + col + "&FORMAT=image/png";
                url.append(".tianditu.com/DataServer?T=cva_c&X=").append(this.col).append("&Y=").append(this.row).append("&L=").append(this.level);
                break;
            case CIA_C:
//                url = "http://www.bjmap.gov.cn/services/ogc/wmts/14?SERVICE=WMTS&REQUEST=GetTile&VERSION=1.0.0&LAYER=14&STYLE=default&TILEMATRIXSET=CustomCRS4326ScaleBJImageMap&TILEMATRIX=" + level + "&TILEROW=" + row + "&TILECOL=" + col + "&FORMAT=image/png";
                url.append(".tianditu.com/DataServer?T=cia_c&X=").append(this.col).append("&Y=").append(this.row).append("&L=").append(this.level);
                break;
            case IMG_C:
//                url = "http://www.bjmap.gov.cn/services/ogc/wmts/15?SERVICE=WMTS&REQUEST=GetTile&VERSION=1.0.0&LAYER=15&STYLE=default&TILEMATRIXSET=CustomCRS4326ScaleBJImageAnno&TILEMATRIX=" + level + "&TILEROW=" + row + "&TILECOL=" + col + "&FORMAT=image/png";
                url.append(".tianditu.com/DataServer?T=img_c&X=").append(this.col).append("&Y=").append(this.row).append("&L=").append(this.level);
                break;
            case XZQ_C:
//                level=level-9;
                level = level - 9;
//                url.append("http://222.222.66.179:8719/newmap/ogc/tianditu/Ifjxnew/wmts?SERVICE=WMTS&REQUEST=GetTile&VERSION=1.0.0&LAYER=Ifjxnew&STYLE=default&TILEMATRIXSET=TileMatrixSet_0&TILEMATRIX=").append(this.level).append("&TILEROW=").append(this.row).append("&TILECOL=").append(this.col).append("&FORMAT=image/jpeg");
                url=new StringBuilder("http://222.222.66.179:8719/newmap/ogc/lftdtgx/jjappcs1/wmts?SERVICE=WMTS&REQUEST=GetTile&VERSION=1.0.0&LAYER=jjappcs1&STYLE=default&TILEMATRIXSET=TileMatrixSet_0&TILEMATRIX=").append(this.level).append("&TILEROW=").append(this.row).append("&TILECOL=").append(this.col).append("&FORMAT=image/jpeg");
                MyLogUtil.showLog("maplayer",url.toString());
                break;
        }
//        Log.i("TAG",url.toString());
        return url.toString();
    }

}
