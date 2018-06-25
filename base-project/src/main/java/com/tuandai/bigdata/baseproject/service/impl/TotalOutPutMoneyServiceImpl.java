package com.tuandai.bigdata.baseproject.service.impl;

import com.tuandai.bigdata.baseproject.dao.localhost.HbaseMysqlResultMapper;
import com.tuandai.bigdata.baseproject.dao.tuandaibm.TotalOutPutMoneyMapper;
import com.tuandai.bigdata.baseproject.entity.HbaseMysqlResult;
import com.tuandai.bigdata.baseproject.model.TotalOutPutMoneyModel;
import com.tuandai.bigdata.baseproject.service.TotalOutPutMoneyService;
import com.tuandai.bigdata.baseproject.util.HBaseUtils;
import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.CellUtil;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("totalOutPutMoneyService")
public class TotalOutPutMoneyServiceImpl implements TotalOutPutMoneyService {
    protected static Logger logger = Logger.getLogger(TotalOutPutMoneyServiceImpl.class);
    @Autowired
    private TotalOutPutMoneyMapper totalOutPutMoneyMapper;
    @Autowired
    private HbaseMysqlResultMapper hbaseMysqlResultMapper;

    @Override
    public void synTotalOutPutMoney() {
        try {
            String hbase_sum_amount = "";
            TotalOutPutMoneyModel t = totalOutPutMoneyMapper.findTotalOutPutMoneyByDay();
            List<Result> list = HBaseUtils.getRowsByColumns("total_out_put_money", "sum_amount", "cf", new String[]{"sum_amount"});
            for (Result rs : list) {
                for (Cell cell : rs.listCells()) {
                    if ("sum_amount".equals(Bytes.toString(CellUtil.cloneQualifier(cell)))) {
                        hbase_sum_amount = Bytes.toString(CellUtil.cloneValue(cell));
                    }
                }
            }
            HbaseMysqlResult h = new HbaseMysqlResult();
            h.setStatus("Y");
            if (null != t && !t.getTotalSumAmt().equals(hbase_sum_amount)) {
//                HBaseUtils.insert("total_out_put_money", "sum_amount", "cf", "sum_amount", t.getTotalSumAmt() + "");
                h.setStatus("N");
            }
            h.setFieldName("total_out_put_money-"+"sum_amount");
            h.setMysqlValue(t.getTotalSumAmt());
            h.setHbaseValue(hbase_sum_amount);
            hbaseMysqlResultMapper.insert(h);

            System.out.println("mysql TotalOutPutMoneyModel=====>" + t.getTotalSumAmt());
            System.out.println("hbase TotalOutPutMoneyModel=====>" + hbase_sum_amount);
            System.out.println("================================end");
        } catch (Exception e) {
            logger.error("TotalOutPutMoneyServiceImpl.synTotalOutPutMoney error", e);
        }

    }

}
