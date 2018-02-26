package com.edison.controller;

import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletOutputStream;
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
    public ModelAndView register() {
    	ModelAndView mv =new ModelAndView("user/register");
    	//����ע�����jsp
    	System.out.println("Func register() do");
        //��ǰע�벿��ע������
    	List<String> citys=new ArrayList<String>();
    	citys.add("--��ѡ��--");
    	citys.add("����");
    	citys.add("����");
    	citys.add("�Ϻ�");
    	citys.add("���");
    	mv.addObject("citys", citys);
    
    	return mv;
    }
    
    @RequestMapping("/delete")
    public String delete(HttpServletRequest req,HttpServletResponse rsp) 
          throws Exception{
    	System.out.println(req.getRequestURI());
    	System.out.println(req.getServerPort());
    	System.out.println(req.getContentLength());
    	
    	System.out.println(rsp.getHeader("Content-Length"));
    	System.out.println(rsp.getCharacterEncoding());
    	
    	//����ע������jsp
        return "user/delete";
    }
    
    @RequestMapping(value="/register.do",method=RequestMethod.POST)
    public ModelAndView create(@ModelAttribute("user") User user,@RequestBody String req) {//������ǰ��ı�ע���getUser�������С�
    	System.out.println("Func create() do");
    	System.out.println(user.toString());
    	
    	//����ע��ɹ�����jsp
    	ModelAndView mv = new ModelAndView("user/success");//ָ����ͼ
        //����ͼ�������Ҫչʾ��ʹ�õ����ݣ�����ҳ����ʹ��
        mv.addObject("message", "����,"+user.getUserName()+"! ��ӭ�������ݿ⣡");
        System.out.println("["+req+"]");
        return mv;
    }
}
