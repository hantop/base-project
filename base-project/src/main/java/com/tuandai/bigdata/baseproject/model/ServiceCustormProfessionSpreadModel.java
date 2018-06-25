package com.tuandai.bigdata.baseproject.model;

public class ServiceCustormProfessionSpreadModel {

    private String profession;
    private String cnt;

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    public String getCnt() {
        return cnt;
    }

    public void setCnt(String cnt) {
        this.cnt = cnt;
    }

    @Override
    public String toString() {
        return "ServiceCustormProfessionSpreadModel{" +
                "profession='" + profession + '\'' +
                ", cnt='" + cnt + '\'' +
                '}';
    }
}
