package com.tuandai.bigdata.baseproject.dao.localhost;

import com.tuandai.bigdata.baseproject.entity.HbaseMysqlResult;
import org.mybatis.spring.annotation.MapperScan;

import java.util.List;
import java.util.Map;

@MapperScan
public interface HbaseMysqlResultMapper {
    public List<HbaseMysqlResult> findResult();
    public List<HbaseMysqlResult> selectPageList(Map map);
    public int selectPageListCount();
    void insert(HbaseMysqlResult hbaseMysqlResult);
}
