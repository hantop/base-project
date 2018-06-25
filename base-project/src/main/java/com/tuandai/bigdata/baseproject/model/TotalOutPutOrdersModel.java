package com.tuandai.bigdata.baseproject.model;

public class TotalOutPutOrdersModel {
    private String totalSumOrder;

    public String getTotalSumOrder() {
        return totalSumOrder;
    }

    public void setTotalSumOrder(String totalSumOrder) {
        this.totalSumOrder = totalSumOrder;
    }

    @Override
    public String toString() {
        return "TotalOutPutOrdersModel{" +
                "totalSumOrder='" + totalSumOrder + '\'' +
                '}';
    }
}
