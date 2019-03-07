package com.edison.testJunit.oth.designPattern.a_Facory.c_AbstractFactory;

import com.edison.testJunit.oth.designPattern.a_Facory.baseEntity.I_Note;
import com.edison.testJunit.oth.designPattern.a_Facory.baseEntity.I_Video;
import com.edison.testJunit.oth.designPattern.a_Facory.baseEntity.JavaNote;
import com.edison.testJunit.oth.designPattern.a_Facory.baseEntity.JavaVideo;

public class JavaCourseFactory implements I_CourseFactory {

	@Override
	public I_Note createNote() {
		return new JavaNote();
	}

	@Override
	public I_Video createVideo() {
		return new JavaVideo();
	}

}
