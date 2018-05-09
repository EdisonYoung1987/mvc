package com.edison.testJunit.oth.streamAndFile;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * 对象序列化<p>
 * 还有通过序列化实现对象的克隆(不完整)*/
public class D_Serializable {
	private static final String FILE="D:\\工作\\其他\\serialize.txt";
	
	public static void main(String[] args) {
//		//测试两个经理Manager共用同一个助手的情况
//		Employee em=new Employee("小习", "EAI", 500.02, "XI@XXX.COM", "234567");
//		Manager m1=new Manager("经理A", "EAIA", 10000.01, "A@XXX.COM", "123456", em,"mammm");
//		Manager m2=new Manager("经理B", "EAIB", 10001.01, "B@XXX.COM", "023456", em,"mammm");
		
		try{
//			//写对象到文件中
//			ObjectOutputStream obo=new ObjectOutputStream(new FileOutputStream(FILE));
//			obo.writeObject(em);
//			obo.writeInt(4);//存储一个整型的数字4 文件中就该是16进制的00 00 00 04 用来作为分隔符
//			obo.writeObject(m1);
//			obo.writeInt(4);//存储一个整型的数字4 文件中就该是16进制的00 00 00 04 用来作为分隔符
//			obo.writeObject(m2);
//			obo.writeInt(4);//存储一个整型的数字4 文件中就该是16进制的00 00 00 04 用来作为分隔符
//
//			
//			obo.flush();
//			obo.close();
			
			//从文件中读取对象
			ObjectInputStream obi=new ObjectInputStream(new FileInputStream(FILE));
			Employee emr=(Employee)obi.readObject();
			System.out.println(emr); //对于transient和static修饰的属性都不会有
			System.out.println(obi.readInt());//读取那个整数4

			Manager m1r=(Manager)obi.readObject();
			System.out.println(m1r);
			System.out.println(obi.readInt());//读取那个整数4
			Manager m2r=(Manager)obi.readObject();
			System.out.println(m2r);
			System.out.println(obi.readInt());//读取那个整数4

			obi.close();
			
			//利用序列化进行克隆
			Employee ema=new Employee("克隆对象", "EAI", 500.02, "XI@XXX.COM", "234567");
			System.out.println("克隆前的employee："+ema.toString());
			Employee emaclone=(Employee)cloneObj(ema);
			System.out.println("克隆后的employee："+emaclone.toString());
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	/**实现序列化对象的克隆.<p>
	 * 如果中间发生异常，则返回null，否则返回克隆对象<br>
	 * 另，原对象中的static和transient属性也不会被克隆*/
	public static  Object cloneObj(Object obj){
		try{
			ByteArrayOutputStream byteout=new ByteArrayOutputStream();
			ObjectOutputStream obo=new ObjectOutputStream(byteout);
			obo.writeObject(obj);
			byteout.close();
			
			ByteArrayInputStream bytein=new ByteArrayInputStream(byteout.toByteArray());
			ObjectInputStream obi2=new ObjectInputStream(bytein);
			Object objClone=obi2.readObject();
			
			return objClone;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
}

/**用于测试的对象-雇员*/
class Employee implements Serializable{
	private static final long serialVersionUID = 4260007050346280219L;
	private String name;
	private String department;
	private double salary;
	private String user;
	private transient String passwd; //登录密码-采用了transient关键字，这样这个字段不会被序列化
	
	public Employee(String name, String department, double salary, String user,
			String passwd) {
		this.name = name;
		this.department = department;
		this.salary = salary;
		this.user = user;
		this.passwd = passwd;
	}

	@Override
	public String toString() {
		return "Employee [name=" + name + ", department=" + department
				+ ", salary=" + salary + ", user=" + user + ", passwd="
				+ passwd + "]";
	}
}

/**经理类，假设是只有一个助手的光杆司令*/
class Manager extends Employee {
	private static String TITLE; //实际上static属性也不会被写到
	private static final long serialVersionUID = -7826924125915007092L;
	private Employee assitent;//助手
	public Manager(String name, String department, double salary, String user,
			String passwd, Employee assitent,String title) {
		super(name, department, salary, user, passwd);
		this.assitent = assitent;
		this.TITLE=title;
	}
	@Override
	public String toString() {
		return "Manager [assitent=" + assitent + ", toString()="
				+ super.toString() + "]"+" TTTLE="+TITLE;
	}
	
	
}
