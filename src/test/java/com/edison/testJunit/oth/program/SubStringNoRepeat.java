package com.edison.testJunit.oth.program;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * 给你一个字符串，找出最长的没有重复字符的子字符串的长度*/
public class SubStringNoRepeat {
	private static int max=0;
	private static String result=null;
	
	
	public static void getSubString(String inStr,int start,int end,int lev){
		System.out.println("--------------------------------"+lev+"---------------------------------------------");
		System.out.print("inStr="+inStr+",max="+max+" ");
		if("".equals(inStr) || null==inStr){
			return;
		}
		
		if(inStr.length()==1){
			if(max==0){
				max=1;
				return;
			}
		}
		
		if(max>inStr.length()){
			System.out.println(" 比max小，直接退出");
			return;
		}
		System.out.println();
		
		//找到重复次数最多的字符
		Character cha=getMostChar(inStr, start, end);
		if(cha==null){//说明是不重复字符串，直接返回
			return;
		}
		
		int pos1=-1,pos2=-1,pos3=-1;//根据出现次数最多的字符拆分子字符串进行处理
		for(int i=0;i<inStr.length();i++){
			if(inStr.charAt(i)==cha){
				if(i==inStr.length()-1){
					if(pos3!=-1){
						getSubString(inStr.substring(pos2+1, inStr.length()), pos2+1, pos1,lev+1); 
					}
				}
				if(pos1==-1){//第一次找到
					pos1=i;
					
					int j=i+1;//找到连续出现的同一个字符
					for(;j<inStr.length();j++){
						if(inStr.charAt(j)!=cha){
							break;
						}
					}
					if(j-1>i){//有连续出现
						getSubString(inStr.substring(0, pos1+1), 0, pos1,lev+1); 
						pos1=0;
						inStr=inStr.substring(j-1);
					}
					
					continue;
				}
				if(pos2==-1){//这里就要拆分子字符串了
					if(i-pos1==1){//紧挨着的两个
						getSubString(inStr.substring(0, pos1+1), 0, i-1,lev+1); 
						pos1=i;
						continue;
					}
					pos2=i;
					getSubString(inStr.substring(pos1, pos2), pos1, pos2,lev+1);        //abca，则处理abc
					getSubString(inStr.substring(pos1+1, pos2+1), pos1+1, pos2+1,lev+1);//abca，则处理bca
					pos1=i; //起始位置指向当前结束位置
					pos2=-1;
				}
			}
			if(pos1==inStr.length()-1){//该字母最后一次出现在末尾
				getSubString(inStr.substring(pos1), pos1, inStr.length()-1,lev+1); 
				return;
			}
			
			if(i==inStr.length()-1){//该字母最后一次出现不在末尾
				getSubString(inStr.substring(pos1), pos1, inStr.length()-1,lev+1); 
			}
		}
	}
	
	public static Character getMostChar(String inStr,int start,int end){
		Map<Character,Integer> map=new HashMap<Character,Integer>();
		int length=inStr.length();
		
		for(int i=0;i<length;i++){
			Character ch=inStr.charAt(i);
			if(map.containsKey(ch)){
				map.put(ch, map.get(ch)+1);
			}else{
				map.put(ch,1);
			}
		}
		
		//找出出现次数最多的字符
		Set<Character> keyset=map.keySet();
		int most=0;   //出现最多的字符的次数
		Character cha = null;//出现次数最多的字符
		for(Character ch:keyset){
			if(map.get(ch)>most){
				most=map.get(ch);
				cha=ch;
			}
		}
		System.out.println("most="+most+",cha="+cha);

		if(most==1) {//这个字符串没有重复的
			if(length>=max){
				max=length;
				result=inStr.substring(start, end);
			}
			System.out.println("max="+max);
			return null; //既然没有重复的，就不用再处理
		}
		return cha;
	}
	
	public static void printAns(){
		System.out.println("The max length of substring with no repeating character is:"+max);
		System.out.println("The substring with no repeating character is:"+result);
	}
}
