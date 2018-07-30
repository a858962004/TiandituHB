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
        StringBuilder url=new StringBuilder("http://222.222.66.179:8719/");
//        Random random=new Random();
//        int subdomain = (random.nextInt(6) + 1);
//        url.append(subdomain);
//        String url = "";
        level=level-9;

        switch(this.tiandituMapServiceType){
            case VEC_C:
//                url = "http://www.bjmap.gov.cn/services/ogc/wmts/12?SERVICE=WMTS&REQUEST=GetTile&VERSION=1.0.0&LAYER=12&STYLE=default&TILEMATRIXSET=CustomCRS4326ScaleBJMap&TILEMATRIX=" + level + "&TILEROW=" + row + "&TILECOL=" + col + "&FORMAT=image/png";
                url.append("newmapserver4/ogc/tianditu/lfgzvector/wmts?SERVICE=WMTS&REQUEST=GetTile&VERSION=1.0.0&LAYER=lfgzvector&STYLE=Default&TILEMATRIXSET=TileMatrixSet_0&TILEMATRIX=").append(this.level).append("&TILEROW=").append(this.row).append("&TILECOL=").append(this.col).append("&FORMAT=image/png");
                break;
            case CVA_C:
//                url = "http://www.bjmap.gov.cn/services/ogc/wmts/13?SERVICE=WMTS&REQUEST=GetTile&VERSION=1.0.0&LAYER=13&STYLE=default&TILEMATRIXSET=CustomCRS4326ScaleBJMapAnno&TILEMATRIX=" + level + "&TILEROW=" + row + "&TILECOL=" + col + "&FORMAT=image/png";
                url.append("newmap/ogc/tianditu/lfgzvectorbz/wmts?SERVICE=WMTS&REQUEST=GetTile&VERSION=1.0.0&LAYER=lfgzvectorbz&STYLE=Default&TILEMATRIXSET=TileMatrixSet_0&TILEMATRIX=").append(this.level).append("&TILEROW=").append(this.row).append("&TILECOL=").append(this.col).append("&FORMAT=image/png");
                break;
            case CIA_C:
//                url = "http://www.bjmap.gov.cn/services/ogc/wmts/14?SERVICE=WMTS&REQUEST=GetTile&VERSION=1.0.0&LAYER=14&STYLE=default&TILEMATRIXSET=CustomCRS4326ScaleBJImageMap&TILEMATRIX=" + level + "&TILEROW=" + row + "&TILECOL=" + col + "&FORMAT=image/png";
                url.append("newmapserver4/ogc/tianditu/lfgzimage/wmts?SERVICE=WMTS&REQUEST=GetTile&VERSION=1.0.0&LAYER=lfgzimage&STYLE=default&TILEMATRIXSET=TileMatrixSet_0&TILEMATRIX=").append(this.level).append("&TILEROW=").append(this.row).append("&TILECOL=").append(this.col).append("&FORMAT=image/jpeg");
                break;
            case IMG_C:
//                url = "http://www.bjmap.gov.cn/services/ogc/wmts/15?SERVICE=WMTS&REQUEST=GetTile&VERSION=1.0.0&LAYER=15&STYLE=default&TILEMATRIXSET=CustomCRS4326ScaleBJImageAnno&TILEMATRIX=" + level + "&TILEROW=" + row + "&TILECOL=" + col + "&FORMAT=image/png";
                url.append("newmap/ogc/tianditu/lfgzimagebz/wmts?SERVICE=WMTS&REQUEST=GetTile&VERSION=1.0.0&LAYER=lfgzimagebz&STYLE=default&TILEMATRIXSET=TileMatrixSet_0&TILEMATRIX=").append(this.level).append("&TILEROW=").append(this.row).append("&TILECOL=").append(this.col).append("&FORMAT=image/jpeg");
                break;
        }
//        Log.i("TAG",url.toString());
        return url.toString();
    }

}
