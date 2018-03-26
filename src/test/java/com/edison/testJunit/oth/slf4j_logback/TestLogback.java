package com.edison.testJunit.oth.slf4j_logback;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestLogback {
	Logger logger=LoggerFactory.getLogger(this.getClass());
	
	@Test
	public void testLogback(){
		logger.info("{} {}",1,2);
		logger.error("{}",4);
	}
}
