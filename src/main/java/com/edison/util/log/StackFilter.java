package com.edison.util.log;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.classic.spi.IThrowableProxy;
import ch.qos.logback.core.filter.Filter;
import ch.qos.logback.core.spi.FilterReply;

/**
 * ��ջ��־������.
 * <p>
 * ERROR������û�в����쳣ʱ�����ERROR.log
 * @author yangbo
 *
 */
public class StackFilter extends Filter<ILoggingEvent>
{

	@Override
	public FilterReply decide(ILoggingEvent event)
	{
		//����ERROR����ֱ������
		if( !event.getLevel().equals(Level.ERROR) )
			return FilterReply.DENY;
		
		//�����쳣���������ջ��־�����������������־
		IThrowableProxy itp = event.getThrowableProxy();
		if(itp!=null)
			return FilterReply.ACCEPT;
		else
			return FilterReply.DENY;
	}

}
