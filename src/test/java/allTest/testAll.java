package allTest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;
import org.testng.annotations.Test;

import com.edison.controller.UserController;

@Test(enabled=false) //表示不跑
@ContextConfiguration(locations = { 
		//"classpath:testspringContext.xml","classpath*:/web.xml" })
		"classpath:testspringContext.xml"})
public class testAll extends AbstractTestNGSpringContextTests{
	@Autowired UserController userController;
	
	private RequestMappingHandlerAdapter  handlerAdapter;  
	private MockHttpServletRequest request;  
	private MockHttpServletResponse response;  
	
	@Test
	void testUserController(){
//		userController.delete(new HttpServletRequest(),new HttpServletResponse());
		System.out.println("测试start");
		request = new MockHttpServletRequest();
		response=new MockHttpServletResponse();
		request.setRequestURI("/user/register");  
        request.addParameter("userName", "admin"); // 设置请求URL及参数  
        request.addParameter("password", "123456");  
        request.setCharacterEncoding("UTF-8");  
        request.setAttribute(HandlerMapping.INTROSPECT_TYPE_LEVEL_MAPPING, true); //Spring3.1 存在的BUG

        try{
        	ModelAndView mav = handlerAdapter.handle(request, response, userController);
        	//handler handler to use. This object must have previously been passed to
        	//the supports method of this interface, which must have returned true.

        }catch(Exception e){
        	e.printStackTrace();
        }
		System.out.println("测试end");
	}
}
