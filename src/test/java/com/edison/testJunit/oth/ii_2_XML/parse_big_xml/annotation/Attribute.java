package com.edison.testJunit.oth.ii_2_XML.parse_big_xml.annotation;
import java.lang.annotation.ElementType; 
import java.lang.annotation.Retention; 
import java.lang.annotation.RetentionPolicy; 
import java.lang.annotation.Target; 

/**用于注解标签的属性<p>
 * 如<Car id="1"> Car对象的属性id就需要注解Attribute(name="id")，否则不会被解析到*/
@Retention(RetentionPolicy.RUNTIME) 
@Target(ElementType.FIELD) 
public @ interface Attribute {
  public String name();
  public String required() default "1";//1:required which will generate the node of xml whatever the element value is null,0:not required which will generate the node of xml if  the element value is not null
}
