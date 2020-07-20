package com.edison.testJunit.oth;

import java.io.UnsupportedEncodingException;

/**16进制转中文*/
public class HexToChinese {
    public static void main(String[] args) throws UnsupportedEncodingException {

        String str = "COLUMN                                      CELL                                                                                                                        \n" +
                " f:BZ                                       timestamp=1591078836753, value=https://img2.utuku.china.com/650x0/news/20190801/8757ba40-b88a-451c-a9d4-5980a77aaf65.jpg    \n" +
                " f:CL_SD                                    timestamp=1591078836753, value=47                                                                                           \n" +
                " f:FXLX                                     timestamp=1591078836753, value=03                                                                                           \n" +
                " f:GCXH                                     timestamp=1591078836753, value=00ffe3dda4a111ea9f11e86a64faa085                                                             \n" +
                " f:GC_RQSJ                                  timestamp=1591078836753, value=1591068006269                                                                                \n" +
                " f:JDCCLLXDM                                timestamp=1591078836753, value=G34                                                                                          \n" +
                " f:JDCHPHM                                  timestamp=1591078836753, value=\\xE6\\xB8\\x9DFB1825                                                                           \n" +
                " f:JDCHPYSDM                                timestamp=1591078836753, value=                                                                                             \n" +
                " f:JDCHPZLDM                                timestamp=1591078836753, value=02                                                                                           \n" +
                " f:KKBH                                     timestamp=1591078836753, value=50010813051210000037                                                                         \n" +
                " f:SBBH                                     timestamp=1591078836753, value=                                                                                             \n" +
                " f:TP1_DZWJMC                               timestamp=1591078836753, value=https://img2.utuku.china.com/650x0/news/20190801/8757ba40-b88a-451c-a9d4-5980a77aaf65.jpg    \n" +
                " f:TP2_DZWJMC                               timestamp=1591078836753, value=https://img2.utuku.china.com/650x0/news/20190801/8757ba40-b88a-451c-a9d4-5980a77aaf65.jpg    \n" +
                " f:TP3_DZWJMC                               timestamp=1591078836753, value=https://img2.utuku.china.com/650x0/news/20200516/4b174cc0-688f-4e35-915c-de347feffa71.jpg ";
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
