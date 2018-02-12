package com.edison.testNG.service;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.unitils.UnitilsJUnit4;
import org.unitils.spring.annotation.SpringApplicationContext;

@SpringApplicationContext({"classpath:springContext.xml"})
public class BaseServiceTest extends UnitilsJUnit4 {
	@SpringApplicationContext
	public ApplicationContext applicationContext;
	@Test
	public void testApplicationContext(){
		assertNotNull(applicationContext);
	}
}
