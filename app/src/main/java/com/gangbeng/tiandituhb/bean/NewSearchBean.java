package com.gangbeng.tiandituhb.bean;

import java.io.Serializable;
import java.util.List;

/**
 * @author zhanghao
 * @date 2018-09-21
 */

public class NewSearchBean implements Serializable {

    /**
     * header : {"totalItemsCount":3459,"pageItemsCount":10,"pages":346,"page":1,"maxItemsPerPage":10}
     * content : {"layername":"dmdz","spatialReference":"EPSG:4326","fieldsSet":{"featureid":"","featurename":"","fields":[{"name":"行业","type":"string"},{"name":"名称","type":"string"},{"name":"地址","type":"string"},{"name":"描述","type":"string"},{"name":"联系人","type":"string"},{"name":"联系电话","type":"string"},{"name":"yifl","type":"string"},{"name":"erfl","type":"string"}]},"features":{"type":"FeatureCollection","features":[{"type":"Feature","id":"2078","geometry":{"type":"Point","coordinates":[116.71330026160271,39.581949817032296]},"properties":{"行业":"","名称":"廊坊市农业高新区管委会","地址":"","描述":"","联系人":"","联系电话":"","yifl":"01","erfl":"0106"}},{"type":"Feature","id":"2201","geometry":{"type":"Point","coordinates":[116.73091468833306,39.58314999206884]},"properties":{"行业":"","名称":"安徽全柴动力股份有限公司廊坊服务站","地址":"","描述":"","联系人":"","联系电话":"","yifl":"01","erfl":"0106"}},{"type":"Feature","id":"2202","geometry":{"type":"Point","coordinates":[116.7492748991323,39.58503496786149]},"properties":{"行业":"","名称":"中国石油天然气管道局设备管理中心","地址":"","描述":"","联系人":"","联系电话":"","yifl":"01","erfl":"0106"}},{"type":"Feature","id":"2203","geometry":{"type":"Point","coordinates":[116.75313087148942,39.58436076567391]},"properties":{"行业":"","名称":"中国石油管道应急救援响应中心","地址":"","描述":"","联系人":"","联系电话":"","yifl":"01","erfl":"0106"}},{"type":"Feature","id":"2204","geometry":{"type":"Point","coordinates":[116.77536796542225,39.58618995695558]},"properties":{"行业":"","名称":"廊坊长途通信传输局微波监控中心","地址":"","描述":"","联系人":"","联系电话":"","yifl":"01","erfl":"0106"}},{"type":"Feature","id":"2205","geometry":{"type":"Point","coordinates":[116.77033764935055,39.588043033439085]},"properties":{"行业":"","名称":"中国海关报关大厅","地址":"","描述":"","联系人":"","联系电话":"","yifl":"01","erfl":"0104"}},{"type":"Feature","id":"2206","geometry":{"type":"Point","coordinates":[116.75652415891942,39.57543018476308]},"properties":{"行业":"","名称":"廊坊开发区建设工程质量监督站","地址":"","描述":"","联系人":"","联系电话":"","yifl":"01","erfl":"0106"}},{"type":"Feature","id":"2207","geometry":{"type":"Point","coordinates":[116.76362618851508,39.53044410529642]},"properties":{"行业":"","名称":"廊坊市救助管理站","地址":"","描述":"","联系人":"","联系电话":"","yifl":"01","erfl":"0106"}},{"type":"Feature","id":"2289","geometry":{"type":"Point","coordinates":[116.70793751885442,39.5370512451269]},"properties":{"行业":"","名称":"廊坊市国土资源大厦","地址":"","描述":"","联系人":"","联系电话":"","yifl":"01","erfl":"0108"}},{"type":"Feature","id":"2290","geometry":{"type":"Point","coordinates":[116.71045644904358,39.5132120467195]},"properties":{"行业":"","名称":"市政大楼","地址":"","描述":"","联系人":"","联系电话":"","yifl":"01","erfl":"0108"}}]}}
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

    public static class HeaderBean implements Serializable {
        /**
         * totalItemsCount : 3459
         * pageItemsCount : 10
         * pages : 346
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

    public static class ContentBean implements Serializable {
        /**
         * layername : dmdz
         * spatialReference : EPSG:4326
         * fieldsSet : {"featureid":"","featurename":"","fields":[{"name":"行业","type":"string"},{"name":"名称","type":"string"},{"name":"地址","type":"string"},{"name":"描述","type":"string"},{"name":"联系人","type":"string"},{"name":"联系电话","type":"string"},{"name":"yifl","type":"string"},{"name":"erfl","type":"string"}]}
         * features : {"type":"FeatureCollection","features":[{"type":"Feature","id":"2078","geometry":{"type":"Point","coordinates":[116.71330026160271,39.581949817032296]},"properties":{"行业":"","名称":"廊坊市农业高新区管委会","地址":"","描述":"","联系人":"","联系电话":"","yifl":"01","erfl":"0106"}},{"type":"Feature","id":"2201","geometry":{"type":"Point","coordinates":[116.73091468833306,39.58314999206884]},"properties":{"行业":"","名称":"安徽全柴动力股份有限公司廊坊服务站","地址":"","描述":"","联系人":"","联系电话":"","yifl":"01","erfl":"0106"}},{"type":"Feature","id":"2202","geometry":{"type":"Point","coordinates":[116.7492748991323,39.58503496786149]},"properties":{"行业":"","名称":"中国石油天然气管道局设备管理中心","地址":"","描述":"","联系人":"","联系电话":"","yifl":"01","erfl":"0106"}},{"type":"Feature","id":"2203","geometry":{"type":"Point","coordinates":[116.75313087148942,39.58436076567391]},"properties":{"行业":"","名称":"中国石油管道应急救援响应中心","地址":"","描述":"","联系人":"","联系电话":"","yifl":"01","erfl":"0106"}},{"type":"Feature","id":"2204","geometry":{"type":"Point","coordinates":[116.77536796542225,39.58618995695558]},"properties":{"行业":"","名称":"廊坊长途通信传输局微波监控中心","地址":"","描述":"","联系人":"","联系电话":"","yifl":"01","erfl":"0106"}},{"type":"Feature","id":"2205","geometry":{"type":"Point","coordinates":[116.77033764935055,39.588043033439085]},"properties":{"行业":"","名称":"中国海关报关大厅","地址":"","描述":"","联系人":"","联系电话":"","yifl":"01","erfl":"0104"}},{"type":"Feature","id":"2206","geometry":{"type":"Point","coordinates":[116.75652415891942,39.57543018476308]},"properties":{"行业":"","名称":"廊坊开发区建设工程质量监督站","地址":"","描述":"","联系人":"","联系电话":"","yifl":"01","erfl":"0106"}},{"type":"Feature","id":"2207","geometry":{"type":"Point","coordinates":[116.76362618851508,39.53044410529642]},"properties":{"行业":"","名称":"廊坊市救助管理站","地址":"","描述":"","联系人":"","联系电话":"","yifl":"01","erfl":"0106"}},{"type":"Feature","id":"2289","geometry":{"type":"Point","coordinates":[116.70793751885442,39.5370512451269]},"properties":{"行业":"","名称":"廊坊市国土资源大厦","地址":"","描述":"","联系人":"","联系电话":"","yifl":"01","erfl":"0108"}},{"type":"Feature","id":"2290","geometry":{"type":"Point","coordinates":[116.71045644904358,39.5132120467195]},"properties":{"行业":"","名称":"市政大楼","地址":"","描述":"","联系人":"","联系电话":"","yifl":"01","erfl":"0108"}}]}
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

        public static class FieldsSetBean implements Serializable {
            /**
             * featureid :
             * featurename :
             * fields : [{"name":"行业","type":"string"},{"name":"名称","type":"string"},{"name":"地址","type":"string"},{"name":"描述","type":"string"},{"name":"联系人","type":"string"},{"name":"联系电话","type":"string"},{"name":"yifl","type":"string"},{"name":"erfl","type":"string"}]
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

            public static class FieldsBean implements Serializable {
                /**
                 * name : 行业
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

        public static class FeaturesBeanX implements Serializable {
            /**
             * type : FeatureCollection
             * features : [{"type":"Feature","id":"2078","geometry":{"type":"Point","coordinates":[116.71330026160271,39.581949817032296]},"properties":{"行业":"","名称":"廊坊市农业高新区管委会","地址":"","描述":"","联系人":"","联系电话":"","yifl":"01","erfl":"0106"}},{"type":"Feature","id":"2201","geometry":{"type":"Point","coordinates":[116.73091468833306,39.58314999206884]},"properties":{"行业":"","名称":"安徽全柴动力股份有限公司廊坊服务站","地址":"","描述":"","联系人":"","联系电话":"","yifl":"01","erfl":"0106"}},{"type":"Feature","id":"2202","geometry":{"type":"Point","coordinates":[116.7492748991323,39.58503496786149]},"properties":{"行业":"","名称":"中国石油天然气管道局设备管理中心","地址":"","描述":"","联系人":"","联系电话":"","yifl":"01","erfl":"0106"}},{"type":"Feature","id":"2203","geometry":{"type":"Point","coordinates":[116.75313087148942,39.58436076567391]},"properties":{"行业":"","名称":"中国石油管道应急救援响应中心","地址":"","描述":"","联系人":"","联系电话":"","yifl":"01","erfl":"0106"}},{"type":"Feature","id":"2204","geometry":{"type":"Point","coordinates":[116.77536796542225,39.58618995695558]},"properties":{"行业":"","名称":"廊坊长途通信传输局微波监控中心","地址":"","描述":"","联系人":"","联系电话":"","yifl":"01","erfl":"0106"}},{"type":"Feature","id":"2205","geometry":{"type":"Point","coordinates":[116.77033764935055,39.588043033439085]},"properties":{"行业":"","名称":"中国海关报关大厅","地址":"","描述":"","联系人":"","联系电话":"","yifl":"01","erfl":"0104"}},{"type":"Feature","id":"2206","geometry":{"type":"Point","coordinates":[116.75652415891942,39.57543018476308]},"properties":{"行业":"","名称":"廊坊开发区建设工程质量监督站","地址":"","描述":"","联系人":"","联系电话":"","yifl":"01","erfl":"0106"}},{"type":"Feature","id":"2207","geometry":{"type":"Point","coordinates":[116.76362618851508,39.53044410529642]},"properties":{"行业":"","名称":"廊坊市救助管理站","地址":"","描述":"","联系人":"","联系电话":"","yifl":"01","erfl":"0106"}},{"type":"Feature","id":"2289","geometry":{"type":"Point","coordinates":[116.70793751885442,39.5370512451269]},"properties":{"行业":"","名称":"廊坊市国土资源大厦","地址":"","描述":"","联系人":"","联系电话":"","yifl":"01","erfl":"0108"}},{"type":"Feature","id":"2290","geometry":{"type":"Point","coordinates":[116.71045644904358,39.5132120467195]},"properties":{"行业":"","名称":"市政大楼","地址":"","描述":"","联系人":"","联系电话":"","yifl":"01","erfl":"0108"}}]
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

            public static class FeaturesBean implements Serializable {
                /**
                 * type : Feature
                 * id : 2078
                 * geometry : {"type":"Point","coordinates":[116.71330026160271,39.581949817032296]}
                 * properties : {"行业":"","名称":"廊坊市农业高新区管委会","地址":"","描述":"","联系人":"","联系电话":"","yifl":"01","erfl":"0106"}
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

                public static class GeometryBean implements Serializable {
                    /**
                     * type : Point
                     * coordinates : [116.71330026160271,39.581949817032296]
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

                public static class PropertiesBean implements Serializable {
                    /**
                     * 行业 :
                     * 名称 : 廊坊市农业高新区管委会
                     * 地址 :
                     * 描述 :
                     * 联系人 :
                     * 联系电话 :
                     * yifl : 01
                     * erfl : 0106
                     */

                    private String 行业;
                    private String 名称;
                    private String 地址;
                    private String 描述;
                    private String 联系人;
                    private String 联系电话;
                    private String yifl;
                    private String erfl;

                    public String get行业() {
                        return 行业;
                    }

                    public void set行业(String 行业) {
                        this.行业 = 行业;
                    }

                    public String get名称() {
                        return 名称;
                    }

                    public void set名称(String 名称) {
                        this.名称 = 名称;
                    }

                    public String get地址() {
                        return 地址;
                    }

                    public void set地址(String 地址) {
                        this.地址 = 地址;
                    }

                    public String get描述() {
                        return 描述;
                    }

                    public void set描述(String 描述) {
                        this.描述 = 描述;
                    }

                    public String get联系人() {
                        return 联系人;
                    }

                    public void set联系人(String 联系人) {
                        this.联系人 = 联系人;
                    }

                    public String get联系电话() {
                        return 联系电话;
                    }

                    public void set联系电话(String 联系电话) {
                        this.联系电话 = 联系电话;
                    }

                    public String getYifl() {
                        return yifl;
                    }

                    public void setYifl(String yifl) {
                        this.yifl = yifl;
                    }

                    public String getErfl() {
                        return erfl;
                    }

                    public void setErfl(String erfl) {
                        this.erfl = erfl;
                    }
                }
            }
        }
    }
}
