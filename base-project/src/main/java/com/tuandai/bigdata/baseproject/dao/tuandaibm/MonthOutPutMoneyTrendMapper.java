package com.tuandai.bigdata.baseproject.dao.tuandaibm;

import com.tuandai.bigdata.baseproject.model.MonthOutPutMoneyTrendModel;
import org.mybatis.spring.annotation.MapperScan;

import java.util.List;

@MapperScan
public interface MonthOutPutMoneyTrendMapper {
    public List<MonthOutPutMoneyTrendModel> findMopmt();
}
