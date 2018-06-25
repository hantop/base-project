package com.tuandai.bigdata.baseproject.model;

public class TotalOutPutMoneyModel {
    private String  totalSumAmt;

    public String getTotalSumAmt() {
        return totalSumAmt;
    }

    public void setTotalSumAmt(String totalSumAmt) {
        this.totalSumAmt = totalSumAmt;
    }

    @Override
    public String toString() {
        return "TotalOutPutMoneyModel{" +
                "totalSumAmt='" + totalSumAmt + '\'' +
                '}';
    }
}
