package com.edison.testJunit.oth;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import jdk.internal.util.xml.impl.ReaderUTF8;

import org.junit.Test;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import com.edison.db.entity.User;

/**
 * 用于测试资源文件读取*/
public class TestResource {

	@Test
	public void testResource() {
		try{
			//普通获取资源方式
			Resource res=new ClassPathResource("springContext.xml");
			InputStream in=res.getInputStream();
			StringBuilder sb=new StringBuilder();
			String line=null;
			
			int i=0;
			while( (i=in.read())!= -1){
				if(i<128){
					sb.append((char)i);
				}else{
					sb.append(" ");
				}
			}
			System.out.println(sb.toString());
			System.out.println("-----------------------------------------");
			
			//通用获取资源方式
			res=new PathMatchingResourcePatternResolver().getResource("classpath:springContext.xml");
			in=res.getInputStream(); //另外Resource的getFile()方法在JAR包会失效，只能getInputStream()
			BufferedReader br=new BufferedReader(new ReaderUTF8(in));
			
			while((line=br.readLine())!=null){
				System.out.println(line);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
