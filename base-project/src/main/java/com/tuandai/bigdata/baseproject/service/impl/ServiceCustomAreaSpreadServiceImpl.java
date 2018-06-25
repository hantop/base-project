package com.tuandai.bigdata.baseproject.service.impl;

import com.tuandai.bigdata.baseproject.dao.localhost.HbaseMysqlResultMapper;
import com.tuandai.bigdata.baseproject.dao.tuandaibm.ServiceCustomAreaSpreadMapper;
import com.tuandai.bigdata.baseproject.entity.HbaseMysqlResult;
import com.tuandai.bigdata.baseproject.model.ServiceCustomAreaSpreadModel;
import com.tuandai.bigdata.baseproject.service.ServiceCustomAreaSpreadService;
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

@Service("serviceCustomAreaSpreadService")
public class ServiceCustomAreaSpreadServiceImpl implements ServiceCustomAreaSpreadService {
    protected static Logger logger = Logger.getLogger(ServiceCustomAreaSpreadServiceImpl.class);
    @Autowired
    private ServiceCustomAreaSpreadMapper serviceCustomAreaSpreadMapper;
    @Autowired
    private HbaseMysqlResultMapper hbaseMysqlResultMapper;

    @Override
    public void synServiceCustomAreaSpread() {
        try{
            HashMap<String, String> hbaseMap = new HashMap<String, String>();
            List<Result> list = HBaseUtils.getRowsByColumns("service_custom_area_spread", "", "cf", new String[]{"area"});
            for (Result rs : list) {
                String rowkey = new String(rs.getRow());
                for (Cell cell : rs.listCells()) {
                    if ("area".equals(Bytes.toString(CellUtil.cloneQualifier(cell)))) {
                        hbaseMap.put(rowkey, Bytes.toString(CellUtil.cloneValue(cell)));
                    }
                }
            }
            String key = "";
            HbaseMysqlResult h = new HbaseMysqlResult();
            List<ServiceCustomAreaSpreadModel> modelList = serviceCustomAreaSpreadMapper.findScasList();
            for (ServiceCustomAreaSpreadModel scas : modelList) {
                h.setStatus("Y");
                key = scas.getProvince();
                if (null != key && !key.isEmpty() && (null == hbaseMap.get(key) || !scas.getCnt().equals(hbaseMap.get(key)))) {
//                    HBaseUtils.insert("service_custom_area_spread", key, "cf", "area", scas.getCnt() + "");
                    h.setStatus("N");
                }
                h.setFieldName("service_custom_area_spread-"+key);
                h.setMysqlValue(scas.getCnt());
                h.setHbaseValue(hbaseMap.get(key));
                hbaseMysqlResultMapper.insert(h);
            }
            System.out.println("mysql List<ServiceCustomAreaSpreadModel>=====>" + modelList.toString());
            System.out.println("hbase List<ServiceCustomAreaSpreadModel>=====>" + hbaseMap.toString());
            System.out.println("================================end");
        }catch (Exception e){
            logger.error("ServiceCustomAreaSpreadServiceImpl.synServiceCustomAreaSpread error",e);
        }

    }

}
