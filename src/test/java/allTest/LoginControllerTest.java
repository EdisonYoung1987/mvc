package allTest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.edison.controller.UserController;
@ContextConfiguration(locations = { 
		"classpath:testspringContext.xml","classpath*:/web.xml" })
		//"classpath:testspringContext.xml"})
public class LoginControllerTest extends AbstractTestNGSpringContextTests {
	private RequestMappingHandlerAdapter handlerAdapter;
	
	@Autowired
	private UserController controller;
	//声明Request与Response模拟对象
	private MockHttpServletRequest request;
	private MockHttpServletResponse response;
	//执行测试前先初始模拟对象
	
	@BeforeMethod
	public void before() {
		request = new MockHttpServletRequest();
		request.setCharacterEncoding("UTF-8");
		response = new MockHttpServletResponse();
		request.setAttribute(HandlerMapping.INTROSPECT_TYPE_LEVEL_MAPPING, true); //Spring3.1 存在的BUG
	}
// 测试LoginController#loginCheck()方法
	@Test
	public void loginCheck() throws Exception {
		System.out.println("测试2");
		//测试登陆成功的情况
		request.setRequestURI("/user/register");
		request.addParameter("userName", "admin"); // 设置请求URL及参数
		request.addParameter("password", "123456");
		System.out.println("测试2。4");
		//向控制发起请求 ” /loginCheck.html”
		try{
			System.out.println(request+"-"+response+"-"+controller);
			handlerAdapter=new RequestMappingHandlerAdapter();
//			handlerAdapter.handle(request, response ,new HandlerMethod);
		}catch(Throwable t){
			t.printStackTrace();
		}
		System.out.println("测试3");

	}
}