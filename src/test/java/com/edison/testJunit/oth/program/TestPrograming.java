package com.edison.testJunit.oth.program;

import org.junit.Test;


public class TestPrograming {
		@Test
//		@Ignore //�����ʾ��ִ�иò��Է���
		public void testSubStringNoRepeat(){
			String ins="abcdefdfabcdabbbb";
			SubStringNoRepeat.getSubString(ins, 0, ins.length()-1,0);
			SubStringNoRepeat.printAns();
		}
}
