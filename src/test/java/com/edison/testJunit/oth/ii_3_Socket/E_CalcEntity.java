package com.edison.testJunit.oth.ii_3_Socket;

public class E_CalcEntity {
	/**客户端传递的计算表达式[a b c]*/
	StringBuilder calString;
	
	/**服务端返回结果*/
	long   result;
	
	/**已接收并计算完毕标志*/
	boolean doneFlag;
	
	public E_CalcEntity(){
		this.calString=new StringBuilder();
		this.doneFlag=false;
		this.result=0;
	}
	
	public StringBuilder getCalString() {
		return calString;
	}

	public void setCalString(StringBuilder calString) {
		this.calString = calString;
	}
	public void addCalString(String instr){
		this.calString.append(instr);
	}

	public long getResult() {
		return result;
	}

	public void setResult(long result) {
		this.result = result;
	}

	public boolean isDoneFlag() {
		return doneFlag;
	}

	public void setDoneFlag(boolean doneFlag) {
		this.doneFlag = doneFlag;
	}
	
	public void clear(){
		this.calString=new StringBuilder();
		this.doneFlag=false;
		this.result=0;
	}
	
}
