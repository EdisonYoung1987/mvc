package com.edison.testJunit.oth.designPattern.a_Facory;

import com.edison.testJunit.oth.designPattern.a_Facory.a_SimpleFactory.SimpleCourseFactory;
import com.edison.testJunit.oth.designPattern.a_Facory.b_FactoryMethod.I_FactoryMethod;
import com.edison.testJunit.oth.designPattern.a_Facory.b_FactoryMethod.JavaFactoryMethod;
import com.edison.testJunit.oth.designPattern.a_Facory.b_FactoryMethod.PythonFactoryMethod;
import com.edison.testJunit.oth.designPattern.a_Facory.baseEntity.I_Course;
import com.edison.testJunit.oth.designPattern.a_Facory.baseEntity.I_Note;
import com.edison.testJunit.oth.designPattern.a_Facory.baseEntity.I_Video;
import com.edison.testJunit.oth.designPattern.a_Facory.baseEntity.PythonCourse;
import com.edison.testJunit.oth.designPattern.a_Facory.c_AbstractFactory.I_CourseFactory;
import com.edison.testJunit.oth.designPattern.a_Facory.c_AbstractFactory.JavaCourseFactory;

/**工厂模式测试类*/
public class FactoryTest {

	public static void main(String[] args) {
		//简单工厂模式
		I_Course course=SimpleCourseFactory.create("JAVA");
		course.record(); //暂不考虑null情况
		course=SimpleCourseFactory.create(PythonCourse.class);
		course.record();
		
		//工厂方法模式:工厂为抽象类，只提供制造方法，由具体产品去实现工厂接口
		I_FactoryMethod factoryMethod=new JavaFactoryMethod();
		course=factoryMethod.create();
		course.record();
		
		factoryMethod=new PythonFactoryMethod();
		course=factoryMethod.create();
		course.record();
		
		//抽象工厂模式：感觉和工厂方法模式一样，只不过是将多个产品放到一个工厂里面去了？？
		I_CourseFactory courseFactory=new JavaCourseFactory();
		I_Note iNote=courseFactory.createNote();
		I_Video iVideo=courseFactory.createVideo();
		iNote.note();
		iVideo.record();
	}

}
