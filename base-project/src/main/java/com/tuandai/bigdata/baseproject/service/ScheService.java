package com.tuandai.bigdata.baseproject.service;

import com.tuandai.bigdata.baseproject.entity.HbaseMysqlResult;

import java.util.List;

public interface ScheService {
    public void synHbase();
    public List<HbaseMysqlResult> findResult();

}