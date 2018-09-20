package com.gangbeng.tiandituhb.tiandituMap;

/**
 * Created by admin on 2016/6/22.
 */
public class TianDiTuLFUrl {

    private TianDiTuTiledMapServiceType tiandituMapServiceType;
    private int level;
    private int col;
    private int row;

    public TianDiTuLFUrl(TianDiTuTiledMapServiceType tiandituMapServiceType, int level, int col, int row) {
        this.tiandituMapServiceType = tiandituMapServiceType;
        this.level = level;
        this.col = col;
        this.row = row;
    }

    public String generatUrl(){
        /**
         * 天地图矢量、影像
         * */
        StringBuilder url=new StringBuilder("http://222.222.66.230/newmapserver4/");
        level=level-9;

        switch(this.tiandituMapServiceType){
            /**
             * 天地图矢量
             * */
            case VEC_C:
//                url = "http://www.bjmap.gov.cn/services/ogc/wmts/12?SERVICE=WMTS&REQUEST=GetTile&VERSION=1.0.0&LAYER=12&STYLE=default&TILEMATRIXSET=CustomCRS4326ScaleBJMap&TILEMATRIX=" + level + "&TILEROW=" + row + "&TILECOL=" + col + "&FORMAT=image/png";
                url.append("tianditu/tianditu/lfgzvector/wmts?SERVICE=WMTS&REQUEST=GetTile&VERSION=1.0.0&LAYER=lfgzvector&STYLE=Default&TILEMATRIXSET=TileMatrixSet_0&TILEMATRIX=").append(this.level).append("&TILEROW=").append(this.row).append("&TILECOL=").append(this.col).append("&FORMAT=image/png");
                break;
            /**
             * 天地图矢量标注
             * */
            case CVA_C:
//                url = "http://www.bjmap.gov.cn/services/ogc/wmts/13?SERVICE=WMTS&REQUEST=GetTile&VERSION=1.0.0&LAYER=13&STYLE=default&TILEMATRIXSET=CustomCRS4326ScaleBJMapAnno&TILEMATRIX=" + level + "&TILEROW=" + row + "&TILECOL=" + col + "&FORMAT=image/png";
                url.append("ogc/tianditu/lfgzimagebz/wmts?SERVICE=WMTS&REQUEST=GetTile&VERSION=1.0.0&LAYER=lfgzimagebz&STYLE=Default&TILEMATRIXSET=TileMatrixSet_0&TILEMATRIX=").append(this.level).append("&TILEROW=").append(this.row).append("&TILECOL=").append(this.col).append("&FORMAT=image/png");
                break;
            /**
             * 天地图影像
             * */
            case IMG_C:
//                url = "http://www.bjmap.gov.cn/services/ogc/wmts/15?SERVICE=WMTS&REQUEST=GetTile&VERSION=1.0.0&LAYER=15&STYLE=default&TILEMATRIXSET=CustomCRS4326ScaleBJImageAnno&TILEMATRIX=" + level + "&TILEROW=" + row + "&TILECOL=" + col + "&FORMAT=image/png";
                url.append("tianditu/tianditu/lfgzimage/wmts?SERVICE=WMTS&REQUEST=GetTile&VERSION=1.0.0&LAYER=lfgzimage&STYLE=default&TILEMATRIXSET=TileMatrixSet_0&TILEMATRIX=").append(this.level).append("&TILEROW=").append(this.row).append("&TILECOL=").append(this.col).append("&FORMAT=image/jpeg");
                break;
            case XZQ_C:
                url.append("ogc/tianditu/Ifjxnew/wmts?SERVICE=WMTS&REQUEST=GetTile&VERSION=1.0.0&LAYER=Ifjxnew&STYLE=default&TILEMATRIXSET=TileMatrixSet_0&TILEMATRIX=").append(this.level).append("&TILEROW=").append(this.row).append("&TILECOL=").append(this.col).append("&FORMAT=image/jpeg");
                break;

        }
//        Log.i("TAG",url.toString());
        return url.toString();
    }

}
