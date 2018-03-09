package com.edison.testJunit.oth.database.springJdbc.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.edison.testJunit.oth.database.entity.Forum;

@Repository
public class Forum_SpringDao {
	 @Autowired
	    private JdbcTemplate jdbcTemplate;

	    public int insertOne(Forum forum){
	        String insSql="INSERT INTO FORUM(FORUMID,FORUMNAME,FORUMDESC) VALUES(?,?,?)";//FORUM���ֶ�forum_id�������ģ��������ﲻ��Ҫ
	        return jdbcTemplate.update(insSql,forum.getForumName(),forum.getForumDesc());
	    }
	    public int insertOne2(Forum forum){//����ķ�����update�Ĳ�����Ϊ����δָ�������ܳ�����ת�����⣬���ʹ����һ������
	        String insSql="INSERT INTO FORUM(FORUMID,FORUMNAME,FORUMDESC) VALUES(?,?,?)";//FORUM���ֶ�forum_id�������ģ��������ﲻ��Ҫ
	        Object[] params=new Object[]{forum.getForumName(),forum.getForumDesc()};
	        int[]   types=new int[]{Types.VARCHAR,Types.VARCHAR}; //�����������ṹ�����仯��ȫ����Ҫ���ĵ��鷳
	        return jdbcTemplate.update(insSql,params,types);
	    }

	    public int insertOne3(final Forum forum){//��ȡ��������
	        final String insSql="INSERT INTO FORUM(FORUMID,FORUMNAME,FORUMDESC) VALUES(?,?,?)";//inssqlҪ�����final����Ϊ�����ڲ���Ҫʹ��
	        KeyHolder keyHolder=new GeneratedKeyHolder();//һ��ͨ�õĻ�ȡkey���࣬����Ҫ�Զ���
	        jdbcTemplate.update(new PreparedStatementCreator() {
	            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
	                PreparedStatement ps=connection.prepareStatement(insSql);
	                ps.setString(1,forum.getForumName()); //��1��ʼ
	                ps.setString(2,forum.getForumDesc());
	                return ps;
	            }
	        }, keyHolder);
	        forum.setForumId(keyHolder.getKey().intValue());
	        return 1;
	    }

	    public int insertBatch(final List<Forum>  forumlist){ //�������������򵥺���,ֻ��Ҫ����������÷��������ص��ǲ�������
	        final String insSql="INSERT INTO FORUM(FORUMID,FORUMNAME,FORUMDESC) VALUES(?,?,?)";
	        int totnum=0;
	        int[] nums=jdbcTemplate.batchUpdate(insSql, new BatchPreparedStatementSetter() {
	            public void setValues(PreparedStatement preparedStatement, int idx) throws SQLException { //����������
	                preparedStatement.setString(1,forumlist.get(idx).getForumName());
	                preparedStatement.setString(2,forumlist.get(idx).getForumDesc());
	            }
	            public int getBatchSize() {//����list�Ĵ�С
	                return forumlist.size();
	            }
	        });
	        for(int i:nums){
	            totnum+=i;
	        }
	        return totnum;
	    }

	    public Forum selectOne(final String  forumName){//û��ͨ�ã������ֲ�ѯ��̳��������Ϣ����Ҫ���巵�ؽ������ӳ�䴦��
	        final String sql="SELECT * FROM FORUM WHERE FORUMNAME=?";
	        final Forum forum=new Forum(-1,"","");

	        jdbcTemplate.query(sql, new Object[]{forumName}, new RowCallbackHandler() {//����Ҫ�Լ���������Ϣ���ֶζ�Ļ���ɵ���ˡ���
	            public void processRow(ResultSet resultSet) throws SQLException {
	                forum.setForumId(resultSet.getInt("FORUMID"));
	                forum.setForumName(forumName);
	                forum.setForumDesc(resultSet.getString("FORUMDESC"));
	            }
	        });
	        if(forum.getForumId()==-1){
	            return null;
	        }
	        return forum;
	    }
	    public Forum selectOne1(Forum forum) {
	        return selectOne(forum.getForumName());
	    }

	    public List<Forum> selectList(final String  forumName){//������ģ����ѯ�б�,������ص�list����Ϊ0��ʾ�޼�¼
	        final String sql="SELECT * FROM FORUM WHERE FORUMNAME like '%'||?||'%'";
	        //final Forum forum=new Forum();//�������ŵ�������ڲ������棬��Ȼlist����Ķ�����¼Ϊͬһ��forum
	        final List<Forum> list=new ArrayList<Forum>();

	        jdbcTemplate.query(sql, new Object[]{forumName}, new RowCallbackHandler() {//����Ҫ�Լ���������Ϣ���ֶζ�Ļ���ɵ���ˡ���
	            public void processRow(ResultSet resultSet) throws SQLException {
	                Forum forum=new Forum();
	                forum.setForumId(resultSet.getInt("FORUMID"));
	                forum.setForumName(forumName);
	                forum.setForumDesc(resultSet.getString("FORUMDESC"));
	                list.add(forum);
	            }
	        });
	        return list;
	    }

	    public  List<Forum> selectList( Forum forum) {//������ģ����ѯ�б�,������ص�list����Ϊ0��ʾ�޼�¼
	        return selectList(forum.getForumName());
	    }

	    public List<Forum> selectList2(final String  forumName){//������ģ����ѯ�б�,������ص�list����Ϊ0��ʾ�޼�¼
	        final String sql="SELECT * FROM FORUM WHERE FORUMNAME like '%'||?||'%'";
	       // final Forum forum=new Forum();�������ŵ�������ڲ������棬��Ȼlist����Ķ�����¼Ϊͬһ��forum
	        //final List<Forum> list=new ArrayList<Forum>();//list�Ķ���Ҳ��RowMapper��ɶ���

	        return jdbcTemplate.query(sql, new Object[]{forumName}, new RowMapper<Forum>() { //���صľ���LIST<T>
	            public Forum mapRow(ResultSet rs,int idx) throws SQLException{
	                Forum forum=new Forum();
	                forum.setForumId(rs.getInt("FORUMID"));
	                forum.setForumName(rs.getString("FORUMNAME"));
	                forum.setForumDesc(rs.getString("FORUMDESC")); //��ÿ�����ض���add��listҲ����RowMapper��ɣ����Զ�����¼������ܷ���
	                return forum;
	            }
	        });
	       // return list;
	    }

	    /**
	     * ִ�д洢����
	     * @param
	     * @return 0-�ɹ�   1-ʧ��
	     */
	    public int executeProcedure(final int userId){ //���Ҳû�취ͨ�ã������鷳
	        String sql="{CALL P_GET_TOPIC_NUM(?,?)}";
	        return jdbcTemplate.execute(sql, new CallableStatementCallback<Integer>() {
	            public Integer doInCallableStatement(CallableStatement callableStatement) throws SQLException, DataAccessException {
	                callableStatement.setInt(1,userId);
	                callableStatement.registerOutParameter(2,Types.INTEGER);
	                callableStatement.execute();
	                return callableStatement.getInt(2);

	            }
	        });
	    }
}
