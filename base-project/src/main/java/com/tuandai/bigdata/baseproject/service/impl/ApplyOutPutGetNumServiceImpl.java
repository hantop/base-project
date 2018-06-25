package com.tuandai.bigdata.baseproject.service.impl;

import com.tuandai.bigdata.baseproject.dao.localhost.HbaseMysqlResultMapper;
import com.tuandai.bigdata.baseproject.dao.tuandaibm.ApplyOutPutGetNumMapper;
import com.tuandai.bigdata.baseproject.entity.HbaseMysqlResult;
import com.tuandai.bigdata.baseproject.model.ApplyOutPutGetNumModel;
import com.tuandai.bigdata.baseproject.service.ApplyOutPutGetNumService;
import com.tuandai.bigdata.baseproject.util.DateFmt;
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

@Service("applyOutPutGetNumService")
public class ApplyOutPutGetNumServiceImpl implements ApplyOutPutGetNumService {
    protected static Logger logger = Logger.getLogger(ApplyOutPutGetNumServiceImpl.class);

    @Autowired
    private ApplyOutPutGetNumMapper applyOutPutGetNumMapper;
    @Autowired
    private HbaseMysqlResultMapper hbaseMysqlResultMapper;

    @Override
    public void synApplyOutPutGetNum() {
        try {
            HashMap<String, String> hbaseMap = new HashMap<String, String>();
            List<Result> list = HBaseUtils.getRowsByColumns("apply_output_get_num", "", "cf", new String[]{"date_status"});
            for (Result rs : list) {
                String rowkey = new String(rs.getRow());
                for (Cell cell : rs.listCells()) {
                    if ("date_status".equals(Bytes.toString(CellUtil.cloneQualifier(cell)))) {
                        hbaseMap.put(rowkey, Bytes.toString(CellUtil.cloneValue(cell)));
                    }
                }
            }
            String key = "";
            HbaseMysqlResult h = new HbaseMysqlResult();
            List<ApplyOutPutGetNumModel> modelList = applyOutPutGetNumMapper.findAopgnList();
            for (ApplyOutPutGetNumModel aopgn : modelList) {
                h.setStatus("Y");
                key = DateFmt.getCountDate(aopgn.getDate_time(), DateFmt.date_month) + "_" + aopgn.getStatus_type();
                if (null != key && !key.isEmpty() && (null == hbaseMap.get(key)|| !aopgn.getCnt().equals(hbaseMap.get(key)))) {
//                    HBaseUtils.insert("apply_output_get_num", key, "cf", "date_status", aopgn.getCnt() + "");
                    h.setStatus("N");
                }
                h.setFieldName("apply_output_get_num-"+key);
                h.setMysqlValue(aopgn.getCnt());
                h.setHbaseValue(hbaseMap.get(key));
                hbaseMysqlResultMapper.insert(h);
            }
            System.out.println("mysql List<ApplyOutPutGetNumModel>=====>" + modelList.toString());
            System.out.println("hbase List<ApplyOutPutGetNumModel>=====>" + hbaseMap.toString());
            System.out.println("================================end");
        } catch (Exception e) {
            logger.error("ApplyOutPutGetNumServiceImpl.synApplyOutPutGetNum error", e);
        }

    }

}
