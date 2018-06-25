package com.tuandai.bigdata.baseproject.dao.tuandaibm;

import com.tuandai.bigdata.baseproject.model.ApplyOutPutGetNumModel;
import com.tuandai.bigdata.baseproject.model.ServiceCustomAreaSpreadModel;
import com.tuandai.bigdata.baseproject.model.ServiceCustormProfessionSpreadModel;
import org.mybatis.spring.annotation.MapperScan;

import java.util.List;

@MapperScan
public interface ServiceCustormProfessionSpreadMapper {

    public List<ServiceCustormProfessionSpreadModel> findScpsList();
}
