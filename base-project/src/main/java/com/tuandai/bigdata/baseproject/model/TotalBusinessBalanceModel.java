package com.tuandai.bigdata.baseproject.model;

public class TotalBusinessBalanceModel {
    private String factAmount;

    public String getFactAmount() {
        return factAmount;
    }
    public void setFactAmount(String factAmount) {
        this.factAmount = factAmount;
    }

    @Override
    public String toString() {
        return "TotalBusinessBalanceModel{" +
                "factAmount='" + factAmount + '\'' +
                '}';
    }
}
