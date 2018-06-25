package com.tuandai.bigdata.baseproject.service.impl;

import com.tuandai.bigdata.baseproject.dao.localhost.HbaseMysqlResultMapper;
import com.tuandai.bigdata.baseproject.dao.tuandaibm.BorrowLimitSpreadMapper;
import com.tuandai.bigdata.baseproject.entity.HbaseMysqlResult;
import com.tuandai.bigdata.baseproject.model.BorrowLimitSpreadModel;
import com.tuandai.bigdata.baseproject.service.BorrowLimitSpreadService;
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

@Service("borrowLimitSpreadService")
public class BorrowLimitSpreadServiceImpl implements BorrowLimitSpreadService {
    protected static Logger logger = Logger.getLogger(BorrowLimitSpreadServiceImpl.class);

    @Autowired
    private BorrowLimitSpreadMapper borrowLimitSpreadMapper;
    @Autowired
    private HbaseMysqlResultMapper hbaseMysqlResultMapper;

    @Override
    public void synBorrowLimitSpread() {
        try {
            HashMap<String, String> hbaseMap = new HashMap<String, String>();
            List<Result> list = HBaseUtils.getRowsByColumns("borrow_limit_spread", "", "cf", new String[]{"sum_limit_num"});
            for (Result rs : list) {
                String rowkey = new String(rs.getRow());
                for (Cell cell : rs.listCells()) {
                    if ("sum_limit_num".equals(Bytes.toString(CellUtil.cloneQualifier(cell)))) {
                        hbaseMap.put(rowkey, Bytes.toString(CellUtil.cloneValue(cell)));
                    }
                }
            }
            String key = "";
            HbaseMysqlResult h = new HbaseMysqlResult();
            List<BorrowLimitSpreadModel> modelList = borrowLimitSpreadMapper.findBorrowLimitList();
            for (BorrowLimitSpreadModel bls : modelList) {
                h.setStatus("Y");
                key = bls.getBorrow_limit();
                if (null != key && !key.isEmpty() && (null == hbaseMap.get(key) || !bls.getCnt().equals(hbaseMap.get(key)))) {
//                    HBaseUtils.insert("borrow_limit_spread", key, "cf", "sum_limit_num", bls.getCnt() + "");
                    h.setStatus("N");
                }
                h.setFieldName("borrow_limit_spread-"+key);
                h.setMysqlValue(bls.getCnt());
                h.setHbaseValue(hbaseMap.get(key));
                hbaseMysqlResultMapper.insert(h);
            }
            System.out.println("mysql List<BorrowLimitSpreadModel>=====>" + modelList.toString());
            System.out.println("hbase List<BorrowLimitSpreadModel>=====>" + hbaseMap.toString());
            System.out.println("================================end");

        } catch (Exception e) {
            logger.error("BorrowLimitSpreadServiceImpl.synBorrowLimitSpread error", e);
        }

    }

}
