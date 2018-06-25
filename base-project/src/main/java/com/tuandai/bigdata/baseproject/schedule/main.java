package com.tuandai.bigdata.baseproject.schedule;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

public class main {
    public static void maina(String[] args) {

        Random random = new Random();
        boolean next = true;

        List<Integer> zlist = new ArrayList<Integer>();
        List<Integer> moneylist = new ArrayList<Integer>();
        int rate = 0;
        int zrate = 0;
        long zmoney = 0;
        for (int i = 0; i < 100000; i++) {
            int sum = 10000;

            int[] a = {0, 1};
            int c = 50;
            int z = 0;
            int maxMoney = 0;
            while (next) {
//                if (z % 10 == 0) {
//                    System.out.println("第" + z + "次sum=========>" + sum);
//                }
                z++;
                c = c * 2;
                int b = a[random.nextInt(2)];
                if (b == 0) {
                    sum = sum - c;
                    if (sum < 0) {
//                        System.out.println("多少次破产======>" + z);
                        break;
                    }
                    continue;
                }
                sum += c;
                c = 50;
                maxMoney = maxMoney > sum ? maxMoney : sum;
                next = true;
            }
            zlist.add(z);
            moneylist.add(maxMoney);
            zrate +=z;
            zmoney +=maxMoney;
//            System.out.println("max money ===================>" + maxMoney);
            if(maxMoney > 20000){
                rate ++;
            }
        }
//        System.out.println(zlist.toString());
//        System.out.println(moneylist.toString());
//        System.out.println("平均多少次破产===>" +Double.parseDouble(String.valueOf(zrate))/Double.parseDouble("10000"));
//        System.out.println("平均最高多少钱===>" +Double.parseDouble(String.valueOf(zmoney))/Double.parseDouble("10000"));
        System.out.println(Double.parseDouble(String.valueOf(rate))/Double.parseDouble("100000"));


    }

    public static void main(String[] args) {
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyyMMdd");
        Calendar calendar= Calendar.getInstance();
        calendar.add(Calendar.DATE,-59);
        System.out.println(simpleDateFormat.format(calendar.getTime()));
    }
}
