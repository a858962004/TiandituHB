package com.gangbeng.tiandituhb.tiandituMap;

import android.os.Environment;
import android.util.Log;

import com.esri.android.map.TiledServiceLayer;
import com.esri.core.geometry.Envelope;
import com.esri.core.geometry.Point;
import com.esri.core.geometry.SpatialReference;
import com.esri.core.io.UserCredentials;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.RejectedExecutionException;

/**
 * Created by admin on 2016/6/22.
 */
public class TianDiTuTiledMapServiceLayer extends TiledServiceLayer {

    private TianDiTuTiledMapServiceType _mapType;
    private TileInfo tiandituTileInfo;

    public TianDiTuTiledMapServiceLayer() {
        this(null, null, true);
    }

    public TianDiTuTiledMapServiceLayer(TianDiTuTiledMapServiceType mapType) {
        this(mapType, null, true);
    }

    public TianDiTuTiledMapServiceLayer(TianDiTuTiledMapServiceType mapType, UserCredentials usercredentials) {
        this(mapType, usercredentials, true);
    }

    public TianDiTuTiledMapServiceLayer(TianDiTuTiledMapServiceType mapType, UserCredentials usercredentials, boolean flag) {
        super("");
        this._mapType = mapType;
        setCredentials(usercredentials);

        if (flag)
            try {
                getServiceExecutor().submit(new Runnable() {

                    public final void run() {
                        a.initLayer();
                    }

                    final TianDiTuTiledMapServiceLayer a;


                    {
                        a = TianDiTuTiledMapServiceLayer.this;
                        //super();
                    }
                });
                return;
            } catch (RejectedExecutionException _ex) {
            }
    }

    public TianDiTuTiledMapServiceType getMapType() {
        return this._mapType;
    }

    protected void initLayer() {
        this.buildTileInfo();
        this.setFullExtent(new Envelope(-180, -90, 180, 90));
//        this.setDefaultSpatialReference(SpatialReference.create(4490));   //CGCS2000
        this.setDefaultSpatialReference(SpatialReference.create(4490));
        this.setInitialExtent(new Envelope(90.52, 33.76, 113.59, 42.88));
        super.initLayer();
    }

    public void refresh() {
        try {
            getServiceExecutor().submit(new Runnable() {

                public final void run() {
                    if (a.isInitialized())
                        try {
                            a.b();
                            a.clearTiles();
                            return;
                        } catch (Exception exception) {
                            Log.e("ArcGIS", "Re-initialization of the layer failed.", exception);
                        }
                }

                final TianDiTuTiledMapServiceLayer a;


                {
                    a = TianDiTuTiledMapServiceLayer.this;
                    //super();
                }
            });
            return;
        } catch (RejectedExecutionException _ex) {
            return;
        }
    }

    final void b() throws Exception {

    }

    @Override
    protected byte[] getTile(final int level, final int col, final int row) throws Exception {
        /**
         *
         * */

        byte[] bytes = null;
        final String cachepath = Environment.getExternalStorageDirectory() + File.separator + "Tianditu_LF" + File.separator + "map_zg" + File.separator + _mapType;
        //根据图层、行、列找本地数据
        bytes = getOfflineCacheFile(cachepath, level, col, row);
        //如果本地数据为空，则调用网络数据。改接口2.0.0测试成功。最新的10.0貌似没有这个接口。具体自个找找吧，我也没有测过
        if (bytes == null) {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            URL sjwurl = new URL(this.getTianDiMapUrl(level, col, row));
//            String sjwurl = this.getTianDiMapUrl(level, col, row);
            HttpURLConnection httpUrl = null;
            BufferedInputStream bis = null;
            byte[] buf = new byte[1024];

            httpUrl = (HttpURLConnection) sjwurl.openConnection();
            httpUrl.connect();
            bis = new BufferedInputStream(httpUrl.getInputStream());

            while (true) {
                int bytes_read = bis.read(buf);
                if (bytes_read > 0) {
                    bos.write(buf, 0, bytes_read);
                } else {
                    break;
                }
            }
            bis.close();
            httpUrl.disconnect();
            bytes = bos.toByteArray();
            final byte[] finalBytes = bytes;
            AddOfflineCacheFile(cachepath, level, col, row, finalBytes);
        }
        return bytes;
    }


    @Override
    public TileInfo getTileInfo() {
        return this.tiandituTileInfo;
    }

    /**
     *
     * */
    private String getTianDiMapUrl(int level, int col, int row) {

        String url = new TianDiTuUrl(this._mapType, level, col, row).generatUrl();
        return url;
    }

    private void buildTileInfo() {
        Point originalPoint = new Point(-180, 90);

        double[] res = {
                1.40625,
                0.703125,
                0.3515625,
                0.17578125,
                0.087890625,
                0.0439453125,
                0.02197265625,
                0.010986328125,
                0.0054931640625,
                0.00274658203125,
                0.001373291015625,
                0.0006866455078125,
                0.00034332275390625,
                0.000171661376953125,
                8.58306884765629E-05,
                4.29153442382814E-05,
                2.14576721191407E-05,
                1.07288360595703E-05,
                5.36441802978515E-06,
                2.68220901489258E-06,
                1.34110450744629E-06
        };
        double[] scale = {
                590995197.14166909755553014475,
                295497598.57083454877776507238,
                147748799.28541727438888253619,
                73874399.642708637194441268094,
                36937199.821354318597220634047,
                18468599.910677159298610317023,
                9234299.955338579649305158512,
                4617149.9776692898246525792559,
                2308574.9888346449123262896279,
                1154287.494417322456163144814,
                577143.74720866122808157240698,
                288571.87360433061404078620349,
                144285.93680216530702039310175,
                72142.968401082653510196550873,
                36071.484200541326755098275436,
                18035.742100270663377549137718,
                9017.871050135331688774568859,
                4508.9355250676658443872844296,
                2254.4677625338329221936422148,
                1127.2338812669164610968211074,
                563.61694063345823054841055369
        };
        int levels = 21;
        int dpi = 96;
        int tileWidth = 256;
        int tileHeight = 256;
        this.tiandituTileInfo = new TileInfo(originalPoint, scale, res, levels, dpi, tileWidth, tileHeight);
        this.setTileInfo(this.tiandituTileInfo);
    }

    // 将图片保存到本地 目录结构可以随便定义 只要你找得到对应的图片
    private byte[] AddOfflineCacheFile(String cachepath, int level, int col, int row, byte[] bytes) {

        File file = new File(cachepath);
        if (!file.exists()) {
            file.mkdirs();
        }
        File levelfile = new File(cachepath + "/" + level);
        if (!levelfile.exists()) {
            levelfile.mkdirs();
        }
        File colfile = new File(cachepath + "/" + level + "/" + col);
        if (!colfile.exists()) {
            colfile.mkdirs();
        }
        File rowfile = new File(cachepath + "/" + level + "/" + col + "/" + row
                + ".dat");
        if (!rowfile.exists()) {
            try {
                FileOutputStream out = new FileOutputStream(rowfile);
                out.write(bytes);
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return bytes;

    }

    // 从本地获取图片
    private byte[] getOfflineCacheFile(String cachepath, int level, int col, int row) {
        byte[] bytes = null;
        File rowfile = new File(cachepath + "/" + level + "/" + col + "/" + row
                + ".dat");

        if (rowfile.exists()) {
            try {
                bytes = CopySdcardbytes(rowfile);
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        } else {
            bytes = null;
        }
        return bytes;
    }

    // 读取本地图片流
    public byte[] CopySdcardbytes(File file) throws IOException {
        FileInputStream in = new FileInputStream(file);

        ByteArrayOutputStream out = new ByteArrayOutputStream(1024);

        byte[] temp = new byte[1024];

        int size = 0;

        while ((size = in.read(temp)) != -1) {
            out.write(temp, 0, size);
        }
        in.close();
        byte[] bytes = out.toByteArray();
        return bytes;
    }
}
