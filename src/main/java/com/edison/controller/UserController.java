package com.edison.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
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
	
	@ModelAttribute("user")
	public User getUser(){
		User user=new User();
		return user;
	}
	
    @RequestMapping("/register")
    public String register() {
    	//返回注册界面jsp
    	System.out.println("Func register() do");
        return "user/register";
    }
    
    @RequestMapping("/delete")
    public String delete(HttpServletRequest req,HttpServletResponse rsp) {
    	System.out.println(req.getRequestURI());
    	System.out.println(req.getServerPort());
    	System.out.println(req.getContentLength());
    	
    	System.out.println(rsp.getHeader("Content-Length"));
    	System.out.println(rsp.getCharacterEncoding());
    	//返回注销界面jsp
        return "user/delete";
    }
    
    @RequestMapping(value="/register.do",method=RequestMethod.POST)
    public ModelAndView create(@ModelAttribute("user") User user,@RequestBody String req) {//必须有前面的被注解的getUser方法才行。
    	System.out.println("Func create() do");

    	//返回注册成功界面jsp
    	  ModelAndView mv = new ModelAndView("user/success");//指定视图
          System.out.println(user.getUserName());
          System.out.println(user.getPassword());
          //向视图中添加所要展示或使用的内容，将在页面中使用
          mv.addObject("message", "您好,"+user.getUserName()+"! 欢迎加入数据库！");
          
          System.out.println("["+req+"]");
          return mv;
    }
}
