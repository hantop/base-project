package com.tuandai.bigdata.baseproject.dao.tuandaibm;

import com.tuandai.bigdata.baseproject.model.CentreMapModel;
import org.mybatis.spring.annotation.MapperScan;

import java.util.List;

@MapperScan
public interface CentreMapMapper {
    public List<CentreMapModel> findCentreMapModel();
}
