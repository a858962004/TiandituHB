package com.gangbeng.tiandituhb.bean;

/**
 * @author zhanghao
 * @date 2018-11-15
 */

public class UpdateBean {

    /**
     * ret : 200
     * data : {"err_code":0,"err_msg":"","data":{"id":1,"uuid":"","add_time":"2018-11-15 14:33:05","update_time":null,"ext_data":null,"versionCode":"1","versionName":"1.0","updateUrl":"https://qdcu01.baidupcs.com/file/93cf40e45e19b0ce5e192f5a3bcd0534?bkt=p3-00001fc0844d579c60ee2ac58c0eb141a1e5&fid=3391303808-250528-440604549433407&time=1542263093&sign=FDTAXGERLQBHSKW-DCb740ccc5511e5e8fedcff06b081203-2WeEEpIoGfQCPQfW%2FJpygdnYoOc%3D&to=65&size=44204740&sta_dx=44204740&sta_cs=1&sta_ft=apk&sta_ct=0&sta_mt=0&fm2=MH%2CQingdao%2CAnywhere%2C%2Cbeijing%2Ccnc&ctime=1542263064&mtime=1542263064&resv0=cdnback&resv1=0&vuk=3391303808&iv=0&htype=&newver=1&newfm=1&secfm=1&flow_ver=3&pkey=00001fc0844d579c60ee2ac58c0eb141a1e5&sl=76480590&expires=8h&rt=sh&r=686905462&mlogid=7389247677826908951&vbdid=832439869&fin=%E5%A4%A9%E5%9C%B0%E5%9B%BE%E5%BB%8A%E5%9D%8A.apk&fn=%E5%A4%A9%E5%9C%B0%E5%9B%BE%E5%BB%8A%E5%9D%8A.apk&rtype=1&dp-logid=7389247677826908951&dp-callid=0.1.1&hps=1&tsl=80&csl=80&csign=I1d5v3zLcGlnFKdLsQKvl61fxXU%3D&so=0&ut=6&uter=4&serv=0&uc=2842470600&ti=c77a2290e27174be21fa12ec1eb09b697b125593612a5426305a5e1275657320&by=themis","updateContent":"<p>更新测试<br/><\/p><br/>"}}
     * msg : 当前请求接口：App.Table.Get
     */

    private int ret;
    private DataBeanX data;
    private String msg;

    public int getRet() {
        return ret;
    }

    public void setRet(int ret) {
        this.ret = ret;
    }

    public DataBeanX getData() {
        return data;
    }

    public void setData(DataBeanX data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public static class DataBeanX {
        /**
         * err_code : 0
         * err_msg :
         * data : {"id":1,"uuid":"","add_time":"2018-11-15 14:33:05","update_time":null,"ext_data":null,"versionCode":"1","versionName":"1.0","updateUrl":"https://qdcu01.baidupcs.com/file/93cf40e45e19b0ce5e192f5a3bcd0534?bkt=p3-00001fc0844d579c60ee2ac58c0eb141a1e5&fid=3391303808-250528-440604549433407&time=1542263093&sign=FDTAXGERLQBHSKW-DCb740ccc5511e5e8fedcff06b081203-2WeEEpIoGfQCPQfW%2FJpygdnYoOc%3D&to=65&size=44204740&sta_dx=44204740&sta_cs=1&sta_ft=apk&sta_ct=0&sta_mt=0&fm2=MH%2CQingdao%2CAnywhere%2C%2Cbeijing%2Ccnc&ctime=1542263064&mtime=1542263064&resv0=cdnback&resv1=0&vuk=3391303808&iv=0&htype=&newver=1&newfm=1&secfm=1&flow_ver=3&pkey=00001fc0844d579c60ee2ac58c0eb141a1e5&sl=76480590&expires=8h&rt=sh&r=686905462&mlogid=7389247677826908951&vbdid=832439869&fin=%E5%A4%A9%E5%9C%B0%E5%9B%BE%E5%BB%8A%E5%9D%8A.apk&fn=%E5%A4%A9%E5%9C%B0%E5%9B%BE%E5%BB%8A%E5%9D%8A.apk&rtype=1&dp-logid=7389247677826908951&dp-callid=0.1.1&hps=1&tsl=80&csl=80&csign=I1d5v3zLcGlnFKdLsQKvl61fxXU%3D&so=0&ut=6&uter=4&serv=0&uc=2842470600&ti=c77a2290e27174be21fa12ec1eb09b697b125593612a5426305a5e1275657320&by=themis","updateContent":"<p>更新测试<br/><\/p><br/>"}
         */

        private int err_code;
        private String err_msg;
        private DataBean data;

        public int getErr_code() {
            return err_code;
        }

        public void setErr_code(int err_code) {
            this.err_code = err_code;
        }

        public String getErr_msg() {
            return err_msg;
        }

        public void setErr_msg(String err_msg) {
            this.err_msg = err_msg;
        }

        public DataBean getData() {
            return data;
        }

        public void setData(DataBean data) {
            this.data = data;
        }

        public static class DataBean {
            /**
             * id : 1
             * uuid :
             * add_time : 2018-11-15 14:33:05
             * update_time : null
             * ext_data : null
             * versionCode : 1
             * versionName : 1.0
             * updateUrl : https://qdcu01.baidupcs.com/file/93cf40e45e19b0ce5e192f5a3bcd0534?bkt=p3-00001fc0844d579c60ee2ac58c0eb141a1e5&fid=3391303808-250528-440604549433407&time=1542263093&sign=FDTAXGERLQBHSKW-DCb740ccc5511e5e8fedcff06b081203-2WeEEpIoGfQCPQfW%2FJpygdnYoOc%3D&to=65&size=44204740&sta_dx=44204740&sta_cs=1&sta_ft=apk&sta_ct=0&sta_mt=0&fm2=MH%2CQingdao%2CAnywhere%2C%2Cbeijing%2Ccnc&ctime=1542263064&mtime=1542263064&resv0=cdnback&resv1=0&vuk=3391303808&iv=0&htype=&newver=1&newfm=1&secfm=1&flow_ver=3&pkey=00001fc0844d579c60ee2ac58c0eb141a1e5&sl=76480590&expires=8h&rt=sh&r=686905462&mlogid=7389247677826908951&vbdid=832439869&fin=%E5%A4%A9%E5%9C%B0%E5%9B%BE%E5%BB%8A%E5%9D%8A.apk&fn=%E5%A4%A9%E5%9C%B0%E5%9B%BE%E5%BB%8A%E5%9D%8A.apk&rtype=1&dp-logid=7389247677826908951&dp-callid=0.1.1&hps=1&tsl=80&csl=80&csign=I1d5v3zLcGlnFKdLsQKvl61fxXU%3D&so=0&ut=6&uter=4&serv=0&uc=2842470600&ti=c77a2290e27174be21fa12ec1eb09b697b125593612a5426305a5e1275657320&by=themis
             * updateContent : <p>更新测试<br/></p><br/>
             */

            private int id;
            private String uuid;
            private String add_time;
            private Object update_time;
            private Object ext_data;
            private String versionCode;
            private String versionName;
            private String updateUrl;
            private String updateContent;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getUuid() {
                return uuid;
            }

            public void setUuid(String uuid) {
                this.uuid = uuid;
            }

            public String getAdd_time() {
                return add_time;
            }

            public void setAdd_time(String add_time) {
                this.add_time = add_time;
            }

            public Object getUpdate_time() {
                return update_time;
            }

            public void setUpdate_time(Object update_time) {
                this.update_time = update_time;
            }

            public Object getExt_data() {
                return ext_data;
            }

            public void setExt_data(Object ext_data) {
                this.ext_data = ext_data;
            }

            public String getVersionCode() {
                return versionCode;
            }

            public void setVersionCode(String versionCode) {
                this.versionCode = versionCode;
            }

            public String getVersionName() {
                return versionName;
            }

            public void setVersionName(String versionName) {
                this.versionName = versionName;
            }

            public String getUpdateUrl() {
                return updateUrl;
            }

            public void setUpdateUrl(String updateUrl) {
                this.updateUrl = updateUrl;
            }

            public String getUpdateContent() {
                return updateContent;
            }

            public void setUpdateContent(String updateContent) {
                this.updateContent = updateContent;
            }
        }
    }
}
