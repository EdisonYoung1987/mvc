package com.edison.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.edison.db.entity.User;

@Controller
@RequestMapping("/user")
public class UserController {
	@Autowired 
	private User user;
	String message = "Welcome to Spring MVC!";
	 
    @RequestMapping("/register")
    public String register() {
    	//����ע�����jsp
        return "user/register";
    }
    
    @RequestMapping("/delete")
    public String delete(HttpServletRequest req,HttpServletResponse rsp) {
    	System.out.println(req.getRequestURI());
    	System.out.println(req.getServerPort());
    	System.out.println(req.getContentLength());
    	
    	System.out.println(rsp.getHeader("Content-Length"));
    	System.out.println(rsp.getCharacterEncoding());
    	//����ע������jsp
        return "user/delete";
    }
    
    @RequestMapping(method=RequestMethod.POST)
    public ModelAndView create(User user,@RequestBody String req) {
    	//����ע��ɹ�����jsp
    	  ModelAndView mv = new ModelAndView("user/success");//ָ����ͼ
          
          //����ͼ�������Ҫչʾ��ʹ�õ����ݣ�����ҳ����ʹ��
          mv.addObject("message", "����,"+user.getUserName()+"! ��ӭ�������ݿ⣡");
          
          System.out.println("["+req+"]");
          return mv;
    }
}
