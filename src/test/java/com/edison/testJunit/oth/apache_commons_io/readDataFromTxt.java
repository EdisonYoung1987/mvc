package com.edison.testJunit.oth.apache_commons_io;

import java.io.File;
import java.util.List;

import org.apache.commons.io.FileUtils;

/**为了读取数据，按长度截取各个字段并生成sql语句*/
public class readDataFromTxt {
	private final static String PROJECTDIR="C:\\Users\\Edison\\Desktop\\a.txt";//
	private final static String OUTFILE="D:\\工作\\其他\\传递外部文档.txt";
	
	public static void main(String[] args) {
		try{
			File inFile=FileUtils.getFile(PROJECTDIR); //待转码文件根路径
			File outFile=FileUtils.getFile(OUTFILE);
			
			List<String> lines=FileUtils.readLines(inFile,"UTF-8");
			
			String acctNo19,uniqNo19,writeOff2,typeCd2,type1,date8,teller6,status1;
			int uuid=0; 
			for(String line:lines){
				acctNo19=line.substring(0, 19);
				uniqNo19=line.substring(19, 38);
				writeOff2=line.substring(38, 40);
				typeCd2=line.substring(40, 42);
				type1=line.substring(42, 44);
				date8=line.substring(44, 52);
				teller6=line.substring(52, 58);
				status1=line.substring(58, 59);
				uuid++;
				String str="insert into lnmza values("+"'"+acctNo19+"','"+uniqNo19+"','"+
				    writeOff2.trim()+"','"+typeCd2.trim()+"','"+type1.trim()+"','"+date8+"','"+teller6+"','"+status1+"','"+uuid+"');\n";
				boolean append=false;
				if(uuid!=1){
					append=true;
				}
				FileUtils.write(outFile, str, "UTF-8",append);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}

}
