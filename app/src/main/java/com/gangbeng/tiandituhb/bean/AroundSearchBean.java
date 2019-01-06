package com.gangbeng.tiandituhb.bean;

import java.util.List;

/**
 * Created by Administrator on 2019/1/6.
 */

public class AroundSearchBean {

    /**
     * header : {"totalItemsCount":9,"pageItemsCount":9,"pages":1,"page":1,"maxItemsPerPage":10}
     * content : {"layername":"dmdzzzys","spatialReference":"EPSG:4490","fieldsSet":{"featureid":"","featurename":"","fields":[{"name":"简称","type":"string"},{"name":"yif1","type":"string"},{"name":"erf1","type":"string"},{"name":"sanf1","type":"string"},{"name":"代码","type":"string"},{"name":"地址","type":"string"}]},"features":{"type":"FeatureCollection","features":[{"type":"Feature","id":"3715","geometry":{"type":"Point","coordinates":[116.74527728454487,39.590266013882896]},"properties":{"简称":"超达国际网球学校","yif1":"15","erf1":"1501","sanf1":"150105","代码":"1310040011001402700","地址":"河北省廊坊市廊坊经济技术开发区云鹏道街道东方大学城"}},{"type":"Feature","id":"3716","geometry":{"type":"Point","coordinates":[116.74594434886038,39.5906847949699]},"properties":{"简称":"综合武道","yif1":"15","erf1":"1502","sanf1":"150202","代码":"1310040011001401600","地址":"河北省廊坊市廊坊经济技术开发区云鹏道街道东方大学城"}},{"type":"Feature","id":"3726","geometry":{"type":"Point","coordinates":[116.75033088516966,39.592253728484266]},"properties":{"简称":"东方领航考研","yif1":"15","erf1":"1502","sanf1":"150202","代码":"1310040011001404000","地址":"河北省廊坊市廊坊经济技术开发区云鹏道街道东方大学城"}},{"type":"Feature","id":"3731","geometry":{"type":"Point","coordinates":[116.7468490005729,39.59390809968935]},"properties":{"简称":"河北东方学院","yif1":"15","erf1":"1501","sanf1":"150106","代码":"1310040011001401000","地址":"河北省廊坊市廊坊经济技术开发区云鹏道街道东方大学城"}},{"type":"Feature","id":"3827","geometry":{"type":"Point","coordinates":[116.74911502313472,39.598158995234066]},"properties":{"简称":"大学城农村信用分社","yif1":"15","erf1":"1501","sanf1":"150106","代码":"1310040011012403200","地址":"河北省廊坊市廊坊经济技术开发区云鹏道街道东方大学城"}},{"type":"Feature","id":"26289","geometry":{"type":"Point","coordinates":[116.75115773593312,39.599556503816494]},"properties":{"简称":"廊坊华航航空学校","yif1":"15","erf1":"1501","sanf1":"150105","代码":"1310040011012402500","地址":"河北省廊坊市廊坊经济技术开发区云鹏道街道东方大学城"}}]}}
     */

    private HeaderBean header;
    private ContentBean content;

    public HeaderBean getHeader() {
        return header;
    }

    public void setHeader(HeaderBean header) {
        this.header = header;
    }

    public ContentBean getContent() {
        return content;
    }

    public void setContent(ContentBean content) {
        this.content = content;
    }

    public static class HeaderBean {
        /**
         * totalItemsCount : 9
         * pageItemsCount : 9
         * pages : 1
         * page : 1
         * maxItemsPerPage : 10
         */

        private int totalItemsCount;
        private int pageItemsCount;
        private int pages;
        private int page;
        private int maxItemsPerPage;

        public int getTotalItemsCount() {
            return totalItemsCount;
        }

        public void setTotalItemsCount(int totalItemsCount) {
            this.totalItemsCount = totalItemsCount;
        }

        public int getPageItemsCount() {
            return pageItemsCount;
        }

        public void setPageItemsCount(int pageItemsCount) {
            this.pageItemsCount = pageItemsCount;
        }

        public int getPages() {
            return pages;
        }

        public void setPages(int pages) {
            this.pages = pages;
        }

        public int getPage() {
            return page;
        }

        public void setPage(int page) {
            this.page = page;
        }

        public int getMaxItemsPerPage() {
            return maxItemsPerPage;
        }

        public void setMaxItemsPerPage(int maxItemsPerPage) {
            this.maxItemsPerPage = maxItemsPerPage;
        }
    }

    public static class ContentBean {
        /**
         * layername : dmdzzzys
         * spatialReference : EPSG:4490
         * fieldsSet : {"featureid":"","featurename":"","fields":[{"name":"简称","type":"string"},{"name":"yif1","type":"string"},{"name":"erf1","type":"string"},{"name":"sanf1","type":"string"},{"name":"代码","type":"string"},{"name":"地址","type":"string"}]}
         * features : {"type":"FeatureCollection","features":[{"type":"Feature","id":"3715","geometry":{"type":"Point","coordinates":[116.74527728454487,39.590266013882896]},"properties":{"简称":"超达国际网球学校","yif1":"15","erf1":"1501","sanf1":"150105","代码":"1310040011001402700","地址":"河北省廊坊市廊坊经济技术开发区云鹏道街道东方大学城"}},{"type":"Feature","id":"3716","geometry":{"type":"Point","coordinates":[116.74594434886038,39.5906847949699]},"properties":{"简称":"综合武道","yif1":"15","erf1":"1502","sanf1":"150202","代码":"1310040011001401600","地址":"河北省廊坊市廊坊经济技术开发区云鹏道街道东方大学城"}},{"type":"Feature","id":"3726","geometry":{"type":"Point","coordinates":[116.75033088516966,39.592253728484266]},"properties":{"简称":"东方领航考研","yif1":"15","erf1":"1502","sanf1":"150202","代码":"1310040011001404000","地址":"河北省廊坊市廊坊经济技术开发区云鹏道街道东方大学城"}},{"type":"Feature","id":"3731","geometry":{"type":"Point","coordinates":[116.7468490005729,39.59390809968935]},"properties":{"简称":"河北东方学院","yif1":"15","erf1":"1501","sanf1":"150106","代码":"1310040011001401000","地址":"河北省廊坊市廊坊经济技术开发区云鹏道街道东方大学城"}},{"type":"Feature","id":"3827","geometry":{"type":"Point","coordinates":[116.74911502313472,39.598158995234066]},"properties":{"简称":"大学城农村信用分社","yif1":"15","erf1":"1501","sanf1":"150106","代码":"1310040011012403200","地址":"河北省廊坊市廊坊经济技术开发区云鹏道街道东方大学城"}},{"type":"Feature","id":"26289","geometry":{"type":"Point","coordinates":[116.75115773593312,39.599556503816494]},"properties":{"简称":"廊坊华航航空学校","yif1":"15","erf1":"1501","sanf1":"150105","代码":"1310040011012402500","地址":"河北省廊坊市廊坊经济技术开发区云鹏道街道东方大学城"}}]}
         */

        private String layername;
        private String spatialReference;
        private FieldsSetBean fieldsSet;
        private FeaturesBeanX features;

        public String getLayername() {
            return layername;
        }

        public void setLayername(String layername) {
            this.layername = layername;
        }

        public String getSpatialReference() {
            return spatialReference;
        }

        public void setSpatialReference(String spatialReference) {
            this.spatialReference = spatialReference;
        }

        public FieldsSetBean getFieldsSet() {
            return fieldsSet;
        }

        public void setFieldsSet(FieldsSetBean fieldsSet) {
            this.fieldsSet = fieldsSet;
        }

        public FeaturesBeanX getFeatures() {
            return features;
        }

        public void setFeatures(FeaturesBeanX features) {
            this.features = features;
        }

        public static class FieldsSetBean {
            /**
             * featureid :
             * featurename :
             * fields : [{"name":"简称","type":"string"},{"name":"yif1","type":"string"},{"name":"erf1","type":"string"},{"name":"sanf1","type":"string"},{"name":"代码","type":"string"},{"name":"地址","type":"string"}]
             */

            private String featureid;
            private String featurename;
            private List<FieldsBean> fields;

            public String getFeatureid() {
                return featureid;
            }

            public void setFeatureid(String featureid) {
                this.featureid = featureid;
            }

            public String getFeaturename() {
                return featurename;
            }

            public void setFeaturename(String featurename) {
                this.featurename = featurename;
            }

            public List<FieldsBean> getFields() {
                return fields;
            }

            public void setFields(List<FieldsBean> fields) {
                this.fields = fields;
            }

            public static class FieldsBean {
                /**
                 * name : 简称
                 * type : string
                 */

                private String name;
                private String type;

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }

                public String getType() {
                    return type;
                }

                public void setType(String type) {
                    this.type = type;
                }
            }
        }

        public static class FeaturesBeanX {
            /**
             * type : FeatureCollection
             * features : [{"type":"Feature","id":"3715","geometry":{"type":"Point","coordinates":[116.74527728454487,39.590266013882896]},"properties":{"简称":"超达国际网球学校","yif1":"15","erf1":"1501","sanf1":"150105","代码":"1310040011001402700","地址":"河北省廊坊市廊坊经济技术开发区云鹏道街道东方大学城"}},{"type":"Feature","id":"3716","geometry":{"type":"Point","coordinates":[116.74594434886038,39.5906847949699]},"properties":{"简称":"综合武道","yif1":"15","erf1":"1502","sanf1":"150202","代码":"1310040011001401600","地址":"河北省廊坊市廊坊经济技术开发区云鹏道街道东方大学城"}},{"type":"Feature","id":"3726","geometry":{"type":"Point","coordinates":[116.75033088516966,39.592253728484266]},"properties":{"简称":"东方领航考研","yif1":"15","erf1":"1502","sanf1":"150202","代码":"1310040011001404000","地址":"河北省廊坊市廊坊经济技术开发区云鹏道街道东方大学城"}},{"type":"Feature","id":"3731","geometry":{"type":"Point","coordinates":[116.7468490005729,39.59390809968935]},"properties":{"简称":"河北东方学院","yif1":"15","erf1":"1501","sanf1":"150106","代码":"1310040011001401000","地址":"河北省廊坊市廊坊经济技术开发区云鹏道街道东方大学城"}},{"type":"Feature","id":"3827","geometry":{"type":"Point","coordinates":[116.74911502313472,39.598158995234066]},"properties":{"简称":"大学城农村信用分社","yif1":"15","erf1":"1501","sanf1":"150106","代码":"1310040011012403200","地址":"河北省廊坊市廊坊经济技术开发区云鹏道街道东方大学城"}},{"type":"Feature","id":"26289","geometry":{"type":"Point","coordinates":[116.75115773593312,39.599556503816494]},"properties":{"简称":"廊坊华航航空学校","yif1":"15","erf1":"1501","sanf1":"150105","代码":"1310040011012402500","地址":"河北省廊坊市廊坊经济技术开发区云鹏道街道东方大学城"}}]
             */

            private String type;
            private List<FeaturesBean> features;

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public List<FeaturesBean> getFeatures() {
                return features;
            }

            public void setFeatures(List<FeaturesBean> features) {
                this.features = features;
            }

            public static class FeaturesBean {
                /**
                 * type : Feature
                 * id : 3715
                 * geometry : {"type":"Point","coordinates":[116.74527728454487,39.590266013882896]}
                 * properties : {"简称":"超达国际网球学校","yif1":"15","erf1":"1501","sanf1":"150105","代码":"1310040011001402700","地址":"河北省廊坊市廊坊经济技术开发区云鹏道街道东方大学城"}
                 */

                private String type;
                private String id;
                private GeometryBean geometry;
                private PropertiesBean properties;

                public String getType() {
                    return type;
                }

                public void setType(String type) {
                    this.type = type;
                }

                public String getId() {
                    return id;
                }

                public void setId(String id) {
                    this.id = id;
                }

                public GeometryBean getGeometry() {
                    return geometry;
                }

                public void setGeometry(GeometryBean geometry) {
                    this.geometry = geometry;
                }

                public PropertiesBean getProperties() {
                    return properties;
                }

                public void setProperties(PropertiesBean properties) {
                    this.properties = properties;
                }

                public static class GeometryBean {
                    /**
                     * type : Point
                     * coordinates : [116.74527728454487,39.590266013882896]
                     */

                    private String type;
                    private List<Double> coordinates;

                    public String getType() {
                        return type;
                    }

                    public void setType(String type) {
                        this.type = type;
                    }

                    public List<Double> getCoordinates() {
                        return coordinates;
                    }

                    public void setCoordinates(List<Double> coordinates) {
                        this.coordinates = coordinates;
                    }
                }

                public static class PropertiesBean {
                    /**
                     * 简称 : 超达国际网球学校
                     * yif1 : 15
                     * erf1 : 1501
                     * sanf1 : 150105
                     * 代码 : 1310040011001402700
                     * 地址 : 河北省廊坊市廊坊经济技术开发区云鹏道街道东方大学城
                     */

                    private String 简称;
                    private String yif1;
                    private String erf1;
                    private String sanf1;
                    private String 代码;
                    private String 地址;

                    public String get简称() {
                        return 简称;
                    }

                    public void set简称(String 简称) {
                        this.简称 = 简称;
                    }

                    public String getYif1() {
                        return yif1;
                    }

                    public void setYif1(String yif1) {
                        this.yif1 = yif1;
                    }

                    public String getErf1() {
                        return erf1;
                    }

                    public void setErf1(String erf1) {
                        this.erf1 = erf1;
                    }

                    public String getSanf1() {
                        return sanf1;
                    }

                    public void setSanf1(String sanf1) {
                        this.sanf1 = sanf1;
                    }

                    public String get代码() {
                        return 代码;
                    }

                    public void set代码(String 代码) {
                        this.代码 = 代码;
                    }

                    public String get地址() {
                        return 地址;
                    }

                    public void set地址(String 地址) {
                        this.地址 = 地址;
                    }
                }
            }
        }
    }
}
