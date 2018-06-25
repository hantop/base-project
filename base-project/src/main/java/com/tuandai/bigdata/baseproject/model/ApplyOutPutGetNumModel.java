package com.tuandai.bigdata.baseproject.model;

public class ApplyOutPutGetNumModel {
    private String cnt;
    private String date_time;
    private String status_type;

    public String getCnt() {
        return cnt;
    }

    public void setCnt(String cnt) {
        this.cnt = cnt;
    }

    public String getDate_time() {
        return date_time;
    }

    public void setDate_time(String date_time) {
        this.date_time = date_time;
    }

    public String getStatus_type() {
        return status_type;
    }

    public void setStatus_type(String status_type) {
        this.status_type = status_type;
    }

    @Override
    public String toString() {
        return "ApplyOutPutGetNumModel{" +
                "cnt='" + cnt + '\'' +
                ", date_time='" + date_time + '\'' +
                ", status_type='" + status_type + '\'' +
                '}';
    }
}
