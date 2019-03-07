package com.edison.testJunit.oth.designPattern.a_Facory.b_FactoryMethod;

import com.edison.testJunit.oth.designPattern.a_Facory.baseEntity.I_Course;
import com.edison.testJunit.oth.designPattern.a_Facory.baseEntity.JavaCourse;

public class JavaFactoryMethod implements I_FactoryMethod {

	@Override
	public I_Course create() {
		return new JavaCourse();
	}

}
