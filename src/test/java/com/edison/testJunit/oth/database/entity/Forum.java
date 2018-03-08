package com.edison.testJunit.oth.database.entity;

import java.io.Serializable;


public class Forum implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int forumId;
	private String forumName;
	private String forumDesc;

	public Forum(int forumId, String forumName, String forumDesc) {
		this.forumId=forumId;
		this.forumName=forumName;
		this.forumDesc=forumDesc;
	}
	public Forum(){}

	public int getForumId() {
		return forumId;
	}

	public void setForumId(int forumId) {
		this.forumId = forumId;
	}

	public String getForumName() {
		return forumName;
	}

	public void setForumName(String forumName) {
		this.forumName = forumName;
	}

	public String getForumDesc() {
		return forumDesc;
	}

	public void setForumDesc(String forumDesc) {
		this.forumDesc = forumDesc;
	}

	@Override
	public String toString() {
		return "Forum{" +
				"forumId=" + forumId +
				", forumName='" + forumName + '\'' +
				", forumDesc='" + forumDesc + '\'' +
				'}';
	}
}
