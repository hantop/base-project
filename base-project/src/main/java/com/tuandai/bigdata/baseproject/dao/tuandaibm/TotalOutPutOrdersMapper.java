package com.tuandai.bigdata.baseproject.dao.tuandaibm;

import com.tuandai.bigdata.baseproject.model.TotalOutPutOrdersModel;
import org.mybatis.spring.annotation.MapperScan;

@MapperScan
public interface TotalOutPutOrdersMapper {
    public TotalOutPutOrdersModel findTotalOutPutOrdersByDay();
}
