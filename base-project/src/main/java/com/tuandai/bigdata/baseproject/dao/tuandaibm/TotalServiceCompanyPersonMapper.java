package com.tuandai.bigdata.baseproject.dao.tuandaibm;

import com.tuandai.bigdata.baseproject.model.TotalServiceCompanyPersonalModel;
import org.mybatis.spring.annotation.MapperScan;

@MapperScan
public interface TotalServiceCompanyPersonMapper {
    public TotalServiceCompanyPersonalModel findTscp();
}
