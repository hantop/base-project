package com.tuandai.bigdata.baseproject.model;

public class MonthOutPutMoneyTrendModel {
    private String sum_amt;
    private String date_time;
    private String business_type;

    public String getSumAmt() {
        return sum_amt;
    }

    public void setSumAmt(String sum_amt) {
        this.sum_amt = sum_amt;
    }

    public String getDateTime() {
        return date_time;
    }

    public void setDateTime(String date_time) {
        this.date_time = date_time;
    }


    public String getBusinessType() {
        return business_type;
    }

    public void setBusinessType(String business_type) {
        this.business_type = business_type;
    }

    @Override
    public String toString() {
        return "MonthOutPutMoneyTrendModel{" +
                "sum_amt='" + sum_amt + '\'' +
                ", date_time='" + date_time + '\'' +
                ", business_type='" + business_type + '\'' +
                '}';
    }
}
