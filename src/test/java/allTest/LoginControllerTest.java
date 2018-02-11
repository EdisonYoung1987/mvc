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
	//����Request��Responseģ�����
	private MockHttpServletRequest request;
	private MockHttpServletResponse response;
	//ִ�в���ǰ�ȳ�ʼģ�����
	
	@BeforeMethod
	public void before() {
		request = new MockHttpServletRequest();
		request.setCharacterEncoding("UTF-8");
		response = new MockHttpServletResponse();
		request.setAttribute(HandlerMapping.INTROSPECT_TYPE_LEVEL_MAPPING, true); //Spring3.1 ���ڵ�BUG
	}
// ����LoginController#loginCheck()����
	@Test
	public void loginCheck() throws Exception {
		System.out.println("����2");
		//���Ե�½�ɹ������
		request.setRequestURI("/user/register");
		request.addParameter("userName", "admin"); // ��������URL������
		request.addParameter("password", "123456");
		System.out.println("����2��4");
		//����Ʒ������� �� /loginCheck.html��
		try{
			System.out.println(request+"-"+response+"-"+controller);
			handlerAdapter=new RequestMappingHandlerAdapter();
//			handlerAdapter.handle(request, response ,new HandlerMethod);
		}catch(Throwable t){
			t.printStackTrace();
		}
		System.out.println("����3");

	}
}