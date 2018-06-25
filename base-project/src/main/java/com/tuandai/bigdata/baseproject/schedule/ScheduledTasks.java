package com.tuandai.bigdata.baseproject.schedule;

import com.tuandai.bigdata.baseproject.dao.localhost.HbaseMysqlResultMapper;
import com.tuandai.bigdata.baseproject.entity.HbaseMysqlResult;
import com.tuandai.bigdata.baseproject.service.ScheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Configurable
@EnableScheduling
public class ScheduledTasks {
    @Autowired
    private ScheService scheService;
    @Autowired
    private HbaseMysqlResultMapper hbaseMysqlResultMapper;

    //每天凌晨6点执行
    @Scheduled(cron = "0 0 6 * * ?")
//    @Scheduled(cron = "0 */1 *  * * * ")
    public void reportCurrentByCron() {
        scheService.synHbase();
    }
}

