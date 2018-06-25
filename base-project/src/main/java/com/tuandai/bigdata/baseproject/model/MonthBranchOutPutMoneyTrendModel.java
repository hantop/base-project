package com.tuandai.bigdata.baseproject.model;

public class MonthBranchOutPutMoneyTrendModel {
    private String company;
    private String sum_amt;
    private String business_type;

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getSumAmt() {
        return sum_amt;
    }

    public void setSumAmt(String sum_amt) {
        this.sum_amt = sum_amt;
    }

    public String getBusinessType() {
        return business_type;
    }

    public void setBusinessType(String business_type) {
        this.business_type = business_type;
    }

    @Override
    public String toString() {
        return "MonthBranchOutPutMoneyTrendModel{" +
                "company='" + company + '\'' +
                ", sum_amt='" + sum_amt + '\'' +
                ", bussiness_type='" + business_type + '\'' +
                '}';
    }
}
