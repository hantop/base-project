package com.tuandai.bigdata.baseproject.service.impl;

import com.tuandai.bigdata.baseproject.dao.localhost.HbaseMysqlResultMapper;
import com.tuandai.bigdata.baseproject.dao.tuandaibm.TotalServiceCompanyPersonMapper;
import com.tuandai.bigdata.baseproject.entity.HbaseMysqlResult;
import com.tuandai.bigdata.baseproject.model.TotalServiceCompanyPersonalModel;
import com.tuandai.bigdata.baseproject.service.TotalServiceCompanyPersonalService;
import com.tuandai.bigdata.baseproject.util.HBaseUtils;
import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.CellUtil;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("totalServiceCompanyPersonalService")
public class TotalServiceCompanyPersonalServiceImpl implements TotalServiceCompanyPersonalService {
    protected static Logger logger = Logger.getLogger(TotalServiceCompanyPersonalServiceImpl.class);
    @Autowired
    private TotalServiceCompanyPersonMapper totalServiceCompanyPersonMapper;
    @Autowired
    private HbaseMysqlResultMapper hbaseMysqlResultMapper;

    @Override
    public void synTotalServiceCompanyPerson() {
        try {
            String hbase_sum_service_person = "";
            TotalServiceCompanyPersonalModel t = totalServiceCompanyPersonMapper.findTscp();
            List<Result> list = HBaseUtils.getRowsByColumns("total_service_company_personal_num", "sum_service_person", "cf", new String[]{"sum_service_person"});
            for (Result rs : list) {
                for (Cell cell : rs.listCells()) {
                    if ("sum_service_person".equals(Bytes.toString(CellUtil.cloneQualifier(cell)))) {
                        hbase_sum_service_person = Bytes.toString(CellUtil.cloneValue(cell));
                    }
                }
            }
            HbaseMysqlResult h = new HbaseMysqlResult();
            h.setStatus("Y");
            if (null != t && !t.getSumServicePerson().equals(hbase_sum_service_person)) {
//                HBaseUtils.insert("total_service_company_personal_num", "sum_service_person", "cf", "sum_service_person", t.getSumServicePerson() + "");
                h.setStatus("N");
            }
            h.setFieldName("total_service_company_personal_num-"+"sum_service_person");
            h.setMysqlValue(t.getSumServicePerson());
            h.setHbaseValue(hbase_sum_service_person);
            hbaseMysqlResultMapper.insert(h);
            System.out.println("mysql TotalServiceCompanyPersonalModel=====>" + t.getSumServicePerson());
            System.out.println("hbase TotalServiceCompanyPersonalModel=====>" + hbase_sum_service_person);
            System.out.println("================================end");
        } catch (Exception e) {
            logger.error("TotalServiceCompanyPersonalServiceImpl.synTotalServiceCompanyPerson error",e);
        }

    }
}
