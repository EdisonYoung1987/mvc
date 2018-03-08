package com.edison.testJunit.oth.database.pureJdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Properties;

import org.springframework.stereotype.Component;

/**
 * 1.�򿪹رն���Ҫ�û��Լ�ȥ����<br>
 * 2.�쳣����Ҳ�Ƚ϶࣬�鷳*/
@Component
public class ConnectionUtil {
	private static ThreadLocal<Connection> threadLocal=new ThreadLocal<Connection>();
	private static String driver=null;
	private static String url;
	private static String user;
	private static String password;
	
	//��̬���룬ioc��������beanʱ�ͻ�ִ��
	public static void init() throws Throwable{
		
		Properties property=new Properties();
		property.load(ClassLoader.getSystemResourceAsStream("dataSource.properties"));
		driver=property.getProperty("driver");
		url=property.getProperty("url");
		user=property.getProperty("user");	
		password=property.getProperty("password");	
		Class.forName(driver);
		System.out.println("��ʼ��init()���");
		System.out.println("driver="+driver);
		System.out.println("url="+url);
		System.out.println("userName="+user);
		System.out.println("password="+password);
	}
	
	public static Connection getConnection(){
		if(driver==null){//���г�ʼ��
			try{
				init();
			}catch(Throwable e){
				e.printStackTrace();
				return null;
			}
			
		}
		Connection con=threadLocal.get();
		System.out.println("��ȡ���ӿ�ʼ");
		if(con==null){
			try{
				System.out.println("�������ݿ⿪ʼ...");
				con=DriverManager.getConnection(url, user, password);
				System.out.println("�������ݿ�ɹ�");
			}catch(Exception e){
				System.out.println("getConnection fail");
				e.printStackTrace();
				return null;
			}
			threadLocal.set(con);
		}
		return con;
	}
	
	public static void closeConnection(){
		 Connection con=threadLocal.get();
	        if(con!=null){
	            try {
	                con.close();
	                System.out.println("�ر����ݿ�����");
	                threadLocal.set(null); //���������
	            }catch (Exception e){
	                e.printStackTrace();
	            }
	        }
	}
	
	public static void closeStatement(Statement statement){
		if(statement!=null){
			try{
                statement.close();
                System.out.println("�ر�statement");
            }catch(Exception e){
                e.printStackTrace();
            }
		}
	}
	
	public static void closeResultSet(ResultSet rs){
		if(rs!=null){
			try{
                rs.close();
                System.out.println("�ر�resultSet");
            }catch(Exception e){
                e.printStackTrace();
            }
		}
	}
	
	public static void closeAll(Statement statement,ResultSet rs){
		closeResultSet(rs);
		closeStatement(statement);
		closeConnection();
	}
}
