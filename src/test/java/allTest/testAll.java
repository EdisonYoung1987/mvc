package allTest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.testng.annotations.Test;

import com.edison.controller.UserController;

@Test
@ContextConfiguration(locations = { "classpath:testspringContext.xml" })
public class testAll {
	@Autowired UserController userController;
	
	@Test
	void testUserController(){
//		userController.delete(new HttpServletRequest(),new HttpServletResponse());
		System.out.println("≤‚ ‘");
	}
}
