package com.edison.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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
    public String delete() {
    	//����ע������jsp
        return "user/delete";
    }
    
    @RequestMapping(method=RequestMethod.POST)
    public ModelAndView create(User user) {
    	//����ע��ɹ�����jsp
    	  ModelAndView mv = new ModelAndView("user/success");//ָ����ͼ
          
          //����ͼ�������Ҫչʾ��ʹ�õ����ݣ�����ҳ����ʹ��
          mv.addObject("message", "����,"+user.getUserName()+"! ��ӭ�������ݿ⣡");
          return mv;
    }
}
