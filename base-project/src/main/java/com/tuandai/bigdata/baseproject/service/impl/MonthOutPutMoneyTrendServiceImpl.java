package com.tuandai.bigdata.baseproject.service.impl;

import com.tuandai.bigdata.baseproject.dao.localhost.HbaseMysqlResultMapper;
import com.tuandai.bigdata.baseproject.dao.tuandaibm.MonthOutPutMoneyTrendMapper;
import com.tuandai.bigdata.baseproject.entity.HbaseMysqlResult;
import com.tuandai.bigdata.baseproject.model.MonthOutPutMoneyTrendModel;
import com.tuandai.bigdata.baseproject.service.MonthOutPutMoneyTrendService;
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

@Service("monthOutPutMoneyTrendService")
public class MonthOutPutMoneyTrendServiceImpl implements MonthOutPutMoneyTrendService {
    protected static Logger logger = Logger.getLogger(MonthOutPutMoneyTrendServiceImpl.class);
    @Autowired
    private MonthOutPutMoneyTrendMapper monthOutPutMoneyTrendMapper;
    @Autowired
    private HbaseMysqlResultMapper hbaseMysqlResultMapper;

    @Override
    public void synMonthOutPutMoneyTrend() {
        try {
            HashMap<String, String> hbaseMap = new HashMap<String, String>();
            List<Result> list = HBaseUtils.getRowsByColumns("every_month_out_put_money_trend", "", "cf", new String[]{"date_type_amount"});
            for (Result rs : list) {
                String rowkey = new String(rs.getRow());
                for (Cell cell : rs.listCells()) {
                    if ("date_type_amount".equals(Bytes.toString(CellUtil.cloneQualifier(cell)))) {
                        hbaseMap.put(rowkey, Bytes.toString(CellUtil.cloneValue(cell)));
                    }
                }
            }
            HbaseMysqlResult h = new HbaseMysqlResult();
            List<MonthOutPutMoneyTrendModel> modelList = monthOutPutMoneyTrendMapper.findMopmt();
            String key = "";
            for (MonthOutPutMoneyTrendModel mopmt : modelList) {
                h.setStatus("Y");
                key = DateFmt.getCountDate(mopmt.getDateTime(), DateFmt.date_month) + "_" + mopmt.getBusinessType();
                if (null != key && !key.isEmpty() && (null == hbaseMap.get(key)|| !mopmt.getSumAmt().equals(hbaseMap.get(key)))) {
//                    HBaseUtils.insert("every_month_out_put_money_trend", DateFmt.getCountDate(mopmt.getDateTime(), DateFmt.date_month) + "_" + mopmt.getBusinessType(), "cf", "date_type_amount", mopmt.getSumAmt() + "");
                    h.setStatus("N");
                }
                h.setFieldName("every_month_out_put_money_trend-"+key);
                h.setMysqlValue(mopmt.getSumAmt());
                h.setHbaseValue(hbaseMap.get(key));
                hbaseMysqlResultMapper.insert(h);
            }
            System.out.println("mysql List<MonthOutPutMoneyTrendModel>=====>" + modelList.toString());
            System.out.println("hbase List<MonthOutPutMoneyTrendModel>=====>" + hbaseMap.toString());
            System.out.println("================================end");
        } catch (Exception e) {
            logger.error("MonthOutPutMoneyTrendServiceImpl.synMonthOutPutMoneyTrend error", e);
        }

    }

}
