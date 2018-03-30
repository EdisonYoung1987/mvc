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
	        String insSql="INSERT INTO FORUM(FORUMID,FORUMNAME,FORUMDESC) VALUES(?,?,?)";//FORUM的字段forum_id是自增的，所以这里不需要
	        return jdbcTemplate.update(insSql,forum.getForumName(),forum.getForumDesc());
	    }
	    public int insertOne2(Forum forum){//上面的方法，update的参数因为类型未指定，可能出类型转换问题，最好使用另一个方法
	        String insSql="INSERT INTO FORUM(FORUMID,FORUMNAME,FORUMDESC) VALUES(?,?,?)";//FORUM的字段forum_id是自增的，所以这里不需要
	        Object[] params=new Object[]{forum.getForumName(),forum.getForumDesc()};
	        int[]   types=new int[]{Types.VARCHAR,Types.VARCHAR}; //都存在如果表结构发生变化就全部需要更改的麻烦
	        return jdbcTemplate.update(insSql,params,types);
	    }

	    public int insertOne3(final Forum forum){//获取自增主键
	        final String insSql="INSERT INTO FORUM(FORUMID,FORUMNAME,FORUMDESC) VALUES(?,?,?)";//inssql要定义成final，因为后面内部类要使用
	        KeyHolder keyHolder=new GeneratedKeyHolder();//一个通用的获取key的类，否则要自定义
	        jdbcTemplate.update(new PreparedStatementCreator() {
	            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
	                PreparedStatement ps=connection.prepareStatement(insSql);
	                ps.setString(1,forum.getForumName()); //从1开始
	                ps.setString(2,forum.getForumDesc());
	                return ps;
	            }
	        }, keyHolder);
	        forum.setForumId(keyHolder.getKey().intValue());
	        return 1;
	    }

	    public int insertBatch(final List<Forum>  forumlist){ //批量插入这个最简单合适,只需要定义参数设置方法，返回的是插入条数
	        final String insSql="INSERT INTO FORUM(FORUMID,FORUMNAME,FORUMDESC) VALUES(?,?,?)";
	        int totnum=0;
	        int[] nums=jdbcTemplate.batchUpdate(insSql, new BatchPreparedStatementSetter() {
	            public void setValues(PreparedStatement preparedStatement, int idx) throws SQLException { //参数的设置
	                preparedStatement.setString(1,forumlist.get(idx).getForumName());
	                preparedStatement.setString(2,forumlist.get(idx).getForumDesc());
	            }
	            public int getBatchSize() {//对象list的大小
	                return forumlist.size();
	            }
	        });
	        for(int i:nums){
	            totnum+=i;
	        }
	        return totnum;
	    }

	    public Forum selectOne(final String  forumName){//没法通用，按名字查询论坛的所有信息，需要定义返回结果集的映射处理
	        final String sql="SELECT * FROM FORUM WHERE FORUMNAME=?";
	        final Forum forum=new Forum(-1,"","");

	        jdbcTemplate.query(sql, new Object[]{forumName}, new RowCallbackHandler() {//还是要自己处理返回信息，字段多的话就傻逼了。。
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

	    public List<Forum> selectList(final String  forumName){//按名字模糊查询列表,如果返回的list长度为0表示无记录
	        final String sql="SELECT * FROM FORUM WHERE FORUMNAME like '%'||?||'%'";
	        //final Forum forum=new Forum();//这个必须放到定义的内部类里面，不然list里面的多条记录为同一个forum
	        final List<Forum> list=new ArrayList<Forum>();

	        jdbcTemplate.query(sql, new Object[]{forumName}, new RowCallbackHandler() {//还是要自己处理返回信息，字段多的话就傻逼了。。
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

	    public  List<Forum> selectList( Forum forum) {//按名字模糊查询列表,如果返回的list长度为0表示无记录
	        return selectList(forum.getForumName());
	    }

	    public List<Forum> selectList2(final String  forumName){//按名字模糊查询列表,如果返回的list长度为0表示无记录
	        final String sql="SELECT * FROM FORUM WHERE FORUMNAME like '%'||?||'%'";
	       // final Forum forum=new Forum();这个必须放到定义的内部类里面，不然list里面的多条记录为同一个forum
	        //final List<Forum> list=new ArrayList<Forum>();//list的定义也由RowMapper完成定义

	        return jdbcTemplate.query(sql, new Object[]{forumName}, new RowMapper<Forum>() { //返回的就是LIST<T>
	            public Forum mapRow(ResultSet rs,int idx) throws SQLException{
	                Forum forum=new Forum();
	                forum.setForumId(rs.getInt("FORUMID"));
	                forum.setForumName(rs.getString("FORUMNAME"));
	                forum.setForumDesc(rs.getString("FORUMDESC")); //将每个返回对象add到list也是有RowMapper完成，所以多条记录用这个很方便
	                return forum;
	            }
	        });
	       // return list;
	    }

	    /**
	     * 执行存储过程
	     * @param
	     * @return 0-成功   1-失败
	     */
	    public int executeProcedure(final int userId){ //这个也没办法通用，真是麻烦
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
