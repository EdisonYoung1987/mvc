package com.edison.testJunit.oth.protobuf;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.List;

import com.edison.testJunit.oth.protobuf.PersonProtos.Person.PhoneNumber;

/**
 * 将protobuf对象序列化*/
public class Protobuf_Writer {
	/*后附原person.proto*/
	public static void main(String[] args) {
		PersonProtos.Person.Builder builder=PersonProtos.Person.newBuilder();
		builder.setEmail("23422@QQ.COM");
		builder.setName("张三丰");
		builder.setId(1);
		
		//对内部对象赋值 按理说PhoneNumber应该是个list
		PersonProtos.Person.PhoneNumber.Builder phoneBuilder=PersonProtos.Person.PhoneNumber.newBuilder();
		phoneBuilder.setNumber("12345678901");
		phoneBuilder.setType(PersonProtos.Person.PhoneType.MOBILE);
		builder.addPhone(phoneBuilder);
		
		phoneBuilder.setNumber("12345678902");
		phoneBuilder.setType(PersonProtos.Person.PhoneType.MOBILE);
		builder.addPhone(phoneBuilder);
		
		PersonProtos.Person person = builder.build(); 
		
		try{
			//第一种方式  
			//序列化  
			byte[] data = person.toByteArray();//获取字节数组，适用于SOCKET或者保存在磁盘。  
			//反序列化  
			PersonProtos.Person result = PersonProtos.Person.parseFrom(data);  
			System.out.println(result.getName()); 
			
			//第二种序列化：粘包,将一个或者多个protobuf对象字节写入stream。  
			ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();  
			//生成一个由：[字节长度][字节数据]组成的package。特别适合RPC场景  
			person.writeDelimitedTo(byteArrayOutputStream);  
			//反序列化，从steam中读取一个或者多个protobuf字节对象  
			ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(byteArrayOutputStream.toByteArray());  
			result = PersonProtos.Person.parseDelimitedFrom(byteArrayInputStream);  
			System.out.println(result.getEmail());  
			
			//第三种序列化,写入文件或者Socket  
			System.out.println("第三种方式");
			FileOutputStream fileOutputStream = new FileOutputStream(new File("/test.dt"));  
			person.writeTo(fileOutputStream);  
			fileOutputStream.close();  
			  
			FileInputStream fileInputStream = new FileInputStream(new File("/test.dt"));  
			result = PersonProtos.Person.parseFrom(fileInputStream);  
			System.out.println(result);  
			
			List<PhoneNumber> list=result.getPhoneList();
			for(PhoneNumber phoneNumber:list){
				System.out.println(phoneNumber);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}

}
/**option java_package = "com.edison.testJunit.oth.protobuf";
option java_outer_classname="PersonProtos";

message Person {
  required string name = 1;
  required int32 id = 2;
  optional string email = 3;

  enum PhoneType {
    MOBILE = 0;
    HOME = 1;
    WORK = 2;
  }

  message PhoneNumber {
    required string number = 1;
    optional PhoneType type = 2 [default = HOME];
  }

  repeated PhoneNumber phone = 4;
}*/
