package com.tuandai.bigdata.baseproject.dao.tuandaibm;

import com.tuandai.bigdata.baseproject.model.MonthBranchOutPutMoneyTrendModel;
import org.mybatis.spring.annotation.MapperScan;

import java.util.List;

@MapperScan
public interface MonthBranchOutPutMoneyTrendMapper {
    public List<MonthBranchOutPutMoneyTrendModel> findMbopmt();
}
