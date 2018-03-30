package com.edison.testJunit.oth.program;

import org.junit.Test;


public class TestPrograming {
		@Test
//		@Ignore //这个表示不执行该测试方法
		public void testSubStringNoRepeat(){
			String ins="abcdefdfabcdabbbb";
			SubStringNoRepeat.getSubString(ins, 0, ins.length()-1,0);
			SubStringNoRepeat.printAns();
		}
}
