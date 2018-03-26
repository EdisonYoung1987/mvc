package com.edison.testJunit.oth.program;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * ����һ���ַ������ҳ����û���ظ��ַ������ַ����ĳ���*/
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
			System.out.println(" ��maxС��ֱ���˳�");
			return;
		}
		System.out.println();
		
		//�ҵ��ظ����������ַ�
		Character cha=getMostChar(inStr, start, end);
		if(cha==null){//˵���ǲ��ظ��ַ�����ֱ�ӷ���
			return;
		}
		
		int pos1=-1,pos2=-1,pos3=-1;//���ݳ��ִ��������ַ�������ַ������д���
		for(int i=0;i<inStr.length();i++){
			if(inStr.charAt(i)==cha){
				if(i==inStr.length()-1){
					if(pos3!=-1){
						getSubString(inStr.substring(pos2+1, inStr.length()), pos2+1, pos1,lev+1); 
					}
				}
				if(pos1==-1){//��һ���ҵ�
					pos1=i;
					
					int j=i+1;//�ҵ��������ֵ�ͬһ���ַ�
					for(;j<inStr.length();j++){
						if(inStr.charAt(j)!=cha){
							break;
						}
					}
					if(j-1>i){//����������
						getSubString(inStr.substring(0, pos1+1), 0, pos1,lev+1); 
						pos1=0;
						inStr=inStr.substring(j-1);
					}
					
					continue;
				}
				if(pos2==-1){//�����Ҫ������ַ�����
					if(i-pos1==1){//�����ŵ�����
						getSubString(inStr.substring(0, pos1+1), 0, i-1,lev+1); 
						pos1=i;
						continue;
					}
					pos2=i;
					getSubString(inStr.substring(pos1, pos2), pos1, pos2,lev+1);        //abca������abc
					getSubString(inStr.substring(pos1+1, pos2+1), pos1+1, pos2+1,lev+1);//abca������bca
					pos1=i; //��ʼλ��ָ��ǰ����λ��
					pos2=-1;
				}
			}
			if(pos1==inStr.length()-1){//����ĸ���һ�γ�����ĩβ
				getSubString(inStr.substring(pos1), pos1, inStr.length()-1,lev+1); 
				return;
			}
			
			if(i==inStr.length()-1){//����ĸ���һ�γ��ֲ���ĩβ
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
		
		//�ҳ����ִ��������ַ�
		Set<Character> keyset=map.keySet();
		int most=0;   //���������ַ��Ĵ���
		Character cha = null;//���ִ��������ַ�
		for(Character ch:keyset){
			if(map.get(ch)>most){
				most=map.get(ch);
				cha=ch;
			}
		}
		System.out.println("most="+most+",cha="+cha);

		if(most==1) {//����ַ���û���ظ���
			if(length>=max){
				max=length;
				result=inStr.substring(start, end);
			}
			System.out.println("max="+max);
			return null; //��Ȼû���ظ��ģ��Ͳ����ٴ���
		}
		return cha;
	}
	
	public static void printAns(){
		System.out.println("The max length of substring with no repeating character is:"+max);
		System.out.println("The substring with no repeating character is:"+result);
	}
}
