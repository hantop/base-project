package com.tuandai.bigdata.baseproject.model;

public class BorrowLimitSpreadModel {
    private String borrow_limit;
    private String cnt;

    public String getBorrow_limit() {
        return borrow_limit;
    }

    public void setBorrow_limit(String borrow_limit) {
        this.borrow_limit = borrow_limit;
    }

    public String getCnt() {
        return cnt;
    }

    public void setCnt(String cnt) {
        this.cnt = cnt;
    }

    @Override
    public String toString() {
        return "BorrowLimitSpreadModel{" +
                "borrow_limit='" + borrow_limit + '\'' +
                ", cnt='" + cnt + '\'' +
                '}';
    }
}
