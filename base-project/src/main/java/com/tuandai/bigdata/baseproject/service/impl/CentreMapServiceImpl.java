package com.tuandai.bigdata.baseproject.service.impl;

import com.tuandai.bigdata.baseproject.dao.localhost.HbaseMysqlResultMapper;
import com.tuandai.bigdata.baseproject.dao.tuandaibm.CentreMapMapper;
import com.tuandai.bigdata.baseproject.entity.HbaseMysqlResult;
import com.tuandai.bigdata.baseproject.model.CentreMapModel;
import com.tuandai.bigdata.baseproject.service.CentreMapService;
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

@Service("centreMapService")
public class CentreMapServiceImpl implements CentreMapService {
    protected static Logger logger = Logger.getLogger(CentreMapServiceImpl.class);
    @Autowired
    private CentreMapMapper centreMapMapper;
    @Autowired
    private HbaseMysqlResultMapper hbaseMysqlResultMapper;

    @Override
    public void synCentreMap() {
        try {
            HashMap<String, String> hbaseMap = new HashMap<String, String>();
            List<Result> list = HBaseUtils.getRowsByColumns("center_map", "", "cf", new String[]{"area"});
            for (Result rs : list) {
                String rowkey = new String(rs.getRow());
                for (Cell cell : rs.listCells()) {
                    if ("area".equals(Bytes.toString(CellUtil.cloneQualifier(cell)))) {
                        hbaseMap.put(rowkey, Bytes.toString(CellUtil.cloneValue(cell)));
                    }
                }
            }
            HbaseMysqlResult h = new HbaseMysqlResult();
            List<CentreMapModel> centreList = centreMapMapper.findCentreMapModel();
            for (CentreMapModel cen : centreList) {
                h.setStatus("Y");
                if (null != cen.getArea() && !cen.getArea().isEmpty() && (null == hbaseMap.get(cen.getArea()) || !cen.getAreaAmount().equals(hbaseMap.get(cen.getArea())))) {
//                    HBaseUtils.insert("center_map", cen.getArea(), "cf", "area", cen.getAreaAmount() + "");
                    h.setStatus("N");
                }
                h.setFieldName("center_map-"+cen.getArea());
                h.setMysqlValue(cen.getAreaAmount());
                h.setHbaseValue(hbaseMap.get(cen.getArea()));
                hbaseMysqlResultMapper.insert(h);
            }

            System.out.println("mysql List<CentreMapModel>=====>" + centreList.toString());
            System.out.println("hbase List<CentreMapModel>=====>" + hbaseMap.toString());
            System.out.println("================================end");
        } catch (Exception e) {
            logger.error("CentreMapServiceImpl.synCentreMap error", e);
        }

    }
}
