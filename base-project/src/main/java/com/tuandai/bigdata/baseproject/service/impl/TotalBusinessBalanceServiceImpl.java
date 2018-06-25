package com.tuandai.bigdata.baseproject.service.impl;

import com.tuandai.bigdata.baseproject.dao.hongtealms.TotalBusinessBalanceMapper;
import com.tuandai.bigdata.baseproject.dao.localhost.HbaseMysqlResultMapper;
import com.tuandai.bigdata.baseproject.entity.HbaseMysqlResult;
import com.tuandai.bigdata.baseproject.model.TotalBusinessBalanceModel;
import com.tuandai.bigdata.baseproject.service.TotalBusinessBalanceService;
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

@Service("totalBusinessBalanceService")
public class TotalBusinessBalanceServiceImpl implements TotalBusinessBalanceService {
    protected static Logger logger = Logger.getLogger(TotalBusinessBalanceServiceImpl.class);
    @Autowired
    private TotalBusinessBalanceMapper totalBusinessBalanceMapper;
    @Autowired
    private HbaseMysqlResultMapper hbaseMysqlResultMapper;

    @Override
    public void synTotalBusinessBalance() {
        try {
            HashMap<String, String> hbaseMap = new HashMap<String, String>();
            List<Result> list = HBaseUtils.getRowsByColumns("total_business_balance", "balance_amount", "cf", new String[]{"balance_amount"});
            for (Result rs : list) {
                String rowkey = new String(rs.getRow());
                for (Cell cell : rs.listCells()) {
                    if ("balance_amount".equals(Bytes.toString(CellUtil.cloneQualifier(cell)))) {
                        hbaseMap.put(rowkey, Bytes.toString(CellUtil.cloneValue(cell)));
                    }
                }
            }
            String key = "balance_amount";
            HbaseMysqlResult h = new HbaseMysqlResult();
            h.setStatus("Y");
            TotalBusinessBalanceModel tbb = totalBusinessBalanceMapper.findTbb();
            if (null != key && !key.isEmpty() &&  (null == hbaseMap.get(key) || !tbb.getFactAmount().equals(hbaseMap.get(key)))) {
//                HBaseUtils.insert("total_business_balance", key, "cf", "balance_amount", tbb.getFactAmount() + "");
                h.setStatus("N");
            }
            h.setFieldName("total_business_balance-"+key);
            h.setMysqlValue(tbb.getFactAmount());
            h.setHbaseValue(hbaseMap.get(key));
            hbaseMysqlResultMapper.insert(h);
            System.out.println("mysql TotalBusinessBalanceModel=====>" + tbb.getFactAmount());
            System.out.println("hbase TotalBusinessBalanceModel=====>" + hbaseMap.toString());
            System.out.println("================================end");
        } catch (Exception e) {
            logger.error("TotalBusinessBalanceServiceImpl.synTotalBusinessBalance error", e);
        }

    }

}
