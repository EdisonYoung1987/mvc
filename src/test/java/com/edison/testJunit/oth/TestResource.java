package com.edison.testJunit.oth;

import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import org.junit.Test;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import com.edison.db.entity.User;

/**
 * 用于测试资源文件读取*/
public class TestResource {

	@Test
	public void testResource() {
		try{
			Resource res=new ClassPathResource("springContext.xml");
			InputStream in=res.getInputStream();
			StringBuilder sb=new StringBuilder();
			
			int i=0;
			while( (i=in.read())!= -1){
				if(i<128){
					sb.append((char)i);
				}else{
					sb.append(" ");
				}
			}
			System.out.println(sb.toString());
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
