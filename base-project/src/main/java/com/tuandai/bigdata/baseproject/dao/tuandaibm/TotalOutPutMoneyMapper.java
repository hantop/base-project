package com.tuandai.bigdata.baseproject.dao.tuandaibm;

import com.tuandai.bigdata.baseproject.model.TotalOutPutMoneyModel;
import org.mybatis.spring.annotation.MapperScan;

@MapperScan
public interface TotalOutPutMoneyMapper {
    public TotalOutPutMoneyModel findTotalOutPutMoneyByDay();
}
