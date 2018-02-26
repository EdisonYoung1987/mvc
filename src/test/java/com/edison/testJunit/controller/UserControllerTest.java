package com.edison.testJunit.controller;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.edison.controller.UserController;
import com.edison.testJunit.BaseJunitTest;

public class UserControllerTest extends BaseJunitTest{
	@Autowired
	private UserController userController;
	
	private MockMvc mockMvc;

    @Before
    public void setup(){
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    }
    
	@Test
	public void testUserController() {
		try{
			ResultActions resultActions = this.mockMvc.perform(MockMvcRequestBuilders.post("/user/register").param("id", "1"));
	        MvcResult mvcResult = resultActions.andReturn();
	        String result = mvcResult.getResponse().getContentAsString();
	        System.out.println("result="+result);
	        
	        resultActions = this.mockMvc.perform(MockMvcRequestBuilders.post("/user/delete").param("id", "1"));
	        mvcResult = resultActions.andReturn();
	        result = mvcResult.getResponse().getContentAsString();
	        System.out.println("result="+result);
		}catch(Exception e){
			e.printStackTrace();
		}
	}

}
