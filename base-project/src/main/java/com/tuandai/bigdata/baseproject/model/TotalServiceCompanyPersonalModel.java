package com.tuandai.bigdata.baseproject.model;

public class TotalServiceCompanyPersonalModel {
    private String sumServicePerson;

    public String getSumServicePerson() {
        return sumServicePerson;
    }

    public void setSumServicePerson(String sumServicePerson) {
        this.sumServicePerson = sumServicePerson;
    }

    @Override
    public String toString() {
        return "TotalServiceCompanyPersonalModel{" +
                "sumServicePerson='" + sumServicePerson + '\'' +
                '}';
    }
}
