package com.gangbeng.tiandituhb.bean;

import java.util.List;

/**
 * @author zhanghao
 * @date 2018-09-25
 */

public class QuanjingBean {

    /**
     * Admission : GS(2017)3519
     * Date : 20170705
     * DeviceHeight : 2.32
     * FileTag : pano_cfg
     * Heading : 4.31
     * ID : 09002200121707051532215399S
     * ImgLayer : [{"BlockX":2,"BlockY":1,"ImgFormat":"jpg","ImgLevel":1},{"BlockX":4,"BlockY":2,"ImgFormat":"jpg","ImgLevel":2},{"BlockX":8,"BlockY":4,"ImgFormat":"jpg","ImgLevel":3},{"BlockX":16,"BlockY":8,"ImgFormat":"jpg","ImgLevel":4}]
     * LayerCount : 4
     * Links : []
     * Mode : day
     * MoveDir : 4.308
     * NorthDir : 265.69
     * Obsolete : 0
     * Pitch : 1.117
     * PoiDir : null
     * Provider : 9
     * Rname : 军博西路
     * Roads : [{"ID":"60864b-f7ef-e223-dd0b-1cc781","IsCurrent":1,"Name":"军博西路","Panos":[{"DIR":0,"Order":0,"PID":"09002200121707051532190999S","Type":"street","X":1294973954,"Y":482574792},{"DIR":8,"Order":1,"PID":"09002200121707051532203479S","Type":"street","X":1294974126,"Y":482575832},{"DIR":3,"Order":2,"PID":"09002200121707051532215399S","Type":"street","X":1294974295,"Y":482577031},{"DIR":0,"Order":3,"PID":"09002200121707051532227489S","Type":"street","X":1294974363,"Y":482578310},{"DIR":359,"Order":4,"PID":"09002200121707051532239509S","Type":"street","X":1294974378,"Y":482579578},{"DIR":0,"Order":5,"PID":"09002200121707051532251859S","Type":"street","X":1294974377,"Y":482580941},{"DIR":0,"Order":6,"PID":"09002200121707051532264409S","Type":"street","X":1294974376,"Y":482582377}],"Width":550}]
     * Roll : 0.478
     * SwitchID : []
     * Time : 201707
     * TimeLine : [{"ID":"09002200121707051532215399S","IsCurrent":1,"Time":"day","TimeDir":"","TimeLine":"201707","Year":"2017"},{"ID":"0100220000130826114357213J3","IsCurrent":0,"Time":"day","TimeDir":"obsolete","TimeLine":"201308","Year":"2013"}]
     * Type : street
     * UserID :
     * Version : 0
     * Z : 43.18
     * format_v : 0
     * panoinfo : null
     * plane :
     * procdate : 20180723
     * X : 12949742
     * RX : 12949740
     * Y : 4825770
     * RY : 4825770
     */

    private String Admission;
    private String Date;
    private double DeviceHeight;
    private String FileTag;
    private double Heading;
    private String ID;
    private int LayerCount;
    private String Mode;
    private double MoveDir;
    private double NorthDir;
    private int Obsolete;
    private double Pitch;
    private Object PoiDir;
    private int Provider;
    private String Rname;
    private double Roll;
    private String Time;
    private String Type;
    private String UserID;
    private String Version;
    private double Z;
    private String format_v;
    private Object panoinfo;
    private String plane;
    private String procdate;
    private int X;
    private int RX;
    private int Y;
    private int RY;
    private List<ImgLayerBean> ImgLayer;
    private List<?> Links;
    private List<RoadsBean> Roads;
    private List<?> SwitchID;
    private List<TimeLineBean> TimeLine;

    public String getAdmission() {
        return Admission;
    }

    public void setAdmission(String Admission) {
        this.Admission = Admission;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String Date) {
        this.Date = Date;
    }

    public double getDeviceHeight() {
        return DeviceHeight;
    }

    public void setDeviceHeight(double DeviceHeight) {
        this.DeviceHeight = DeviceHeight;
    }

    public String getFileTag() {
        return FileTag;
    }

    public void setFileTag(String FileTag) {
        this.FileTag = FileTag;
    }

    public double getHeading() {
        return Heading;
    }

    public void setHeading(double Heading) {
        this.Heading = Heading;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public int getLayerCount() {
        return LayerCount;
    }

    public void setLayerCount(int LayerCount) {
        this.LayerCount = LayerCount;
    }

    public String getMode() {
        return Mode;
    }

    public void setMode(String Mode) {
        this.Mode = Mode;
    }

    public double getMoveDir() {
        return MoveDir;
    }

    public void setMoveDir(double MoveDir) {
        this.MoveDir = MoveDir;
    }

    public double getNorthDir() {
        return NorthDir;
    }

    public void setNorthDir(double NorthDir) {
        this.NorthDir = NorthDir;
    }

    public int getObsolete() {
        return Obsolete;
    }

    public void setObsolete(int Obsolete) {
        this.Obsolete = Obsolete;
    }

    public double getPitch() {
        return Pitch;
    }

    public void setPitch(double Pitch) {
        this.Pitch = Pitch;
    }

    public Object getPoiDir() {
        return PoiDir;
    }

    public void setPoiDir(Object PoiDir) {
        this.PoiDir = PoiDir;
    }

    public int getProvider() {
        return Provider;
    }

    public void setProvider(int Provider) {
        this.Provider = Provider;
    }

    public String getRname() {
        return Rname;
    }

    public void setRname(String Rname) {
        this.Rname = Rname;
    }

    public double getRoll() {
        return Roll;
    }

    public void setRoll(double Roll) {
        this.Roll = Roll;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String Time) {
        this.Time = Time;
    }

    public String getType() {
        return Type;
    }

    public void setType(String Type) {
        this.Type = Type;
    }

    public String getUserID() {
        return UserID;
    }

    public void setUserID(String UserID) {
        this.UserID = UserID;
    }

    public String getVersion() {
        return Version;
    }

    public void setVersion(String Version) {
        this.Version = Version;
    }

    public double getZ() {
        return Z;
    }

    public void setZ(double Z) {
        this.Z = Z;
    }

    public String getFormat_v() {
        return format_v;
    }

    public void setFormat_v(String format_v) {
        this.format_v = format_v;
    }

    public Object getPanoinfo() {
        return panoinfo;
    }

    public void setPanoinfo(Object panoinfo) {
        this.panoinfo = panoinfo;
    }

    public String getPlane() {
        return plane;
    }

    public void setPlane(String plane) {
        this.plane = plane;
    }

    public String getProcdate() {
        return procdate;
    }

    public void setProcdate(String procdate) {
        this.procdate = procdate;
    }

    public int getX() {
        return X;
    }

    public void setX(int X) {
        this.X = X;
    }

    public int getRX() {
        return RX;
    }

    public void setRX(int RX) {
        this.RX = RX;
    }

    public int getY() {
        return Y;
    }

    public void setY(int Y) {
        this.Y = Y;
    }

    public int getRY() {
        return RY;
    }

    public void setRY(int RY) {
        this.RY = RY;
    }

    public List<ImgLayerBean> getImgLayer() {
        return ImgLayer;
    }

    public void setImgLayer(List<ImgLayerBean> ImgLayer) {
        this.ImgLayer = ImgLayer;
    }

    public List<?> getLinks() {
        return Links;
    }

    public void setLinks(List<?> Links) {
        this.Links = Links;
    }

    public List<RoadsBean> getRoads() {
        return Roads;
    }

    public void setRoads(List<RoadsBean> Roads) {
        this.Roads = Roads;
    }

    public List<?> getSwitchID() {
        return SwitchID;
    }

    public void setSwitchID(List<?> SwitchID) {
        this.SwitchID = SwitchID;
    }

    public List<TimeLineBean> getTimeLine() {
        return TimeLine;
    }

    public void setTimeLine(List<TimeLineBean> TimeLine) {
        this.TimeLine = TimeLine;
    }

    public static class ImgLayerBean {
        /**
         * BlockX : 2
         * BlockY : 1
         * ImgFormat : jpg
         * ImgLevel : 1
         */

        private int BlockX;
        private int BlockY;
        private String ImgFormat;
        private int ImgLevel;

        public int getBlockX() {
            return BlockX;
        }

        public void setBlockX(int BlockX) {
            this.BlockX = BlockX;
        }

        public int getBlockY() {
            return BlockY;
        }

        public void setBlockY(int BlockY) {
            this.BlockY = BlockY;
        }

        public String getImgFormat() {
            return ImgFormat;
        }

        public void setImgFormat(String ImgFormat) {
            this.ImgFormat = ImgFormat;
        }

        public int getImgLevel() {
            return ImgLevel;
        }

        public void setImgLevel(int ImgLevel) {
            this.ImgLevel = ImgLevel;
        }
    }

    public static class RoadsBean {
        /**
         * ID : 60864b-f7ef-e223-dd0b-1cc781
         * IsCurrent : 1
         * Name : 军博西路
         * Panos : [{"DIR":0,"Order":0,"PID":"09002200121707051532190999S","Type":"street","X":1294973954,"Y":482574792},{"DIR":8,"Order":1,"PID":"09002200121707051532203479S","Type":"street","X":1294974126,"Y":482575832},{"DIR":3,"Order":2,"PID":"09002200121707051532215399S","Type":"street","X":1294974295,"Y":482577031},{"DIR":0,"Order":3,"PID":"09002200121707051532227489S","Type":"street","X":1294974363,"Y":482578310},{"DIR":359,"Order":4,"PID":"09002200121707051532239509S","Type":"street","X":1294974378,"Y":482579578},{"DIR":0,"Order":5,"PID":"09002200121707051532251859S","Type":"street","X":1294974377,"Y":482580941},{"DIR":0,"Order":6,"PID":"09002200121707051532264409S","Type":"street","X":1294974376,"Y":482582377}]
         * Width : 550
         */

        private String ID;
        private int IsCurrent;
        private String Name;
        private int Width;
        private List<PanosBean> Panos;

        public String getID() {
            return ID;
        }

        public void setID(String ID) {
            this.ID = ID;
        }

        public int getIsCurrent() {
            return IsCurrent;
        }

        public void setIsCurrent(int IsCurrent) {
            this.IsCurrent = IsCurrent;
        }

        public String getName() {
            return Name;
        }

        public void setName(String Name) {
            this.Name = Name;
        }

        public int getWidth() {
            return Width;
        }

        public void setWidth(int Width) {
            this.Width = Width;
        }

        public List<PanosBean> getPanos() {
            return Panos;
        }

        public void setPanos(List<PanosBean> Panos) {
            this.Panos = Panos;
        }

        public static class PanosBean {
            /**
             * DIR : 0
             * Order : 0
             * PID : 09002200121707051532190999S
             * Type : street
             * X : 1294973954
             * Y : 482574792
             */

            private int DIR;
            private int Order;
            private String PID;
            private String Type;
            private int X;
            private int Y;

            public int getDIR() {
                return DIR;
            }

            public void setDIR(int DIR) {
                this.DIR = DIR;
            }

            public int getOrder() {
                return Order;
            }

            public void setOrder(int Order) {
                this.Order = Order;
            }

            public String getPID() {
                return PID;
            }

            public void setPID(String PID) {
                this.PID = PID;
            }

            public String getType() {
                return Type;
            }

            public void setType(String Type) {
                this.Type = Type;
            }

            public int getX() {
                return X;
            }

            public void setX(int X) {
                this.X = X;
            }

            public int getY() {
                return Y;
            }

            public void setY(int Y) {
                this.Y = Y;
            }
        }
    }

    public static class TimeLineBean {
        /**
         * ID : 09002200121707051532215399S
         * IsCurrent : 1
         * Time : day
         * TimeDir :
         * TimeLine : 201707
         * Year : 2017
         */

        private String ID;
        private int IsCurrent;
        private String Time;
        private String TimeDir;
        private String TimeLine;
        private String Year;

        public String getID() {
            return ID;
        }

        public void setID(String ID) {
            this.ID = ID;
        }

        public int getIsCurrent() {
            return IsCurrent;
        }

        public void setIsCurrent(int IsCurrent) {
            this.IsCurrent = IsCurrent;
        }

        public String getTime() {
            return Time;
        }

        public void setTime(String Time) {
            this.Time = Time;
        }

        public String getTimeDir() {
            return TimeDir;
        }

        public void setTimeDir(String TimeDir) {
            this.TimeDir = TimeDir;
        }

        public String getTimeLine() {
            return TimeLine;
        }

        public void setTimeLine(String TimeLine) {
            this.TimeLine = TimeLine;
        }

        public String getYear() {
            return Year;
        }

        public void setYear(String Year) {
            this.Year = Year;
        }
    }
}
