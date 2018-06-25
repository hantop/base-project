package com.tuandai.bigdata.baseproject.service.impl;

import com.tuandai.bigdata.baseproject.dao.localhost.HbaseMysqlResultMapper;
import com.tuandai.bigdata.baseproject.entity.HbaseMysqlResult;
import com.tuandai.bigdata.baseproject.service.*;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("scheService")
public class ScheServiceImpl implements ScheService {
    protected static Logger logger = Logger.getLogger(ScheServiceImpl.class);
    @Autowired
    private TotalOutPutMoneyService totalOutPutMoneyService;
    @Autowired
    private TotalOutPutOrdersService totalOutPutOrdersService;
    @Autowired
    private CentreMapService centreMapService;
    @Autowired
    private ApplyOutPutGetNumService applyOutPutGetNumService;
    @Autowired
    private BorrowLimitSpreadService borrowLimitSpreadService;
    @Autowired
    private MonthBranchOutPutMoneyTrendService monthBranchOutPutMoneyTrendService;
    @Autowired
    private MonthOutPutMoneyTrendService monthOutPutMoneyTrendService;
    @Autowired
    private ServiceCustomAreaSpreadService serviceCustomAreaSpreadService;
    @Autowired
    private ServiceCustormProfessionSpreadService serviceCustormProfessionSpreadService;
    @Autowired
    private TotalServiceCompanyPersonalService totalServiceCompanyPersonalService;
    @Autowired
    private TotalBusinessBalanceService totalBusinessBalanceService;
    @Autowired
    private HbaseMysqlResultMapper hbaseMysqlResultMapper;

    @Override
    public void synHbase() {
        logger.info("synHbase run start===>");
        totalOutPutMoneyService.synTotalOutPutMoney();
        totalOutPutOrdersService.synTotalOutPutOrders();
        centreMapService.synCentreMap();
        applyOutPutGetNumService.synApplyOutPutGetNum();
        borrowLimitSpreadService.synBorrowLimitSpread();
        monthBranchOutPutMoneyTrendService.synMonthBranchOutPutMoneyTrend();
        monthOutPutMoneyTrendService.synMonthOutPutMoneyTrend();
        serviceCustomAreaSpreadService.synServiceCustomAreaSpread();
        serviceCustormProfessionSpreadService.synServiceCustormProfessionSpread();
        totalServiceCompanyPersonalService.synTotalServiceCompanyPerson();
        totalBusinessBalanceService.synTotalBusinessBalance();
    }

    @Override
    public List<HbaseMysqlResult> findResult() {
        return hbaseMysqlResultMapper.findResult();
    }
}