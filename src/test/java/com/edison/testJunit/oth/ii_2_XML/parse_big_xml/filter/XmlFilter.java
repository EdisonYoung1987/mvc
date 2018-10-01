package com.edison.testJunit.oth.ii_2_XML.parse_big_xml.filter;

import org.dom4j.Element;

/**
 * 用于过滤不需要的解析的xml的Element
 * @author Edison
 *
 */
public interface XmlFilter{
	public boolean accept(Element element);
}
