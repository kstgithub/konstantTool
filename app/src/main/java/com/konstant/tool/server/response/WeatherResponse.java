package com.konstant.tool.server.response;

import java.util.List;

/**
 * Created by konstant on 2017/12/29.
 */

public class WeatherResponse {

    private List<HeWeather> HeWeather6;

    public List<HeWeather> getHeWeather6() {
        return HeWeather6;
    }

    public void setHeWeather6(List<HeWeather> HeWeather6) {
        this.HeWeather6 = HeWeather6;
    }

    public static class HeWeather {
        /**
         * basic : {"cid":"CN101010300","location":"朝阳","parent_city":"北京","admin_area":"北京","cnty":"中国","lat":"39.92148972","lon":"116.48641205","tz":"+8.0"}
         * update : {"loc":"2018-02-02 14:51","utc":"2018-02-02 06:51"}
         * status : ok
         * daily_forecast : [{"cond_code_d":"100","cond_code_n":"100","cond_txt_d":"晴","cond_txt_n":"晴","date":"2018-02-02","hum":"19","mr":"19:40","ms":"08:27","pcpn":"0.0","pop":"0","pres":"1039","sr":"07:21","ss":"17:35","tmp_max":"-1","tmp_min":"-9","uv_index":"2","vis":"20","wind_deg":"349","wind_dir":"北风","wind_sc":"3-4","wind_spd":"16"},{"cond_code_d":"101","cond_code_n":"101","cond_txt_d":"多云","cond_txt_n":"多云","date":"2018-02-03","hum":"19","mr":"20:48","ms":"09:05","pcpn":"0.0","pop":"0","pres":"1038","sr":"07:20","ss":"17:36","tmp_max":"0","tmp_min":"-10","uv_index":"2","vis":"20","wind_deg":"356","wind_dir":"北风","wind_sc":"微风","wind_spd":"4"},{"cond_code_d":"100","cond_code_n":"100","cond_txt_d":"晴","cond_txt_n":"晴","date":"2018-02-04","hum":"24","mr":"21:55","ms":"09:40","pcpn":"0.0","pop":"0","pres":"1035","sr":"07:19","ss":"17:37","tmp_max":"2","tmp_min":"-8","uv_index":"2","vis":"20","wind_deg":"222","wind_dir":"西南风","wind_sc":"微风","wind_spd":"3"},{"cond_code_d":"100","cond_code_n":"100","cond_txt_d":"晴","cond_txt_n":"晴","date":"2018-02-05","hum":"23","mr":"22:58","ms":"10:11","pcpn":"0.0","pop":"0","pres":"1036","sr":"07:18","ss":"17:39","tmp_max":"1","tmp_min":"-10","uv_index":"2","vis":"20","wind_deg":"29","wind_dir":"东北风","wind_sc":"微风","wind_spd":"8"},{"cond_code_d":"101","cond_code_n":"101","cond_txt_d":"多云","cond_txt_n":"多云","date":"2018-02-06","hum":"24","mr":"00:00","ms":"10:42","pcpn":"0.0","pop":"0","pres":"1030","sr":"07:17","ss":"17:40","tmp_max":"3","tmp_min":"-7","uv_index":"2","vis":"20","wind_deg":"253","wind_dir":"西南风","wind_sc":"微风","wind_spd":"6"},{"cond_code_d":"100","cond_code_n":"100","cond_txt_d":"晴","cond_txt_n":"晴","date":"2018-02-07","hum":"21","mr":"06:45","ms":"11:14","pcpn":"0.0","pop":"0","pres":"1029","sr":"07:16","ss":"17:41","tmp_max":"3","tmp_min":"-8","uv_index":"2","vis":"20","wind_deg":"350","wind_dir":"北风","wind_sc":"3-4","wind_spd":"10"},{"cond_code_d":"100","cond_code_n":"100","cond_txt_d":"晴","cond_txt_n":"晴","date":"2018-02-08","hum":"23","mr":"00:59","ms":"11:48","pcpn":"0.0","pop":"0","pres":"1028","sr":"07:15","ss":"17:42","tmp_max":"3","tmp_min":"-7","uv_index":"2","vis":"20","wind_deg":"219","wind_dir":"西南风","wind_sc":"微风","wind_spd":"5"}]
         * sunrise_sunset : [{"$ref":"$[0].daily_forecast[0]"},{"$ref":"$[0].daily_forecast[1]"},{"$ref":"$[0].daily_forecast[2]"},{"$ref":"$[0].daily_forecast[3]"},{"$ref":"$[0].daily_forecast[4]"},{"$ref":"$[0].daily_forecast[5]"},{"$ref":"$[0].daily_forecast[6]"}]
         */

        private Basic basic;
        private UpdateBean update;
        private String status;
        private List<DailyForecast> daily_forecast;

        public Basic getBasic() {
            return basic;
        }

        public void setBasic(Basic basic) {
            this.basic = basic;
        }

        public UpdateBean getUpdate() {
            return update;
        }

        public void setUpdate(UpdateBean update) {
            this.update = update;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public List<DailyForecast> getDaily_forecast() {
            return daily_forecast;
        }

        public void setDaily_forecast(List<DailyForecast> daily_forecast) {
            this.daily_forecast = daily_forecast;
        }


        public static class Basic {
            /**
             * cid : CN101010300
             * location : 朝阳
             * parent_city : 北京
             * admin_area : 北京
             * cnty : 中国
             * lat : 39.92148972
             * lon : 116.48641205
             * tz : +8.0
             */

            private String cid;
            private String location;
            private String parent_city;
            private String admin_area;
            private String cnty;
            private String lat;
            private String lon;
            private String tz;

            public String getCid() {
                return cid;
            }

            public void setCid(String cid) {
                this.cid = cid;
            }

            public String getLocation() {
                return location;
            }

            public void setLocation(String location) {
                this.location = location;
            }

            public String getParent_city() {
                return parent_city;
            }

            public void setParent_city(String parent_city) {
                this.parent_city = parent_city;
            }

            public String getAdmin_area() {
                return admin_area;
            }

            public void setAdmin_area(String admin_area) {
                this.admin_area = admin_area;
            }

            public String getCnty() {
                return cnty;
            }

            public void setCnty(String cnty) {
                this.cnty = cnty;
            }

            public String getLat() {
                return lat;
            }

            public void setLat(String lat) {
                this.lat = lat;
            }

            public String getLon() {
                return lon;
            }

            public void setLon(String lon) {
                this.lon = lon;
            }

            public String getTz() {
                return tz;
            }

            public void setTz(String tz) {
                this.tz = tz;
            }
        }

        public static class UpdateBean {
            /**
             * loc : 2018-02-02 14:51
             * utc : 2018-02-02 06:51
             */

            private String loc;
            private String utc;

            public String getLoc() {
                return loc;
            }

            public void setLoc(String loc) {
                this.loc = loc;
            }

            public String getUtc() {
                return utc;
            }

            public void setUtc(String utc) {
                this.utc = utc;
            }
        }

        public static class DailyForecast {
            /**
             * cond_code_d : 100
             * cond_code_n : 100
             * cond_txt_d : 晴
             * cond_txt_n : 晴
             * date : 2018-02-02
             * hum : 19
             * mr : 19:40
             * ms : 08:27
             * pcpn : 0.0
             * pop : 0
             * pres : 1039
             * sr : 07:21
             * ss : 17:35
             * tmp_max : -1
             * tmp_min : -9
             * uv_index : 2
             * vis : 20
             * wind_deg : 349
             * wind_dir : 北风
             * wind_sc : 3-4
             * wind_spd : 16
             */

            private String cond_code_d;
            private String cond_code_n;
            private String cond_txt_d;
            private String cond_txt_n;
            private String date;
            private String hum;
            private String mr;
            private String ms;
            private String pcpn;
            private String pop;
            private String pres;
            private String sr;
            private String ss;
            private String tmp_max;
            private String tmp_min;
            private String uv_index;
            private String vis;
            private String wind_deg;
            private String wind_dir;
            private String wind_sc;
            private String wind_spd;

            public String getCond_code_d() {
                return cond_code_d;
            }

            public void setCond_code_d(String cond_code_d) {
                this.cond_code_d = cond_code_d;
            }

            public String getCond_code_n() {
                return cond_code_n;
            }

            public void setCond_code_n(String cond_code_n) {
                this.cond_code_n = cond_code_n;
            }

            public String getCond_txt_d() {
                return cond_txt_d;
            }

            public void setCond_txt_d(String cond_txt_d) {
                this.cond_txt_d = cond_txt_d;
            }

            public String getCond_txt_n() {
                return cond_txt_n;
            }

            public void setCond_txt_n(String cond_txt_n) {
                this.cond_txt_n = cond_txt_n;
            }

            public String getDate() {
                return date;
            }

            public void setDate(String date) {
                this.date = date;
            }

            public String getHum() {
                return hum;
            }

            public void setHum(String hum) {
                this.hum = hum;
            }

            public String getMr() {
                return mr;
            }

            public void setMr(String mr) {
                this.mr = mr;
            }

            public String getMs() {
                return ms;
            }

            public void setMs(String ms) {
                this.ms = ms;
            }

            public String getPcpn() {
                return pcpn;
            }

            public void setPcpn(String pcpn) {
                this.pcpn = pcpn;
            }

            public String getPop() {
                return pop;
            }

            public void setPop(String pop) {
                this.pop = pop;
            }

            public String getPres() {
                return pres;
            }

            public void setPres(String pres) {
                this.pres = pres;
            }

            public String getSr() {
                return sr;
            }

            public void setSr(String sr) {
                this.sr = sr;
            }

            public String getSs() {
                return ss;
            }

            public void setSs(String ss) {
                this.ss = ss;
            }

            public String getTmp_max() {
                return tmp_max;
            }

            public void setTmp_max(String tmp_max) {
                this.tmp_max = tmp_max;
            }

            public String getTmp_min() {
                return tmp_min;
            }

            public void setTmp_min(String tmp_min) {
                this.tmp_min = tmp_min;
            }

            public String getUv_index() {
                return uv_index;
            }

            public void setUv_index(String uv_index) {
                this.uv_index = uv_index;
            }

            public String getVis() {
                return vis;
            }

            public void setVis(String vis) {
                this.vis = vis;
            }

            public String getWind_deg() {
                return wind_deg;
            }

            public void setWind_deg(String wind_deg) {
                this.wind_deg = wind_deg;
            }

            public String getWind_dir() {
                return wind_dir;
            }

            public void setWind_dir(String wind_dir) {
                this.wind_dir = wind_dir;
            }

            public String getWind_sc() {
                return wind_sc;
            }

            public void setWind_sc(String wind_sc) {
                this.wind_sc = wind_sc;
            }

            public String getWind_spd() {
                return wind_spd;
            }

            public void setWind_spd(String wind_spd) {
                this.wind_spd = wind_spd;
            }
        }
    }
}
