package com.edison.testJunit.oth.ii_2_XML.parse_big_xml.util;

import java.util.List;

import com.edison.testJunit.oth.ii_2_XML.parse_big_xml.base.Range;

public class Strkit {
	/**获取在传入的StringBuffer*/
	public static Range getRange(StringBuffer sfb,List<String> listTags){
		Range range = new Range();
		int min =-1,max=-1;
		int ic=0;
		for(String tag: listTags){//TODO 还要考虑可能有内容中包含标签的情况，比如CDATA<[[]]> -这种最好用转义符号
			//起始标签有四种情况 <a>  <a/> <a id=""/> <a id=""> 除了第二种是空标签可以不管以外，其余的都是可用标签  
			if(ic==0){
				min=sfb.indexOf("<"+tag+">");
				if(min==-1){//试试查找第3,4中情况
					min=sfb.indexOf("<"+tag+" ");
				}
				if(min!=-1){ //找到起始标签
					max=sfb.lastIndexOf("</"+tag+">"); //这个就意味着从min到max可能含有多组标签对
					if(max!=-1){
						max=max+tag.length()+2; //定位到'<',要定位到'>'需要加上 '/'和tag的长度以及'>'
					}else{//试试第三种情况
						int capacity=sfb.length(),i=min+1; //sfb中内容长度  i-遍历起始下标
						char ch='a';
						for(;i<capacity;i++){ //查找起始标签对应的/>
							ch=sfb.charAt(i);
							if(ch=='<' || ch=='>'){
									break;
							}
						}
						if(ch=='>' && sfb.charAt(i-1)=='/'){
							max=i;
						}
					}
				}
			}else{ //TODO 还没考虑传入多个Class解析的情况
				int imin=sfb.indexOf("<"+tag+">");
				int imax=sfb.lastIndexOf("</"+tag+">");
				if(min!=-1){
					
					if(min>imin){
						min=imin;
					}
					imax=imax+tag.length()+3;
					
					if(max<imax){
						max=imax;
					}
					
				}
				
			}
			ic++;
			
		}
		range.setFrom(min);
		range.setTo(max);
		return range;
	}
	
	
}
