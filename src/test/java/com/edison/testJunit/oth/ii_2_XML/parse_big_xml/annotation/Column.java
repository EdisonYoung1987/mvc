package com.edison.testJunit.oth.ii_2_XML.parse_big_xml.annotation;
import java.lang.annotation.ElementType; 
import java.lang.annotation.Retention; 
import java.lang.annotation.RetentionPolicy; 
import java.lang.annotation.Target; 

/**用于注解对象中属性名称和xml中子标签不一致时的标签名称<p>
 * 如属性名称abc,子标签名称为Abc,则abc域需要注解Column(name="Abc")*/
@Retention(RetentionPolicy.RUNTIME) 
@Target(ElementType.FIELD) 
public @ interface Column {
  public String name();
  public String required() default "1";//1:required which will generate the node of xml whatever the element value is null,0:not required which will generate the node of xml if  the element value is not null
}
