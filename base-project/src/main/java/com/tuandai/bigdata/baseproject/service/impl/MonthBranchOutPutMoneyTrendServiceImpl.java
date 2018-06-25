package com.tuandai.bigdata.baseproject.service.impl;

import com.tuandai.bigdata.baseproject.dao.localhost.HbaseMysqlResultMapper;
import com.tuandai.bigdata.baseproject.dao.tuandaibm.MonthBranchOutPutMoneyTrendMapper;
import com.tuandai.bigdata.baseproject.entity.HbaseMysqlResult;
import com.tuandai.bigdata.baseproject.model.MonthBranchOutPutMoneyTrendModel;
import com.tuandai.bigdata.baseproject.service.MonthBranchOutPutMoneyTrendService;
import com.tuandai.bigdata.baseproject.util.DateFmt;
import com.tuandai.bigdata.baseproject.util.HBaseUtils;
import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.CellUtil;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Service("monthBranchOutPutMoneyTrendService")
public class MonthBranchOutPutMoneyTrendServiceImpl implements MonthBranchOutPutMoneyTrendService {
    protected static Logger logger = Logger.getLogger(MonthBranchOutPutMoneyTrendServiceImpl.class);
    @Autowired
    private MonthBranchOutPutMoneyTrendMapper monthOutPutMoneyTrendMapper;
    @Autowired
    private HbaseMysqlResultMapper hbaseMysqlResultMapper;

    @Override
    public void synMonthBranchOutPutMoneyTrend() {
        try {
            HashMap<String, String> hbaseMap = new HashMap<String, String>();
            List<Result> list = HBaseUtils.getRowsByColumns("every_month_branch_out_put_money_trend", "", "cf", new String[]{"date_company_type"});
            for (Result rs : list) {
                String rowkey = new String(rs.getRow());
                for (Cell cell : rs.listCells()) {
                    if ("date_company_type".equals(Bytes.toString(CellUtil.cloneQualifier(cell)))) {
                        hbaseMap.put(rowkey, Bytes.toString(CellUtil.cloneValue(cell)));
                    }
                }
            }
            HbaseMysqlResult h = new HbaseMysqlResult();
            Date now = new Date();
            String key = "";
            List<MonthBranchOutPutMoneyTrendModel> modelList = monthOutPutMoneyTrendMapper.findMbopmt();
            for (MonthBranchOutPutMoneyTrendModel mopmt : modelList) {
                h.setStatus("Y");
                key = DateFmt.getCountDate(now, DateFmt.date_month) + "_" + mopmt.getCompany() + "_" + mopmt.getBusinessType();
                if (null != key && !key.isEmpty() && (null == hbaseMap.get(key) || !mopmt.getSumAmt().equals(hbaseMap.get(key)))) {
//                    HBaseUtils.insert("every_month_branch_out_put_money_trend", key, "cf", "date_company_type", mopmt.getSumAmt() + "");
                    h.setStatus("N");
                }
                h.setFieldName("every_month_branch_out_put_money_trend-"+key);
                h.setMysqlValue(mopmt.getSumAmt());
                h.setHbaseValue(hbaseMap.get(key));
                hbaseMysqlResultMapper.insert(h);
            }
            System.out.println("mysql List<MonthBranchOutPutMoneyTrendModel>=====>" + modelList.toString());
            System.out.println("hbase List<MonthBranchOutPutMoneyTrendModel>=====>" + hbaseMap.toString());
            System.out.println("================================end");
        } catch (Exception e) {
            logger.error("MonthBranchOutPutMoneyTrendServiceImpl.synMonthBranchOutPutMoneyTrend error", e);
        }

    }

}
