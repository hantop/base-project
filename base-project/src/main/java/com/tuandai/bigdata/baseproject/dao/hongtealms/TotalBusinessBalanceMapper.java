package com.tuandai.bigdata.baseproject.dao.hongtealms;

import com.tuandai.bigdata.baseproject.model.ServiceCustormProfessionSpreadModel;
import com.tuandai.bigdata.baseproject.model.TotalBusinessBalanceModel;
import org.mybatis.spring.annotation.MapperScan;

import java.util.List;

@MapperScan
public interface TotalBusinessBalanceMapper {
    public TotalBusinessBalanceModel findTbb();
}
