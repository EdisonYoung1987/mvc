package com.edison.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        ModelAndView mv = new ModelAndView("hellospring");//指定视图
        
        //向视图中添加所要展示或使用的内容，将在页面中使用,添加map对象
        mv.addObject("message", message);
        mv.addObject("name", name);
        
        //添加list
        List<String> list=new ArrayList<String>();
        SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd");
        list.add("开发者："+"Edison");
        list.add("日期："+ df.format(new Date()));
        list.add("联系方式："+"272882055");
        mv.addObject("INFOLIST",list);
        
        //添加Map
        Map<String,String> map=new HashMap<String,String>();
        map.put("Title:","Spring MVC LEARNING");
        map.put("CHAPTER", "17TH");
        map.put("Msg", "加油");
        mv.addObject("STUDYMAP",map);
        
        return mv;
    }
}
