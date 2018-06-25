package com.tuandai.bigdata.baseproject.entity;

public class HbaseMysqlResult {
    private int id;
    private String fieldName;
    private String mysqlValue;
    private String hbaseValue;
    private String dateTime;
    private String status;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getMysqlValue() {
        return mysqlValue;
    }

    public void setMysqlValue(String mysqlValue) {
        this.mysqlValue = mysqlValue;
    }

    public String getHbaseValue() {
        return hbaseValue;
    }

    public void setHbaseValue(String hbaseValue) {
        this.hbaseValue = hbaseValue;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
