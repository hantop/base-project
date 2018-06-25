package com.tuandai.bigdata.baseproject.service.impl;

import ch.ethz.ssh2.Connection;
import ch.ethz.ssh2.Session;
import com.tuandai.bigdata.baseproject.dao.localhost.HbaseMysqlResultMapper;
import com.tuandai.bigdata.baseproject.service.HbaseMysqlResultService;
import com.tuandai.bigdata.baseproject.util.HBaseUtils;
import com.tuandai.bigdata.baseproject.util.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("hbaseMysqlResultService")
public class HbaseMysqlListServiceImpl implements HbaseMysqlResultService {

    @Autowired
    private HbaseMysqlResultMapper hbaseMysqlResultMapper;
    private static String[] tableArr = new String[]{"apply_output_get_num", "borrow_limit_spread", "center_map","every_month_branch_out_put_money_trend",
    "every_month_out_put_money_trend","service_custom_area_spread","service_custorm_profession_spread","total_business_balance","total_out_put_money","total_out_put_orders",
    "total_service_company_personal_num"};
    private static String[] sArr = new String[]{"cf"};
    private static String command_list = "sh /root/storm_list.sh";
    private static String command_restart = "sh /root/storm_kill_restart.sh";

    @Override
    public Map<String, Object> hbaseMysqlList(int pageon) {
        Map<String, Object> map = new HashMap<String, Object>();
        Page page = new Page(pageon);
        page.setRowcountAndCompute(hbaseMysqlResultMapper.selectPageListCount());
        map.put("page", page);
        map.put("list", hbaseMysqlResultMapper.selectPageList(map));
        return map;
    }

    @Override
    public void cleanHbase() {
        try {
            for (String table : tableArr) {
                HBaseUtils.deleteTable(table);
                HBaseUtils.creatTable(table, sArr);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void restart() {
        try {
//            Connection conn = new Connection("172.16.200.114");
//            conn.connect(); // 连接
//            conn.authenticateWithPassword("root", "hongte#4"); // 认证
//
//            Session session = conn.openSession(); // 打开一个会话
//            session.execCommand(command_list);
            Process process = Runtime.getRuntime().exec(command_list);
            InputStream in = process.getInputStream();
//            InputStream in = session.getStdout();
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            List<String> processList= processStdout(in, Charset.defaultCharset().toString());
            process.waitFor();
            List<String> topoNameList = new ArrayList<>();
            for (String ret :processList){
                if(ret.contains("ACTIVE")){
                    String[] retArr = ret.split("\\n");
                    for (String rett : retArr){
                        if(rett.contains("ACTIVE")){
                            String[] topoArr = rett.split(" ");
                            topoNameList.add(topoArr[0]);
                        }
                    }
                }
            }
            StringBuffer topoS = new StringBuffer();
            for (String topo :topoNameList){
                topoS.append(" "+topo);
            }
            System.out.println(topoS);
//            Session session2 = conn.openSession(); // 打开一个会话
//            session2.execCommand(command_restart+topoS);
            Process process2 = Runtime.getRuntime().exec(command_restart+topoS);
            in = process2.getInputStream();
//            in = session2.getStdout();
            List<String> processList2= processStdout(in, Charset.defaultCharset().toString());
            System.out.println(processList2);
//            conn.close();
            process2.waitFor();
            in.close();
            reader.close();
            process.destroy();
            process2.destroy();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*public static void main(String[] args) throws IOException {


        Connection conn = new Connection("172.16.200.114");
        conn.connect(); // 连接
        conn.authenticateWithPassword("root", "hongte#4"); // 认证

        Session session = conn.openSession(); // 打开一个会话
        session.execCommand("sh /root/storm_list.sh");

        InputStream in = session.getStdout();
        List<String> topoNameList = new ArrayList<>();
        List<String> processList= processStdout(in, Charset.defaultCharset().toString());
        for (String ret :processList){
            if(ret.contains("ACTIVE")){
                String[] retArr = ret.split("\\n");
                for (String rett : retArr){
                    if(rett.contains("ACTIVE")){
                        String[] topoArr = rett.split(" ");
                        topoNameList.add(topoArr[0]);
                    }
                }
            }
        }
        Session session2 = conn.openSession(); // 打开一个会话
        StringBuffer topoS = new StringBuffer();
        for (String topo :topoNameList){
            topoS.append(" "+topo);
        }
        System.out.println(topoS);
        session2.execCommand("sh /root/storm_kill_restart.sh"+ topoS);
         in = session2.getStdout();
        List<String> processList2= processStdout(in, Charset.defaultCharset().toString());
        System.out.println(processList2);
        conn.close();

    }
*/
    public static List<String> processStdout(InputStream in, String charset) {
        byte[] buf = new byte[1024];
        List<String> processList = new ArrayList<String>();
        try {
            while (in.read(buf) != -1) {
                processList.add(new String(buf, charset));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return processList;
    }


}
