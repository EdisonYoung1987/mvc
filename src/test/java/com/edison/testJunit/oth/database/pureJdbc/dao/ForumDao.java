package com.edison.testJunit.oth.database.pureJdbc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.edison.testJunit.oth.database.entity.Forum;
import com.edison.testJunit.oth.database.pureJdbc.util.ConnectionUtil;

/**
 * �о�һ��sql����һ�δ��룬�ǳ��鷳��sql��仹���뽻֯��һ��<br>
 * ���һ�����м�ʮ�����ȥ������ͬ����������������ͷǳ��鷳<br>
 * �����ṹ�����ı䣬��ô���ҲҪ�ģ����鷳*/
@Component
public class ForumDao {
	public List<Forum> selectList(String forumName){ //��Forum����ģ����ѯ
//		String sql="SELECT * FROM FORUM WHERE FORUMNAME LIKE '%"+forumName+"%'";
		String sql="SELECT * FROM FORUM WHERE FORUMNAME LIKE ?"; //ʹ��ռλ�����Է�ֹsqlע��
		ResultSet rs=null;
		List<Forum> forumList=null;
		PreparedStatement statement=null;
		
		Connection con=ConnectionUtil.getConnection();
		if(con==null){
			System.out.println("��ȡ���ݿ�����Ϊnull");
			return null;
		}
		
		try{
			statement=con.prepareStatement(sql);
			statement.setString(1, "%"+forumName+"%"); //like���ֻ�ܰ�%���⣬��Ȼ�ᱨ����Ч��������
			rs=statement.executeQuery();
			forumList=new ArrayList<Forum>();
			
			while(rs.next()){
				Forum forum=new Forum();
				forum.setForumId(rs.getInt(1));
				forum.setForumName(rs.getString("FORuMNAME")); //��֪���ô�д����Сд ����Ӱ��
				forum.setForumDesc(rs.getString(3));
				forumList.add(forum);
			}
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}finally{
			ConnectionUtil.closeAll(statement, rs);
		}
		return forumList;
	}
}
