package com.edison.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HelloSpringmvcController {
	String message = "Welcome to Spring MVC!";
	 
    @RequestMapping("/hello")
    //public ModelAndView showMessage(@RequestParam(value = "name", required = false, defaultValue = "Spring") String name) {
//        public ModelAndView showMessage(@PathVariable String name) {
        public ModelAndView showMessage( String name) {
        ModelAndView mv = new ModelAndView("hellospring");//ָ����ͼ
        
        //����ͼ�������Ҫչʾ��ʹ�õ����ݣ�����ҳ����ʹ��
        mv.addObject("message", message);
        mv.addObject("name", name);
        return mv;
    }
}
