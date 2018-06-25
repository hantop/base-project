package com.tuandai.bigdata.baseproject.model;

public class CentreMapModel {
    private String area;
    private String areaAmount;

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getAreaAmount() {
        return areaAmount;
    }

    public void setAreaAmount(String areaAmount) {
        this.areaAmount = areaAmount;
    }

    @Override
    public String toString() {
        return "CentreMapModel{" +
                "area='" + area + '\'' +
                ", areaAmount='" + areaAmount + '\'' +
                '}';
    }
}
