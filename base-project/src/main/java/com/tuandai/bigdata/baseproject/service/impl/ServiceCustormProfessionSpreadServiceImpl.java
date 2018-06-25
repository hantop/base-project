package com.tuandai.bigdata.baseproject.service.impl;

import com.tuandai.bigdata.baseproject.dao.localhost.HbaseMysqlResultMapper;
import com.tuandai.bigdata.baseproject.dao.tuandaibm.ServiceCustormProfessionSpreadMapper;
import com.tuandai.bigdata.baseproject.entity.HbaseMysqlResult;
import com.tuandai.bigdata.baseproject.model.ServiceCustormProfessionSpreadModel;
import com.tuandai.bigdata.baseproject.service.ServiceCustormProfessionSpreadService;
import com.tuandai.bigdata.baseproject.util.HBaseUtils;
import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.CellUtil;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service("serviceCustormProfessionSpreadService")
public class ServiceCustormProfessionSpreadServiceImpl implements ServiceCustormProfessionSpreadService {
    protected static Logger logger = Logger.getLogger(ServiceCustormProfessionSpreadServiceImpl.class);
    @Autowired
    private ServiceCustormProfessionSpreadMapper serviceCustormProfessionSpreadMapper;
    @Autowired
    private HbaseMysqlResultMapper hbaseMysqlResultMapper;

    @Override
    public void synServiceCustormProfessionSpread() {
        try{
            HashMap<String, String> hbaseMap = new HashMap<String, String>();
            List<Result> list = HBaseUtils.getRowsByColumns("service_custorm_profession_spread", "", "cf", new String[]{"profession"});
            for (Result rs : list) {
                String rowkey = new String(rs.getRow());
                for (Cell cell : rs.listCells()) {
                    if ("profession".equals(Bytes.toString(CellUtil.cloneQualifier(cell)))) {
                        hbaseMap.put(rowkey, Bytes.toString(CellUtil.cloneValue(cell)));
                    }
                }
            }
            String key = "";
            HbaseMysqlResult h = new HbaseMysqlResult();
            List<ServiceCustormProfessionSpreadModel> modelList = serviceCustormProfessionSpreadMapper.findScpsList();
            for (ServiceCustormProfessionSpreadModel scps : modelList) {
                h.setStatus("Y");
                key = scps.getProfession();
                if (null != key && !key.isEmpty() && (null == hbaseMap.get(key) || !scps.getCnt().equals(hbaseMap.get(key)))) {
//                    HBaseUtils.insert("service_custorm_profession_spread", key, "cf", "profession", scps.getCnt() + "");
                    h.setStatus("N");
                }
                h.setFieldName("service_custorm_profession_spread-"+key);
                h.setMysqlValue(scps.getCnt());
                h.setHbaseValue(hbaseMap.get(key));
                hbaseMysqlResultMapper.insert(h);
            }
            System.out.println("mysql List<ServiceCustormProfessionSpreadModel>=====>" + modelList.toString());
            System.out.println("hbase List<ServiceCustormProfessionSpreadModel>=====>" + hbaseMap.toString());
            System.out.println("================================end");
        }catch (Exception e){
            logger.error("ServiceCustormProfessionSpreadServiceImpl.synServiceCustormProfessionSpread error",e);
        }

    }

}
