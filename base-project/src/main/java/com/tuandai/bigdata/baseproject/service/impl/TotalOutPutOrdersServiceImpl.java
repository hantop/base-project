package com.tuandai.bigdata.baseproject.service.impl;

import com.tuandai.bigdata.baseproject.dao.localhost.HbaseMysqlResultMapper;
import com.tuandai.bigdata.baseproject.dao.tuandaibm.TotalOutPutOrdersMapper;
import com.tuandai.bigdata.baseproject.entity.HbaseMysqlResult;
import com.tuandai.bigdata.baseproject.model.TotalOutPutOrdersModel;
import com.tuandai.bigdata.baseproject.service.TotalOutPutOrdersService;
import com.tuandai.bigdata.baseproject.util.HBaseUtils;
import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.CellUtil;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("totalOutPutOrdersService")
public class TotalOutPutOrdersServiceImpl implements TotalOutPutOrdersService {
    protected static Logger logger = Logger.getLogger(TotalOutPutOrdersServiceImpl.class);
    @Autowired
    private TotalOutPutOrdersMapper totalOutPutOrdersMapper;
    @Autowired
    private HbaseMysqlResultMapper hbaseMysqlResultMapper;

    @Override
    public void synTotalOutPutOrders() {
        try {
            String hbase_sum_order = "";
            TotalOutPutOrdersModel t = totalOutPutOrdersMapper.findTotalOutPutOrdersByDay();
            List<Result> list = HBaseUtils.getRowsByColumns("total_out_put_orders", "sum_order", "cf", new String[]{"sum_order"});
            for (Result rs : list) {
                for (Cell cell : rs.listCells()) {
                    if ("sum_order".equals(Bytes.toString(CellUtil.cloneQualifier(cell)))) {
                        hbase_sum_order = Bytes.toString(CellUtil.cloneValue(cell));
                    }
                }
            }
            HbaseMysqlResult h = new HbaseMysqlResult();
            h.setStatus("Y");
            if (null != t && !t.getTotalSumOrder().equals(hbase_sum_order)) {
//                HBaseUtils.insert("total_out_put_orders", "sum_order", "cf", "sum_order", t.getTotalSumOrder() + "");
                h.setStatus("N");
            }
            h.setFieldName("total_out_put_orders-"+"sum_order");
            h.setMysqlValue(t.getTotalSumOrder());
            h.setHbaseValue(hbase_sum_order);
            hbaseMysqlResultMapper.insert(h);
            System.out.println("mysql TotalOutPutOrdersModel=====>" + t.getTotalSumOrder());
            System.out.println("hbase TotalOutPutOrdersModel=====>" + hbase_sum_order);
            System.out.println("================================end");
        } catch (Exception e) {
            logger.error("TotalOutPutOrdersServiceImpl.synTotalOutPutOrders error", e);
        }

    }
}
