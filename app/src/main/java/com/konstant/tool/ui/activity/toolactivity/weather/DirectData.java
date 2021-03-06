package com.konstant.tool.ui.activity.toolactivity.weather;

import java.util.List;

class DirectData{


    /**
     * id : 01
     * name : 北京
     * city : [{"id":"0101","name":"北京","county":[{"id":"010101","name":"北京","weatherCode":"101010100"},{"id":"010102","name":"海淀","weatherCode":"101010200"},{"id":"010103","name":"朝阳","weatherCode":"101010300"},{"id":"010104","name":"顺义","weatherCode":"101010400"},{"id":"010105","name":"怀柔","weatherCode":"101010500"},{"id":"010106","name":"通州","weatherCode":"101010600"},{"id":"010107","name":"昌平","weatherCode":"101010700"},{"id":"010108","name":"延庆","weatherCode":"101010800"},{"id":"010109","name":"丰台","weatherCode":"101010900"},{"id":"010110","name":"石景山","weatherCode":"101011000"},{"id":"010111","name":"大兴","weatherCode":"101011100"},{"id":"010112","name":"房山","weatherCode":"101011200"},{"id":"010113","name":"密云","weatherCode":"101011300"},{"id":"010114","name":"门头沟","weatherCode":"101011400"},{"id":"010115","name":"平谷","weatherCode":"101011500"}]}]
     */

    private List<ProvinceBean> province;

    public List<ProvinceBean> getProvince() {
        return province;
    }

    public void setProvince(List<ProvinceBean> province) {
        this.province = province;
    }

    public static class ProvinceBean {
        private String id;
        private String name;
        /**
         * id : 0101
         * name : 北京
         * county : [{"id":"010101","name":"北京","weatherCode":"101010100"},{"id":"010102","name":"海淀","weatherCode":"101010200"},{"id":"010103","name":"朝阳","weatherCode":"101010300"},{"id":"010104","name":"顺义","weatherCode":"101010400"},{"id":"010105","name":"怀柔","weatherCode":"101010500"},{"id":"010106","name":"通州","weatherCode":"101010600"},{"id":"010107","name":"昌平","weatherCode":"101010700"},{"id":"010108","name":"延庆","weatherCode":"101010800"},{"id":"010109","name":"丰台","weatherCode":"101010900"},{"id":"010110","name":"石景山","weatherCode":"101011000"},{"id":"010111","name":"大兴","weatherCode":"101011100"},{"id":"010112","name":"房山","weatherCode":"101011200"},{"id":"010113","name":"密云","weatherCode":"101011300"},{"id":"010114","name":"门头沟","weatherCode":"101011400"},{"id":"010115","name":"平谷","weatherCode":"101011500"}]
         */

        private List<CityBean> city;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public List<CityBean> getCity() {
            return city;
        }

        public void setCity(List<CityBean> city) {
            this.city = city;
        }

        public static class CityBean {
            private String id;
            private String name;
            /**
             * id : 010101
             * name : 北京
             * weatherCode : 101010100
             */

            private List<CountyBean> county;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public List<CountyBean> getCounty() {
                return county;
            }

            public void setCounty(List<CountyBean> county) {
                this.county = county;
            }

            public static class CountyBean {
                private String id;
                private String name;
                private String weatherCode;

                public CountyBean() {

                }

                public CountyBean(String id, String name, String weatherCode) {
                    this.id = id;
                    this.name = name;
                    this.weatherCode = weatherCode;
                }

                public String getId() {
                    return id;
                }

                public void setId(String id) {
                    this.id = id;
                }

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }

                public String getWeatherCode() {
                    return weatherCode;
                }

                public void setWeatherCode(String weatherCode) {
                    this.weatherCode = weatherCode;
                }

                @Override
                public String toString() {
                    final StringBuilder sb = new StringBuilder("{");
                    sb.append("\"id\":\"")
                            .append(id).append('\"');
                    sb.append(",\"name\":\"")
                            .append(name).append('\"');
                    sb.append(",\"weatherCode\":\"")
                            .append(weatherCode).append('\"');
                    sb.append('}');
                    return sb.toString();
                }
            }
        }
    }
}