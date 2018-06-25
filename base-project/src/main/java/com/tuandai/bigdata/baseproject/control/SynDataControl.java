package com.tuandai.bigdata.baseproject.control;

import com.tuandai.bigdata.baseproject.dao.localhost.HbaseMysqlResultMapper;
import com.tuandai.bigdata.baseproject.entity.HbaseMysqlResult;
import com.tuandai.bigdata.baseproject.service.HbaseMysqlResultService;
import com.tuandai.bigdata.baseproject.service.ScheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@RestController
@RequestMapping("/data")
public class SynDataControl {
    @Autowired
    private ScheService scheService;
    @Autowired
    private HbaseMysqlResultService hbaseMysqlResultService;

    @GetMapping("/syn")
    public Object findData(@RequestParam(value = "pageon", defaultValue = "1") int pageon, ModelAndView mv) {

        mv.addAllObjects(hbaseMysqlResultService.hbaseMysqlList(pageon));
        mv.setViewName("dataSyn");
        return mv;
    }

    @GetMapping("/clean")
    public Object cleanHbase(@RequestParam(value = "clean", defaultValue = "N") String clean) {
        if (clean.equals("Y")){
            hbaseMysqlResultService.cleanHbase();
        }
        ModelAndView mv = new ModelAndView();
        mv.addAllObjects(hbaseMysqlResultService.hbaseMysqlList(1));
        mv.setViewName("dataSyn");
        return mv;
    }

    @GetMapping("/restart")
    public Object restart(@RequestParam(value = "restart", defaultValue = "N") String restart) {
        if(restart.equals("Y")){
            hbaseMysqlResultService.restart();
        }
        ModelAndView mv = new ModelAndView();
        mv.addAllObjects(hbaseMysqlResultService.hbaseMysqlList(1));
        mv.setViewName("dataSyn");
        return mv;
    }
}