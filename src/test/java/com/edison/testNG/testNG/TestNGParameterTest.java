package com.edison.testNG.testNG;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import static org.testng.Assert.*;
import org.testng.annotations.*;


public class TestNGParameterTest {
	private SimpleDateFormat simpleDateFormat;

    @DataProvider(name = "testParam")
    public static Object[][] getParamters() {
         String[][] params = {
                        { "2016-02-01 00:30:59", "yyyyMMdd", "20160201" },
                        { "2016-02-01 00:30:59", "yyyyå¹´MMæœˆddæ—?", "2016å¹?02æœ?01æ—?" },
                        { "2016-02-01 00:30:59", "HHæ—¶mmåˆ†ssç§?", "00æ—?30åˆ?59ç§?" } };
		 return params;
	}

    @Test(dataProvider = "testParam")
    public void testSimpleDateFormat(String date,String dateformat,String expectedDate) throws ParseException{
    	SimpleDateFormat df  = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    	Date d = df.parse(date);
    	simpleDateFormat = new SimpleDateFormat(dateformat);
    	String result = simpleDateFormat.format(d);
        assertEquals(result,expectedDate);
        System.out.println(result.equals(expectedDate));
    }

}
