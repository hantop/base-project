package com.tuandai.bigdata.baseproject.dao.tuandaibm;

import com.tuandai.bigdata.baseproject.model.ApplyOutPutGetNumModel;
import org.mybatis.spring.annotation.MapperScan;

import java.util.List;

@MapperScan
public interface ApplyOutPutGetNumMapper {
    public List<ApplyOutPutGetNumModel> findAopgnList();
}
