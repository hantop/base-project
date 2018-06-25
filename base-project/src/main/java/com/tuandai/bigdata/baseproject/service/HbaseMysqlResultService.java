package com.tuandai.bigdata.baseproject.service;

import java.util.Map;

public interface HbaseMysqlResultService {
    public Map<String, Object> hbaseMysqlList(int pageon);

    void cleanHbase();

    void restart();
}
