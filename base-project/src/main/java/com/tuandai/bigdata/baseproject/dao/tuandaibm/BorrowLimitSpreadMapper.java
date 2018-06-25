package com.tuandai.bigdata.baseproject.dao.tuandaibm;

import com.tuandai.bigdata.baseproject.model.ApplyOutPutGetNumModel;
import com.tuandai.bigdata.baseproject.model.BorrowLimitSpreadModel;
import org.mybatis.spring.annotation.MapperScan;

import java.util.List;

@MapperScan
public interface BorrowLimitSpreadMapper {
    public List<BorrowLimitSpreadModel> findBorrowLimitList();
}
