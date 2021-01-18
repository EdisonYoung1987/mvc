package com.edison.testJunit.oth;

import java.io.UnsupportedEncodingException;

/**16进制转中文*/
public class HexToChinese {
    public static void main(String[] args) throws UnsupportedEncodingException {

        String str = "#\\u8f66\\u884c\\u4e91\\u7248\\u672c\\u53f7\n" +
                "version=3.1.1.0\n" +
                "#\\u901a\\u7528\\u914d\\u7f6e\n" +
                "#\\u5b9e\\u65f6\\u5904\\u7406\\u5468\\u671f\\uff0c\\u5355\\u4f4d\\u6beb\\u79d2\n" +
                "process_cycle=1000\n" +
                "#\\u6d41\\u91cf\\u7edf\\u8ba1\\u4f7f\\u7528\n" +
                "localcarplate=?\n" +
                "#mq\\u7684IP\\u5730\\u5740\n" +
                "rabbitmq_ip=10.2.111.106\n" +
                "#Redis\\u5185\\u5b58\\u6570\\u636e\\u5e93\\u5730\\u5740\n" +
                "redis_ip=10.2.25.114:9001,10.2.25.117:9001,10.2.25.118:9001\n" +
                "# zookeeper\\u5730\\u5740\n" +
                "zkHosts=10.2.25.112:2181,10.2.25.115:2181,10.2.25.116:2181\n" +
                "#kafka\\u914d\\u7f6e\n" +
                "#kafka broker\n" +
                "kafkaBroker =10.2.25.113:6667\n" +
                "#kafka \\u8d85\\u65f6\\u65f6\\u95f4\n" +
                "timeOut=100000\n" +
                "#kafka \\u6700\\u5927\\u6d88\\u606f\\u957f\\u5ea6\n" +
                "bufferSize=5000000\n" +
                "#\\u4f7f\\u7528\\u7684\\u5206\\u7ec4\\u540d\n" +
                "use_kafka_group=realtime-current\n" +
                "#kafka \\u8fc7\\u8f66\\u8bb0\\u5f55topic\n" +
                "trTopic=tfc_pass_ka\n" +
                "#\\u6279\\u91cf\\u53d6\\u6d88\\u606f\\u957f\\u5ea6\n" +
                "fetchSize=50000000\n" +
                "#\\u5957\\u724c\\u5206\\u6790\n" +
                "#\\u5957\\u724c\\u5206\\u6790\\u6700\\u5927\\u8f66\\u901fkm/h\n" +
                "copy_plate_maxspeed=160\n" +
                "#\\u5957\\u724c\\u8fc7\\u8f66\\u8bb0\\u5f55\\u7f13\\u5b58\\u4e2d\\u4fdd\\u5b58\\u65f6\\u95f4,\\u5355\\u4f4ds\n" +
                "copyplate.redis.timeout=3600\n" +
                "#\\u4e13\\u9879\\u5e03\\u63a7\n" +
                "#\\u9ad8\\u5371\\u533a\\u57df\\u8f66\\u724c\n" +
                "gwqy=\\u65b0,\\u85cf\n" +
                "#\\u4e13\\u9879\\u5e03\\u63a7\\u5047\\u724c\\u8bc6\\u522b\\u4f7f\\u7528,\\u8fd9\\u91cc\\u586b\\u5199\\u672c\\u5730\\u53f7\\u724c\\u524d\\u7f00\\u5982 \\u4eac \\u6216 \\u4eacA \\u7b49\\u7b49\n" +
                "local_hphm=?\n" +
                "#\\u4e13\\u9879\\u5e03\\u63a7\\u7ba1\\u7406\\u53f7\\u724c\\u79cd\\u7c7b 1\\u5927\\u8f662\\u5c0f\\u8f66\\u7b49\\u7b49\\uff0c\\u4f7f\\u7528\\u82f1\\u6587\\u9017\\u53f7\\u9694\\u5f00\n" +
                "JP_HPZL=1,2,15,16\n" +
                "#\\u533a\\u95f4\\u8d85\\u901f\n" +
                "#\\u533a\\u95f4\\u9650\\u884c\\u6700\\u5c0f\\u8d85\\u901f\\u6bd4\\u4f8b\\u914d\\u7f6e,\\u5c0f\\u4e8e10\\u7684\\u8d85\\u901f\\u4e0d\\u8bb0\\u5f55\n" +
                "overspeed_proportion=10\n" +
                "#\\u533a\\u95f4\\u8d85\\u901f\\u65f6\\u95f4\n" +
                "over_speed_nminute=60\n" +
                "\n" +
                "#\\u9650\\u884c\\u914d\\u7f6e\n" +
                "#\\u662f\\u5426\\u9650\\u884c\\u65b9\\u5411\n" +
                "restriction_dir_limit=0\n" +
                "#\\u9650\\u884c\\u662f\\u5426\\u91c7\\u7528\\u4e24\\u6b21\\u8fc7\\u8f66\\u53d1\\u9001\\u4e00\\u6b21\\uff0c0\\u4e00\\u6b21\\u8fc7\\u8f66\\u5c31\\u751f\\u6210\\u9650\\u884c\\u8bb0\\u5f551\\u4e8c\\u6b21\\u751f\\u6210\\u4e00\\u6761\n" +
                "restriction_frequency_enable=0\n" +
                "#restriction_frequency_enable\\u4e3a1\\u65f6\\uff0c\\u4e24\\u6b21\\u8fc7\\u8f66\\u95f4\\u9694\\u5206\\u949f\\u6570\\uff0c\\u9ed8\\u8ba410\\u5206\\u949f\\u4ee5\\u5185\\u751f\\u6210\\u9650\\u884c\\u8bb0\\u5f55\\uff0c\\u8d85\\u8fc710\\u5206\\u949f\\u4e0d\\u751f\\u4ea7\\u8bb0\\u5f55\n" +
                "restriction_valid_minute=10\n" +
                "#\\u9650\\u884c\\u6570\\u636e\\u5b58\\u653e\\u5185\\u5b58redis\\u4e2d\\u5206\\u949f\\u6570\\uff0c\\u9ed8\\u8ba424\\u5c0f\\u65f6\\uff0c\\u6ce8\\u610f\\u4e0d\\u80fd\\u5c0f\\u4e8e\\u754c\\u9762\\u8bbe\\u7f6e\\u7684\\u9650\\u884c\\u751f\\u6210\\u9891\\u6b21\n" +
                "restriction_toredis_minute=1440\n" +
                "#\\u6570\\u636e\\u5e93\\u914d\\u7f6e\n" +
                "jdbcurl=jdbc:oracle:thin:@10.2.15.16:1521:orcl\n" +
                "user=EHL_ANALYSIS\n" +
                "#\\u5bc6\\u7801\n" +
                "password=ehl1234\n" +
                "#\\u6700\\u5c0f\\u8fde\\u63a5\\u6570\n" +
                "minpoolsize=5\n" +
                "#\\u6700\\u5927\\u8fde\\u63a5\\u6570\n" +
                "maxpoolsize=10\n" +
                "#\\u521d\\u59cb\\u8fde\\u63a5\\u6570\n" +
                "initialpoolsize=5\n" +
                "#\\u6c60\\u4e2d\\u6ca1\\u6709\\u7a7a\\u95f2\\u8fde\\u63a5\\u65f6,\\u4e00\\u6b21\\u8bf7\\u6c42\\u83b7\\u53d6\\u7684\\u8fde\\u63a5\\u6570\n" +
                "acquireincrement=5\n" +
                "# \\u6c60\\u4e2d\\u8fde\\u63a5\\u6700\\u5927\\u7a7a\\u95f2\\u65f6\\u95f4\n" +
                "maxidletime=1000000\n" +
                "#\\u83b7\\u53d6\\u8fde\\u63a5\\u5931\\u8d25\\u540e,\\u91cd\\u65b0\\u5c1d\\u8bd5\\u7684\\u6b21\\u6570\n" +
                "acquireretryattempts=300\n" +
                "# \\u5c1d\\u8bd5\\u8fde\\u63a5\\u95f4\\u9694\\u65f6\\u95f4,\\u6beb\\u79d2\n" +
                "acquireretrydelay=5000\n" +
                "# \\u7b49\\u5f85\\u8fde\\u63a5\\u65f6\\u95f4,0\\u4e3a\\u65e0\\u9650\\u7b49\\u5f85,\\u6beb\\u79d2\n" +
                "checkouttimeout=100000\n" +
                "# true,false,\\u662f\\u5426\\u6536\\u56de\\u672a\\u8fd4\\u56de\\u7684\\u6d3b\\u52a8\\u8fde\\u63a5\n" +
                "debugunreturnedconnectionstacktraces=false\n" +
                "# \\u6d3b\\u52a8\\u8fde\\u63a5\\u7684\\u65f6\\u95f4.\n" +
                "unreturnedconnectiontimeout=30000";
//        str=" f:DLJTSGDSRBH                              timestamp=1577146821149, value=\\x00\\x00\\x00\\x00\\x03                         ";
        String[] strings=str.split("\n");
        System.out.println(strings.length);
        for(int i=0;i<strings.length;i++) {
           String stringss = hexStringToString(strings[i]);
            System.out.println(stringss);
        }

    }

    public static String hexStringToString(String s) {
        if (s == null || s.equals("")) {
            return null;
        }
//        s = s.replace(" ", "");
        int ind=s.indexOf("\\x");
        if(ind!=-1) {
            String sp = s.substring(0, ind);
//            System.out.println("sp=["+sp+"]");
            int ind2=s.lastIndexOf("\\x");
            String ss=s.substring(ind2+4);
//            System.out.println("ss=["+ss+"]");
            s=s.substring(ind,ind2+4);
//            System.out.println("s=["+s+"]");
            //注意：在16进制转字符串时我们要先将\x去掉再进行转码
            s=s.replaceAll("\\\\x", "");
//            System.out.println("s=["+s+"]");
            byte[] baKeyword = new byte[s.length() / 2];
//            System.out.println(baKeyword.length);
            for (int i = 0; i < baKeyword.length; i++) {
                try {
                    baKeyword[i] = (byte) (0xff & Integer.parseInt(s.substring(i * 2, i * 2 + 2), 16));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            try {
                s = new String(baKeyword, "UTF-8");
//                System.out.println("s=["+s+"]");
            } catch (Exception e1) {
                e1.printStackTrace();
            }
            return sp+s+ss;
        }else{
            return s;
        }

    }
}
