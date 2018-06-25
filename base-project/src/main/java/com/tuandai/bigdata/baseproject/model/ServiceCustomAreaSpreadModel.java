package com.tuandai.bigdata.baseproject.model;

public class ServiceCustomAreaSpreadModel {
    private String province;
    private String cnt;

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCnt() {
        return cnt;
    }

    public void setCnt(String cnt) {
        this.cnt = cnt;
    }

    @Override
    public String toString() {
        return "ServiceCustomAreaSpreadModel{" +
                "province='" + province + '\'' +
                ", cnt='" + cnt + '\'' +
                '}';
    }
}
