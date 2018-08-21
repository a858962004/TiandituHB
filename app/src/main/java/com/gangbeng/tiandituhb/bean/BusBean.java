package com.gangbeng.tiandituhb.bean;

import java.util.List;

/**
 * @author zhanghao
 * @date 2018-08-20
 */

public class BusBean {

    private boolean hasSubway;
    private int resultCode;
    private List<ResultsBean> results;

    public boolean isHasSubway() {
        return hasSubway;
    }

    public void setHasSubway(boolean hasSubway) {
        this.hasSubway = hasSubway;
    }

    public int getResultCode() {
        return resultCode;
    }

    public void setResultCode(int resultCode) {
        this.resultCode = resultCode;
    }

    public List<ResultsBean> getResults() {
        return results;
    }

    public void setResults(List<ResultsBean> results) {
        this.results = results;
    }

    public static class ResultsBean {

        private int lineType;
        private List<LinesBean> lines;

        public int getLineType() {
            return lineType;
        }

        public void setLineType(int lineType) {
            this.lineType = lineType;
        }

        public List<LinesBean> getLines() {
            return lines;
        }

        public void setLines(List<LinesBean> lines) {
            this.lines = lines;
        }

        public static class LinesBean {

            private String lineName;
            private List<SegmentsBean> segments;

            public String getLineName() {
                return lineName;
            }

            public void setLineName(String lineName) {
                this.lineName = lineName;
            }

            public List<SegmentsBean> getSegments() {
                return segments;
            }

            public void setSegments(List<SegmentsBean> segments) {
                this.segments = segments;
            }

            public static class SegmentsBean {
                /**
                 * segmentLine : [{"segmentStationCount":"","segmentTime":3,"segmentDistance":131,"direction":"","linePoint":"116.32142909541093,39.89988988242982;116.322947,39.899697;","lineName":"","byuuid":""}]
                 * stationEnd : {"lonlat":"116.322947,39.899697","name":"北蜂窝路","uuid":"132911"}
                 * segmentType : 1
                 * stationStart : {"lonlat":"116.32142909541093,39.89988988242982","name":"","uuid":""}
                 */

                private StationEndBean stationEnd;
                private int segmentType;
                private StationStartBean stationStart;
                private List<SegmentLineBean> segmentLine;

                public StationEndBean getStationEnd() {
                    return stationEnd;
                }

                public void setStationEnd(StationEndBean stationEnd) {
                    this.stationEnd = stationEnd;
                }

                public int getSegmentType() {
                    return segmentType;
                }

                public void setSegmentType(int segmentType) {
                    this.segmentType = segmentType;
                }

                public StationStartBean getStationStart() {
                    return stationStart;
                }

                public void setStationStart(StationStartBean stationStart) {
                    this.stationStart = stationStart;
                }

                public List<SegmentLineBean> getSegmentLine() {
                    return segmentLine;
                }

                public void setSegmentLine(List<SegmentLineBean> segmentLine) {
                    this.segmentLine = segmentLine;
                }

                public static class StationEndBean {
                    /**
                     * lonlat : 116.322947,39.899697
                     * name : 北蜂窝路
                     * uuid : 132911
                     */

                    private String lonlat;
                    private String name;
                    private String uuid;

                    public String getLonlat() {
                        return lonlat;
                    }

                    public void setLonlat(String lonlat) {
                        this.lonlat = lonlat;
                    }

                    public String getName() {
                        return name;
                    }

                    public void setName(String name) {
                        this.name = name;
                    }

                    public String getUuid() {
                        return uuid;
                    }

                    public void setUuid(String uuid) {
                        this.uuid = uuid;
                    }
                }

                public static class StationStartBean {
                    /**
                     * lonlat : 116.32142909541093,39.89988988242982
                     * name :
                     * uuid :
                     */

                    private String lonlat;
                    private String name;
                    private String uuid;

                    public String getLonlat() {
                        return lonlat;
                    }

                    public void setLonlat(String lonlat) {
                        this.lonlat = lonlat;
                    }

                    public String getName() {
                        return name;
                    }

                    public void setName(String name) {
                        this.name = name;
                    }

                    public String getUuid() {
                        return uuid;
                    }

                    public void setUuid(String uuid) {
                        this.uuid = uuid;
                    }
                }

                public static class SegmentLineBean {
                    /**
                     * segmentStationCount :
                     * segmentTime : 3
                     * segmentDistance : 131
                     * direction :
                     * linePoint : 116.32142909541093,39.89988988242982;116.322947,39.899697;
                     * lineName :
                     * byuuid :
                     */

                    private String segmentStationCount;
                    private String segmentTime;
                    private String segmentDistance;
                    private String direction;
                    private String linePoint;
                    private String lineName;
                    private String byuuid;

                    public String getSegmentStationCount() {
                        return segmentStationCount;
                    }

                    public void setSegmentStationCount(String segmentStationCount) {
                        this.segmentStationCount = segmentStationCount;
                    }

                    public String getSegmentTime() {
                        return segmentTime;
                    }

                    public void setSegmentTime(String segmentTime) {
                        this.segmentTime = segmentTime;
                    }

                    public String getSegmentDistance() {
                        return segmentDistance;
                    }

                    public void setSegmentDistance(String segmentDistance) {
                        this.segmentDistance = segmentDistance;
                    }

                    public String getDirection() {
                        return direction;
                    }

                    public void setDirection(String direction) {
                        this.direction = direction;
                    }

                    public String getLinePoint() {
                        return linePoint;
                    }

                    public void setLinePoint(String linePoint) {
                        this.linePoint = linePoint;
                    }

                    public String getLineName() {
                        return lineName;
                    }

                    public void setLineName(String lineName) {
                        this.lineName = lineName;
                    }

                    public String getByuuid() {
                        return byuuid;
                    }

                    public void setByuuid(String byuuid) {
                        this.byuuid = byuuid;
                    }
                }
            }
        }
    }
}
